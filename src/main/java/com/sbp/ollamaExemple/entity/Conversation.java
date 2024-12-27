package com.sbp.ollamaExemple.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String conversationKey;

    @OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> messages = new ArrayList<>();

    public List<Message> getMessages() {
        return messages;
    }

    public String getConversationKey() {
        return conversationKey;
    }

    public void setConversationKey(String conversationKey) {
        this.conversationKey = conversationKey;
    }
} 