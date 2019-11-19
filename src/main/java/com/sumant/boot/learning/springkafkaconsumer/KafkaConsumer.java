package com.sumant.boot.learning.springkafkaconsumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.protocol.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@EnableKafka
@Component
public class KafkaConsumer {

    private Book book;

    @KafkaListener(topics = "${book.topic}")
    public void listen(ConsumerRecord<String, String> record){
        System.out.println("Received Message: " + record.value());
        //this.book = book;
    }

    public Book getBook() {
        return book;
    }
}
