package org.example.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.document.Document;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KnowledgeBasedChatService {

    private final ChatClient chatClient;
    private final VectorStore vectorDatabase;

    public KnowledgeBasedChatService(ChatClient.Builder chatClientBuilder, VectorStore vectorDatabase) {
        this.vectorDatabase = vectorDatabase;
        Document initialKnowledge = new Document("Water boils at 100 degrees Celsius under normal atmospheric pressure.");
        List<Document> preparedDocuments = new TokenTextSplitter().apply(List.of(initialKnowledge));
        this.vectorDatabase.add(preparedDocuments);

        QuestionAnswerAdvisor questionAnswerAdvisor = new QuestionAnswerAdvisor(vectorDatabase);

        this.chatClient = chatClientBuilder.defaultAdvisors(questionAnswerAdvisor)
                .build();
    }

    public String handleUserQuery(String question) {
        return chatClient.prompt()
                .user(question)
                .call()
                .content();
    }
}
