package org.example.service;

import org.example.Advisor.LoggingAdvisor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoggingService {

    private final ChatClient chatClient;

    @Autowired
    public LoggingService(ChatClient.Builder chatClientBuilder, LoggingAdvisor loggingAdvisor) {
        this.chatClient = chatClientBuilder
                .defaultAdvisors(loggingAdvisor)
                .build();
    }

    public String initiateChat(String message) {
        return chatClient.prompt()
                .user(message)
                .call()
                .content();
    }


}
