package com.example.umerautos.controllers;

import com.example.umerautos.services.SendEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class SendEmailController {
    @Autowired SendEmailService sendEmailService;

    @GetMapping("/send-email")
    public ResponseEntity<?> sendEmail(){
        Map<String, Integer> lowStock = new HashMap<>();
        lowStock.put("product 1", 3);
        lowStock.put("product 2", 2);
        sendEmailService.sendEmail(lowStock);
        return new ResponseEntity<>("email send", HttpStatus.OK);
    }
}
