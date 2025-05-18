package com.example.umerautos.services;

import com.example.umerautos.dto.LowStockEmailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
public class SendEmailServiceImpl implements SendEmailService {


    @Autowired  JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String mailSenderUsername;



    @Override
    public void sendEmail(Map<String, Integer> lowStockMap) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(mailSenderUsername);
        message.setSubject("Low Stock Alert");
        StringBuilder body = new StringBuilder();
        body.append("Dear Team,\n\n");
        body.append("The following items have stock levels below the threshold:\n\n");

        for (Map.Entry<String, Integer> entry : lowStockMap.entrySet()) {
            body.append(String.format("Part Name: %-20s | Current Stock: %d\n", entry.getKey(), entry.getValue()));
        }

        body.append("\nPlease restock these items accordingly.\n\n");
        body.append("Regards,\nInventory Management System");

        message.setText(body.toString());
        javaMailSender.send(message);

    }
}
