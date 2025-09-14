package org.example.service;

import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class FormExtractorService {

    private final ChatClient chatClient;

    @Autowired
    public FormExtractorService(@Qualifier("ollamaChatClient") ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public String extractForm(String inputText) {
        PromptTemplate promptTemplate = new PromptTemplate(
            """
            You are a data extraction assistant.
            Extract the following fields from the input text:
            - Name - Email - Phone - Address - Appointment Date (yyyy-mm-dd)
            If a field is missing, return it as null.
            Respond only in JSON.
            Input: {inputText}
            """
        );

        Prompt prompt = promptTemplate.create(Map.of("inputText", inputText));

        return chatClient.prompt(prompt)
                .call()
                .content();
    }
	
}
