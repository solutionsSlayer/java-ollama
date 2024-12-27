package com.sbp.ollamaExemple.service;

import com.sbp.ollamaExemple.entity.Conversation;
import com.sbp.ollamaExemple.entity.Message;
import com.sbp.ollamaExemple.repository.ConversationRepository;
import com.sbp.ollamaExemple.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;

    public Conversation startNewConversation() {
        return conversationRepository.save(new Conversation());
    }

    public void addMessageToConversation(Conversation conversation, String content) {
        Message message = new Message();
        message.setContent(content);
        message.setConversation(conversation);
        messageRepository.save(message);
    }

    public List<Message> getMessagesForConversation(Conversation conversation) {
        return conversation.getMessages();
    }
} 