package com.example.umerautos.configuration;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootStrapAddress;

    @Value(value = "${kafka.topicName}")
    private String topicName;

    @Bean
    public KafkaAdmin kafkaAdmin(){
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapAddress);
        return new KafkaAdmin(configs);
     }

     @Bean
    public NewTopic kafkaTopic(){
        return new NewTopic(topicName, 1, (short) 1);
     }

}
