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


# ------- Cluster commands setup ------- #
docker exec -it -w /opt/kafka/bin kafka-cluster bash

alias kafka-console-consumer="./kafka-console-consumer.sh --bootstrap-server kafka-cluster:29092"
alias kafka-console-producer="./kafka-console-producer.sh --bootstrap-server kafka-cluster:29092"
alias kafka-topics="./kafka-topics.sh --bootstrap-server kafka-cluster:29092"
alias kafka-configs="./kafka-configs.sh --bootstrap-server kafka-cluster:29092"


# ------- Consumes messages from trades topic ------- #
kafka-console-consumer --topic trades --from-beginning
kafka-console-consumer --topic trade-transactions-count --from-beginning \
      --property "print.key=true" \
      --property key.separator=":" \
      --value-deserializer "org.apache.kafka.common.serialization.LongDeserializer"


# ------- Topics management ------- #
kafka-topics --list
kafka-topics --topic trades --delete
kafka-topics --topic trade-transactions-count --create --partitions 3 --replication-factor 1

kafka-configs --entity-type topics --entity-name trade-transactions-count --alter \
      --add-config cleanup.policy=compact