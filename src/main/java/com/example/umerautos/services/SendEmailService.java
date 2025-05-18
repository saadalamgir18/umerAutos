package com.example.umerautos.services;

import java.util.Map;

public interface SendEmailService {
    void sendEmail(Map<String, Integer> lowStockMap);
}
