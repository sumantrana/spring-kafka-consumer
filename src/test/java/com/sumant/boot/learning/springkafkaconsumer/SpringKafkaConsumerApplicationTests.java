package com.sumant.boot.learning.springkafkaconsumer;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("contextTest")
class SpringKafkaConsumerApplicationTests {

    @Test
    void contextLoads() {
    }

}
