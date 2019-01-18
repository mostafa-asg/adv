### Cassandra
```
CREATE KEYSPACE myks WITH replication = {'class':'SimpleStrategy', 'replication_factor' : 1};
use myks;
create table impressions(reqId text, data text,primary key (reqId));
```

### Kafka
```
Start kafka brokers
```

### Data ingestion
```
cd collector
mvn package
java -jar target/collector-1.0-SNAPSHOT-jar-with-dependencies.jar
```
It will listens on port 8585.

### Store in cassandra
```
cd store
mvn package
java -jar target/store-1.0-SNAPSHOT-jar-with-dependencies.jar
```

### Store in elasticsearch using Apache Spark
```
run through Intelij :D
```

### Generate data 
```
cd generator
mvn package
java -jar target/generator-1.0-SNAPSHOT-jar-with-dependencies.jar
```

#### HARD CODE
All addresses are hard coded as localhost or 127.0.0.1. So Kafka, Cassandra and ElasticSearch should be on the same server.
