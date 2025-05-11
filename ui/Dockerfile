FROM python:3.11-slim

RUN apt-get update && apt-get install -y \
    build-essential \
    libgl1-mesa-glx \
    libglib2.0-0 \
    curl \
    && pip install --upgrade pip

COPY requirements.txt .
RUN pip install -r requirements.txt

WORKDIR /app
COPY . .

EXPOSE 8501

CMD ["streamlit", "run", "main.py", "--server.port=8501", "--server.address=0.0.0.0"]
