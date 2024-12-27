package com.sbp.ollamaExemple.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClientRequestException;

import com.sbp.ollamaExemple.service.FileReadingService;
import com.sbp.ollamaExemple.service.ChatService;
import com.sbp.ollamaExemple.entity.Conversation;

import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/model")
@RequiredArgsConstructor
public class OllamaCallController {

    private final FileReadingService fileReadingService;
    private final OllamaChatModel chatModel;
    private final ChatService chatService;

    @PostMapping(path = "/dora")
    public String askDoraAQuestion(@RequestParam String question, @RequestParam String key) {
        System.out.println("Received question: " + question + ", key: " + key);
        
        String prompt = selectPromptBasedOnKey(key);
        System.out.println("Selected prompt: " + prompt);
        
        List<Message> messages = new ArrayList<>();
        messages.add(new SystemMessage("<start_of_turn>" + prompt + "<end_of_turn>"));
        messages.add(new UserMessage("<start_of_turn>" + question + "<end_of_turn>"));

        Prompt promptToSend = new Prompt(messages);
        System.out.println("Sending prompt to chat model...");

        Flux<ChatResponse> chatResponses = chatModel.stream(promptToSend);
        String messageContent = Objects.requireNonNull(chatResponses.collectList().block()).stream()
                .map(response -> response.getResult().getOutput().getContent())
                .collect(Collectors.joining(""));

        System.out.println("Generated message content: " + messageContent);

        Conversation conversation = chatService.startNewConversation(key);
        chatService.addMessageToConversation(conversation, messageContent);

        return messageContent;
    }

    private String selectPromptBasedOnKey(String key) {
        String doraPrompt = fileReadingService.readInternalFileAsString("prompts/promptDora.txt");
        String moodPrompt;

        switch (key) {
            case "happy":
                moodPrompt = fileReadingService.readInternalFileAsString("prompts/happyPrompt.txt");
                break;
            case "serious":
                moodPrompt = fileReadingService.readInternalFileAsString("prompts/seriousPrompt.txt");
                break;
            default:
                moodPrompt = fileReadingService.readInternalFileAsString("prompts/defaultPrompt.txt");
                break;
        }

        return doraPrompt + "\n" + moodPrompt;
    }
}
