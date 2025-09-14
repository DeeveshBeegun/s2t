package org.example.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.stereotype.Service;

@Service
public class PromptChatService {

    private final ChatClient chatClient;

    public PromptChatService(OllamaChatModel chatModel) {
        ChatMemory chatMemory = new InMemoryChatMemory();
        PromptChatMemoryAdvisor memoryAdvisor = new PromptChatMemoryAdvisor(chatMemory);
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
}
