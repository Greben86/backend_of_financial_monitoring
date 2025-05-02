# 🧪 Демонстрация работы с входными данными (API)

Ниже представлены примеры запросов ко всем основным эндпоинтам API, включая авторизацию, транзакции, категории, смену пароля и экспорт.

## 🔐 Регистрация пользователя (POST /auth/sign/up)
```json
{
  "username": "vasya",
  "password": "My_passw0rd1"
}
```

## 🔑 Авторизация пользователя (POST /auth/sign/in)
```json
{
  "username": "vasya",
  "password": "My_passw0rd1"
}
```

## 🔁 Смена пароля (PUT /auth/password/change)
```json
{
  "password": "My_NEW_passw0rd1"
}
```

## 👤 Изменение имени пользователя (PUT /user/edit)
```json
{
  "username": "Petya"
}
```

## 🧩 Добавление категории (POST /category/add)
```json
{
  "name": "Продукты"
}
```

## ✏️ Редактирование категории (PUT /category/1/edit)
```json
{
  "name": "Продукты и еда"
}
```

## 📂 Получение всех категорий (GET /category/all)
**Нет тела запроса**

## 💰 Добавление транзакции (POST /transaction/add)
```json
{
  "customerType": "Физическое лицо",
  "transactionTime": "01-05-2025 14:00:00",
  "transactionType": "Поступление",
  "description": "Подарок",
  "sumValue": 1000.00,
  "senderBank": "Райффайзен",
  "account": "40817810000000000001",
  "recipientBank": "Альфа",
  "inn": "1234567890",
  "recipientAccount": "40702810000000000002",
  "categoryId": 1,
  "phone": "+79161234567"
}
```

## ✏️ Редактирование транзакции (PUT /transaction/100/edit)
```json
{
  "customerType": "Физическое лицо",
  "transactionTime": "01-05-2025 14:00:00",
  "transactionType": "Поступление",
  "description": "Подарок на день рождения",
  "sumValue": 1200.00,
  "senderBank": "Райффайзен",
  "account": "40817810000000000001",
  "recipientBank": "Альфа",
  "inn": "1234567890",
  "recipientAccount": "40702810000000000002",
  "categoryId": 1,
  "phone": "+79161234567"
}
```

## 🔁 Изменение статуса транзакции (PUT /transaction/100/status)
```json
{
  "status": "Платеж выполнен"
}
```

## 🔍 Фильтрация транзакций (GET /transaction/search?properties={...})
```json
{
  "transactionType": "Поступление",
  "status": "Платеж выполнен"
}
```

## 📤 Экспорт транзакций (GET /transaction/export?properties={...})
**Ответ: Excel-файл**

## 🗑 Удаление транзакции (DELETE /transaction/100/delete)
**Нет тела запроса**

## POSTMAN 
**[financial_monitoring_collection.json](financial_monitoring_collection.json)**
