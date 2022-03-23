# Interview Task

## How to run

Create a topic:
~~~bash
bin/kafka-topics.sh --bootstrap-server localhost:9092 --create --topic words.processed --replication-factor 1 --partitions 4
~~~

1. Run the processor app:

```
cd processor_app
gradle bootRun
```

By default, the processor application exposes an HTTP endpoint at port `8084` and writes to a Kafka broker at `localhost:9092`.
This can be customized in `processor_app/src/main/resources/application.yaml`

2. Run the repository app:

```
cd repository_app
gradle bootRun
```

By default, the repository application exposes an HTTP endpoint at port `8085`, reads from a Kafka topic at `localhost:9092`, and persists the data in an in-memory H2 database.
This can be customized in `repository_app/src/main/resources/application.yaml`
