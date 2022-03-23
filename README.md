# Interview Task

## Lorem Ipsum API

https://loripsum.net/ provides a REST API to generate dummy text data.

## Task Description

Create small and simple system that contains 2 applications.

### 1. Processing application:

   Write a java server application that exposes a REST API having the next description:

   **Endpoint URL: /betvictor/text**

   **Accepted parameters:**

   * p - indicates the max number of paragraphs (positive number greater than 0)
   * l - indicates length of each paragraph (string of the following: short, medium, long, verylong)
   
   **Method:** HTTP GET

   **Response (JSON):**
   
   ~~~json
   {
   "freq_word": <most_frequent_word>
   "avg_paragraph_size":<avg_paragraph_size>
   "avg_paragraph_processing_time": <avg_paragraph_processing_time>
   "total_processing_time": <total_processing_time>
   }
   ~~~

**where:**

<most_frequent_word>- the word that was the most frequent in the paragraphs
<avg_paragraph_size> - the average size of a paragraph
<avg_paragraph_processing_time> - the average time spent analyzing a paragraph
<total_processing_time> - total processing time to generate the final response

The application should make Lorem Ipsum API requests to generate dummy text based on betvictor endpoint request parameters.

Example: **HTTP GET** /betvictor/text?p=3&l=short

The server will make Lorem Ipsum API requests to generate dummy text that contains from 1 to 3 paragraphs of “short” length:

HTTP GET: https://www.loripsum.net/api/1/short

HTTP GET: https://www.loripsum.net/api/2/short

HTTP GET: https://www.loripsum.net/api/3/short

The application must process all responses and compute the most frequent word found, the average paragraph size, the average paragraph processing time (how much time in average the application spent analysing a paragraph) and total processing time. Result of each request’s computation must be sent to Kafka topic **words.processed** in the same format as HTTP Response is (in other words: every request to **HTTP GET /betvictor/text** should return HTTP Response and produce Kafka message).

Additional requirements:

* Kafka producing messages with the same <most_frequent_word> sent to topic must be available to be consumed in the same order as they were sent.
* Consider as given that topic would have 4 partitions.
* Application must be configurable to allow Kafka producing to any Kafka broker.

### 2. Repository application:

Application should consume Kafka topic words.processed mentioned before. Consumed messages  should be stored in some datasource.
Application should expose HTTP endpoint:

**HTTP GET /betvictor/history**

Response should display last 10 computation results from database

Additional requirements:
* Application must be configurable to allow Kafka consuming from any Kafka broker.
* Application must be configurable to choose number of concurrent Kafka consumers (hint: by using separated threads, not by scaling application instances)



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
