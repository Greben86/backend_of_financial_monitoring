import streamlit as st
import requests
import pandas as pd
import plotly.express as px
import tempfile
from fpdf import FPDF
import plotly.io as pio
from datetime import datetime

BASE_URL = "http://backend-financial-monitoring:8080"

if "users" not in st.session_state:
    st.session_state.users = {}  # username -> token

if "active_user" not in st.session_state:
    st.session_state.active_user = None

def switch_user(username):
    st.session_state.active_user = username
    st.session_state.token = st.session_state.users[username]

def api_request(method, endpoint, json=None, params=None):
    token = st.session_state.get("token", None)
    headers = {"Authorization": f"Bearer {token}"} if token else {}
    return requests.request(method, f"{BASE_URL}{endpoint}", json=json, params=params, headers=headers)

def fetch_categories():
    resp = api_request("GET", "/category/all")
    return resp.json() if resp.ok else []

def fetch_category_options():
    categories = fetch_categories()
    return {cat['name']: cat['id'] for cat in categories}

BANKS = ["Сбербанк", "Альфа-Банк", "Тинькофф", "ВТБ", "Газпромбанк"]

st.title("💳 Финансовое приложение")

menu = st.sidebar.selectbox("Навигация", [
    "Регистрация", "Вход", "Смена пароля",
    "Добавить транзакцию", "Редактировать транзакцию", "Изменить статус транзакции", "Удалить транзакцию",
    "Категории", "Просмотр транзакций", "Графики по транзакциям", "Экспорт в Excel"
])


if menu == "Регистрация":
    st.subheader("Регистрация")
    username = st.text_input("Имя пользователя")
    password = st.text_input("Пароль", type="password")
    if st.button("Зарегистрироваться"):
        resp = api_request("POST", "/auth/sign/up", json={"username": username, "password": password})
        st.write(resp.json())

elif menu == "Вход":
    st.subheader("Вход")
    username = st.text_input("Имя пользователя")
    password = st.text_input("Пароль", type="password")
    if st.button("Войти"):
        resp = api_request("POST", "/auth/sign/in", json={"username": username, "password": password})
        if resp.ok:
            st.session_state.token = resp.json()["token"]
            st.success("Успешный вход!")
        else:
            st.error("Ошибка входа")

elif menu == "Смена пароля":
    st.subheader("Смена пароля")
    new_password = st.text_input("Новый пароль", type="password")
    if st.button("Сменить"):
        resp = api_request("PUT", "/auth/password/change", json={"password": new_password})
        st.write(resp.json() if resp.ok else resp.text)

elif menu == "Добавить транзакцию":
    st.subheader("Добавить транзакцию")
    category_options = fetch_category_options()
    data = {
        "customerType": st.selectbox("Тип лица", ["Физическое лицо", "Юридическое лицо"]),
        "transactionTime": st.date_input("Дата операции", value=datetime.now()).strftime("%d-%m-%Y") + " " + st.time_input("Время", value=datetime.now().time()).strftime("%H:%M:%S"),
        "transactionType": st.selectbox("Тип транзакции", ["Списание", "Поступление"]),
        "description": st.text_input("Комментарий"),
        "sumValue": st.number_input("Сумма", min_value=0.0),
        "senderBank": st.selectbox("Банк отправителя", BANKS),
        "account": st.text_input("Счёт"),
        "recipientBank": st.selectbox("Банк получателя", BANKS),
        "inn": st.text_input("ИНН получателя"),
        "recipientAccount": st.text_input("Счёт получателя"),
        "categoryId": category_options[st.selectbox("Категория", list(category_options.keys()))],
        "phone": st.text_input("Телефон")
    }
    if st.button("Добавить"):
        resp = api_request("POST", "/transaction/add", json=data)
        st.write(resp.json() if resp.ok else resp.text)

elif menu == "Редактировать транзакцию":
    st.subheader("Редактировать транзакцию")
    id = st.number_input("ID транзакции", step=1)
    category_options = fetch_category_options()
    data = {
        "customerType": st.selectbox("Тип лица", ["Физическое лицо", "Юридическое лицо"]),
        "transactionTime": st.date_input("Дата операции", value=datetime.now()).strftime("%d-%m-%Y") + " " + st.time_input("Время", value=datetime.now().time()).strftime("%H:%M:%S"),
        "transactionType": st.selectbox("Тип транзакции", ["Списание", "Поступление"]),
        "description": st.text_input("Комментарий"),
        "sumValue": st.number_input("Сумма"),
        "senderBank": st.selectbox("Банк отправителя", BANKS),
        "account": st.text_input("Счёт"),
        "recipientBank": st.selectbox("Банк получателя", BANKS),
        "inn": st.text_input("ИНН получателя"),
        "recipientAccount": st.text_input("Счёт получателя"),
        "categoryId": category_options[st.selectbox("Категория", list(category_options.keys()))],
        "phone": st.text_input("Телефон")
    }
    if st.button("Сохранить изменения"):
        resp = api_request("PUT", f"/transaction/{id}/edit", json=data)
        st.write(resp.json() if resp.ok else resp.text)

