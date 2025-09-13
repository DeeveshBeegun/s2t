package org.example.controller;

import org.example.service.ChatService;
import org.example.service.PromptChatService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    private final ChatClient openaiChatClient;
    private final ChatClient ollamaChatClient;
    private final ChatService chatService;
    private final PromptChatService promptChatService;

    public ChatController(@Qualifier("openaiChatClient") ChatClient openaiChatClient,
                          @Qualifier("ollamaChatClient") ChatClient ollamaChatClient, ChatService chatService
    , PromptChatService promptChatService) {
         this.openaiChatClient = openaiChatClient;
         this.ollamaChatClient = ollamaChatClient;
         this.chatService = chatService;
         this.promptChatService = promptChatService;
    }

    @GetMapping("/extract")
    public String ask(@RequestParam String inputText,
                      @RequestParam(defaultValue = "ollama") String provider) {
        ChatClient chatClient = "ollama".equalsIgnoreCase(provider) ? ollamaChatClient : openaiChatClient;
        return chatClient.prompt(inputText)
                .call()
                .content();
    }

    @PostMapping("/addItem")
    public String addItemToChatMemory(@RequestParam String item) {
        return chatService.addItemAndFetchHistory(item);
    }

    @PostMapping("/addChat")
    public String addChatToMemory(@RequestParam String chat) {
        return promptChatService.addItemAndFetchHistory(chat);
    }
}
