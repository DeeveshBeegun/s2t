package org.example.controller;

import org.example.service.ChatService;
import org.example.service.FormExtractorService;
import org.example.service.LoggingService;
import org.example.service.PromptChatService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    private final ChatService chatService;
    private final PromptChatService promptChatService;
    private final LoggingService loggingService;
    private final FormExtractorService extractorService;

    public ChatController(ChatService chatService, PromptChatService promptChatService, LoggingService loggingService,
                          FormExtractorService extractorService) {
        this.chatService = chatService;
        this.promptChatService = promptChatService;
        this.loggingService = loggingService;
        this.extractorService = extractorService;
    }

    @GetMapping("/extract")
    public String ask(@RequestParam String inputText,
                      @RequestParam(defaultValue = "ollama") String provider) {
        return extractorService.extractForm(inputText);
    }

    @PostMapping("/addItem")
    public String addItemToChatMemory(@RequestParam String item) {
        return chatService.addItemAndFetchHistory(item);
    }

    @PostMapping("/addChat")
    public String addChatToMemory(@RequestParam String chat) {
        return promptChatService.addItemAndFetchHistory(chat);
    }

    @GetMapping("/inquire")
    public String inquire(@RequestParam String question) {
        return loggingService.initiateChat(question);
    }
}
