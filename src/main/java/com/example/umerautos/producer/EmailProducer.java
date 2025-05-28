package com.example.umerautos.producer;

import com.example.umerautos.dto.LowStockEmailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
public class EmailProducer {
    @Autowired
    private KafkaTemplate<String, LowStockEmailDTO> kafkaTemplate;


    public void sendMessage(String topicName,LowStockEmailDTO message){
        System.out.println(topicName);

        CompletableFuture<SendResult<String, LowStockEmailDTO>> future = kafkaTemplate.send(topicName, message);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                System.out.println("Sent message=[" + message +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            } else {
                System.out.println("Unable to send message=[" +
                        message + "] due to : " + ex.getMessage());
            }
        });

    }
}
