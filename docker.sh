# сборка контейнера
docker build -t backend_of_financial_monitoring .
# запуск контейнера
docker run -d -p 8080:8080 backend_of_financial_monitoring