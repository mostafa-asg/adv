package com.github;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.Session;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class Main {

    public static void main(String[] args) {
        Main app = new Main();

        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {
                app.stop();
            }
        });

        app.run();
    }

    private volatile boolean end = false;

    private void run() {
        Properties consumerProps = new Properties();
        consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        consumerProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        consumerProps.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, "toCassandra");
        KafkaConsumer<String,String> kafkaConsumer = new KafkaConsumer<String, String>(consumerProps);

        kafkaConsumer.subscribe(Collections.singleton("impressions"));

        Cluster cassandraCluster = Cluster.builder().addContactPoint("localhost").build();
        Session cassandraSession = cassandraCluster.connect("myks");
        PreparedStatement insertStatment = cassandraSession.prepare("INSERT INTO impressions(reqId,data) VALUES (?,?)");

        while (!end){
            ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofSeconds(1));
            if (!records.isEmpty()) {
                for (ConsumerRecord<String, String> record : records) {
                    cassandraSession.execute(
                            insertStatment.bind(record.key(), record.value())
                    );
                }

                kafkaConsumer.commitAsync();
            }
        }

        kafkaConsumer.close();
        cassandraSession.close();
        cassandraCluster.close();
    }

    private void stop(){
        end = true;
    }

}
