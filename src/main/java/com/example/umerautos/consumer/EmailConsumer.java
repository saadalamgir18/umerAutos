package com.example.umerautos.consumer;

import com.example.umerautos.dto.LowStockEmailDTO;
import com.example.umerautos.services.SendEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
public class EmailConsumer {

    @Autowired private SendEmailService sendEmailService;


    @KafkaListener(topics = "${kafka.topicName}", groupId = "${spring.kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory")
    public void listener(LowStockEmailDTO message){

        System.out.println("kafka message received! = " + message);
        sendEmailService.sendEmail(message.getLowStock());


    }
}
