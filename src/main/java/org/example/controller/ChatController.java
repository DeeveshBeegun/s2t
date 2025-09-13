package org.example.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    private final ChatClient openaiChatClient;
    private final ChatClient ollamaChatClient;

    public ChatController(@Qualifier("openaiChatClient") ChatClient openaiChatClient,
                          @Qualifier("ollamaChatClient") ChatClient ollamaChatClient) {
         this.openaiChatClient = openaiChatClient;
         this.ollamaChatClient = ollamaChatClient;
        }

    @GetMapping("/extract")
    public String ask(@RequestParam String inputText,
                      @RequestParam(defaultValue = "ollama") String provider) {
        ChatClient chatClient = "ollama".equalsIgnoreCase(provider) ? ollamaChatClient : openaiChatClient;
        return chatClient.prompt(inputText)
                .call()
                .content();
    }   
}
