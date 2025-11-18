# ------- Launches cluster ------- #
docker-compose up -d --build

# ------- Using cURL from Windows Powershell ------- #
Remove-Item alias:curl


# ------- Creates datagen Connector ------- #
curl -X POST http://localhost:8083/connectors -H 'Content-Type: application/json' --data @datagen/connector.json

# ------- Connector lifecycle ------- #
curl -X PUT http://localhost:8083/connectors/datagen-trades/pause
curl -X PUT http://localhost:8083/connectors/datagen-trades/resume
curl -X DELETE http://localhost:8083/connectors/datagen-trades


# ------- Consumes messages from trades topic ------- #
docker-compose exec kafka-cluster /opt/kafka/bin/kafka-console-consumer.sh --topic trades --bootstrap-server kafka-cluster:29092 --from-beginning

# ------- Topics management ------- #
docker-compose exec kafka-cluster /opt/kafka/bin/kafka-topics.sh --bootstrap-server kafka-cluster:9092 --list
docker-compose exec kafka-cluster /opt/kafka/bin/kafka-topics.sh --bootstrap-server kafka-cluster:9092 --topic trades --delete