elif menu == "Изменить статус транзакции":
    st.subheader("Изменение статуса транзакции")
    id = st.number_input("ID транзакции", step=1)
    status = st.selectbox("Новый статус", ["Новая", "Подтвержденная", "В обработке", "Отменена", "Платеж выполнен", "Платеж удален", "Возврат"])
    if st.button("Изменить статус"):
        resp = api_request("PUT", f"/transaction/{id}/status", json={"status": status})
        st.write(resp.json() if resp.ok else resp.text)

elif menu == "Удалить транзакцию":
    st.subheader("Удаление транзакции")
    id = st.number_input("ID транзакции", step=1)
    if st.button("Удалить"):
        resp = api_request("DELETE", f"/transaction/{id}/delete")
        st.write(resp.text)

elif menu == "Категории":
    st.subheader("Категории")
    subaction = st.radio("Действие", ["Просмотр", "Добавить", "Редактировать"])
    if subaction == "Просмотр":
        resp = api_request("GET", "/category/all")
        st.dataframe(resp.json())
    elif subaction == "Добавить":
        name = st.text_input("Название категории")
        if st.button("Добавить"):
            resp = api_request("POST", "/category/add", json={"name": name})
            st.dataframe(resp.json() if resp.ok else resp.text)
    elif subaction == "Редактировать":
        id = st.number_input("ID категории", step=1)
        name = st.text_input("Новое название")
        if st.button("Сохранить"):
            resp = api_request("PUT", f"/category/{id}/edit", json={"name": name})
            st.dataframe(resp.json() if resp.ok else resp.text)

elif menu == "Просмотр транзакций":
    st.subheader("Фильтр транзакций")
    filters = {
        "senderBank": st.text_input("Банк отправителя"),
        "recipientBank": st.text_input("Банк получателя"),
        "dateStart": st.text_input("Дата начала (ISO)"),
        "dateEnd": st.text_input("Дата конца (ISO)"),
        "status": st.text_input("Статус"),
        "inn": st.text_input("ИНН"),
        "minSumValue": st.number_input("Мин. сумма", step=0.01),
        "maxSumValue": st.number_input("Макс. сумма", step=0.01),
        "transactionType": st.text_input("Тип операции"),
        "categoryName": st.text_input("Категория")
    }
    if st.button("Найти"):
        resp = api_request("GET", "/transaction/search", params={"properties": filters})
        st.dataframe(resp.json())

elif menu == "Графики по транзакциям":
    st.subheader("📈 Графики и аналитика по транзакциям")
    filters = {
        "senderBank": st.text_input("Банк отправителя"),
        "recipientBank": st.text_input("Банк получателя"),
        "dateStart": st.text_input("Дата начала (ISO)"),
        "dateEnd": st.text_input("Дата конца (ISO)"),
        "status": st.text_input("Статус"),
        "transactionType": st.selectbox("Тип транзакции", ["", "Списание", "Поступление"]),
        "categoryName": st.text_input("Категория"),
        "inn": st.text_input("ИНН"),
        "phone": st.text_input("Телефон")
    }
    if st.button("Показать статистику"):
        resp = api_request("GET", "/transaction/search", params={"properties": filters})
        if resp.ok and resp.json():
            df = pd.DataFrame(resp.json())
            df["transactionTime"] = pd.to_datetime(df["transactionTime"], format="%d-%m-%Y %H:%M:%S")
            df["categoryName"] = df["category"].apply(lambda x: x.get("name") if isinstance(x, dict) else "Не указано")
            st.dataframe(df.sort_values("transactionTime", ascending=False))
            fig1 = px.line(df, x="transactionTime", y="sumValue", color="transactionType", markers=True)
            fig2 = px.bar(df, x="categoryName", y="sumValue", color="transactionType", barmode="group")
            df["bank"] = df["senderBank"].fillna("") + " ➡ " + df["recipientBank"].fillna("")
            fig3 = px.bar(df, x="bank", y="sumValue", color="transactionType")
            st.plotly_chart(fig1)
            st.plotly_chart(fig2)
            st.plotly_chart(fig3)

elif menu == "Экспорт в Excel":
    st.subheader("Экспорт транзакций")
    filters = {
        "senderBank": st.text_input("Банк отправителя"),
        "recipientBank": st.text_input("Банк получателя"),
        "dateStart": st.text_input("Дата начала (ISO)"),
        "dateEnd": st.text_input("Дата конца (ISO)")
    }
    if st.button("Экспортировать"):
        resp = api_request("GET", "/transaction/export", params={"properties": filters})
        if resp.ok:
            st.download_button("Скачать Excel", data=resp.content, file_name="transactions.xlsx")
        else:
            st.error("Ошибка экспорта")
