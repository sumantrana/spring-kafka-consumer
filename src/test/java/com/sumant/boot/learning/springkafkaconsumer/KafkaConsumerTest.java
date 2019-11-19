package com.sumant.boot.learning.springkafkaconsumer;

import kafka.tools.ConsoleProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@EmbeddedKafka( partitions = 1, topics = {"${book.topic}"})
public class KafkaConsumerTest {

    @Value("${book.topic}")
    private String receiverTopic;

    @Autowired
    private KafkaConsumer consumer;

    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    Producer<String, Book> producer;

    @BeforeEach
    public void setup(){

        Map<String, Object> producerConfig = KafkaTestUtils.producerProps(embeddedKafkaBroker);

        DefaultKafkaProducerFactory<String, Book> producerFactory = new DefaultKafkaProducerFactory<String, Book>(producerConfig, new StringSerializer(), new JsonSerializer<>());

        producer = producerFactory.createProducer();

    }

    @Test
    public void testConsumer() throws Exception{

        Book book = Book.builder().name("TestRecvBook").value(20).build();
        ProducerRecord<String, Book> producerRecord = new ProducerRecord<>(receiverTopic, book);
        producer.send(producerRecord);
        Thread.sleep(5000);
        assertThat(consumer.getBook()).isEqualTo(book);
    }
}
