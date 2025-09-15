package org.example.service;

import java.util.Map;

import org.example.BirdFormDetails;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FormExtractorService {

    private final OllamaChatModel chatModel;

    @Autowired
    public FormExtractorService(OllamaChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public String extractFormData(String inputText) {
        BeanOutputConverter<BirdFormDetails> beanOutputConverter = new BeanOutputConverter<>(BirdFormDetails.class);

        String format= beanOutputConverter.getFormat();

        String template = """
        You are an information extraction assistant. Given a free-form bird observation description,
        extract the following fields and output strict JSON only (no explanation):
        {{ "species": string or null, "count": integer or null, "sex": one of ["male","female","unknown"] or null,
        "age": one of ["adult","juvenile","immature","unknown"] or null, "behavior": concise string or null,
        "habitat": concise string or null, "notes": string or null, "timeObserved": "HH:mm" 24h or null }}
        Input: {inputText}
        {format}
        """;

        PromptTemplate promptTemplate = new PromptTemplate(template, Map.of("inputText", inputText, "format",
                format)
        );
        Prompt prompt = new Prompt(promptTemplate.createMessage());
        Generation generation = chatModel.call(prompt)
                .getResult();

        return beanOutputConverter.convert(generation.getOutput().getText()).toString();

    }
	
}
