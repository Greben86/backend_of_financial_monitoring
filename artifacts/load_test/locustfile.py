from locust import HttpUser, task, between
import random
import string
import datetime

def random_string(length=6):
    return ''.join(random.choices(string.ascii_letters, k=length))

def random_inn():
    return str(random.randint(1000000000, 9999999999))

def random_phone():
    return f"+7{random.randint(9000000000, 9999999999)}"

def current_time():
    return datetime.datetime.now().strftime("%d-%m-%Y %H:%M:%S")

def auth_headers(token):
    return {"Authorization": f"Bearer {token}"} if token else {}

class FinancialUser(HttpUser):
    wait_time = between(1, 3)
    host = "http://localhost:8080"

    def on_start(self):
        self.username = f"user_{random_string()}"
        self.password = "My_passw0rd1"
        self.token = None
        self.category_id = None

        self.register()
        self.login()
        self.create_category()

    def register(self):
        resp = self.client.post("/auth/sign/up", json={
            "username": self.username,
            "password": self.password
        })
        if resp.status_code != 201:
            print(f"❌ Ошибка регистрации: {resp.status_code} - {resp.text}")

    def login(self):
        resp = self.client.post("/auth/sign/in", json={
            "username": self.username,
            "password": self.password
        })
        if resp.status_code == 200:
            self.token = resp.json().get("token")
        else:
            print(f"❌ Ошибка логина: {resp.status_code} - {resp.text}")

    def create_category(self):
        name = f"Категория {random_string()}"
        resp = self.client.post("/category/add", json={"name": name}, headers=auth_headers(self.token))
        if resp.status_code in [200, 201]:
            self.category_id = resp.json().get("id")
        else:
            print(f"Ошибка создания категории: {resp.status_code} - {resp.text}")

    @task(3)
    def add_transaction(self):
        if not self.category_id:
            print("Категория не создана, пропускаем транзакцию")
            return

        payload = {
            "customerType": "Физическое лицо",
            "transactionTime": current_time(),
            "transactionType": "Поступление",
            "description": "Тестовая транзакция",
            "sumValue": round(random.uniform(100, 9999), 2),
            "senderBank": "Банк Тест",
            "account": "40817810000000000001",
            "recipientBank": "Банк Получатель",
            "inn": random_inn(),
            "recipientAccount": "40702810000000000002",
            "categoryId": self.category_id,
            "phone": random_phone()
        }

        resp = self.client.post("/transaction/add", json=payload, headers=auth_headers(self.token))
        if resp.status_code not in [201, 200]:
            print(f"Ошибка при добавлении транзакции: {resp.status_code} - {resp.text}")

    @task(1)
    def get_transactions(self):
        resp = self.client.get("/transaction/search?properties={}", headers=auth_headers(self.token))
        if resp.status_code != 200:
            print(f"Ошибка получения транзакций: {resp.status_code} - {resp.text}")
