package org.example.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
    
    private final ChatClient chatClient; 

    public ChatService(OllamaChatModel chatModel) {
        ChatMemory chatMemory = new InMemoryChatMemory();
        MessageChatMemoryAdvisor memoryAdvisor = new MessageChatMemoryAdvisor(chatMemory);

        this.chatClient = ChatClient.builder(chatModel)
                .defaultAdvisors(memoryAdvisor)
                .build();
    }

    public String addItemAndFetchHistory(String item) {
        return chatClient.prompt()
                .user("Add this item to the list and show all items: " + item)
                .call()
                .content();
    }

    public String extractData(String userInput) {
        return this.chatClient.prompt()
            .user(userInput)
            .call()
            .content();
    }
}