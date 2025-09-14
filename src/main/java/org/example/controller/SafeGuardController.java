package org.example.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SafeGuardAdvisor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SafeGuardController {

    private final ChatClient chatClient;

    public SafeGuardController(ChatClient.Builder chatClient) {
        List<String> forbiddenTopics = List.of("Violence", "Hate Speech", "Adult Content");

        SafeGuardAdvisor safeGuardAdvisor = new SafeGuardAdvisor(forbiddenTopics, "Content not allowed",
                1);
        this.chatClient = chatClient.defaultAdvisors(safeGuardAdvisor).build();
    }

    @GetMapping("/askSafe")
    public String ask(@RequestParam(value="question") String question) {
        return chatClient.prompt()
                .user(question)
                .call()
                .content();
    }
}
