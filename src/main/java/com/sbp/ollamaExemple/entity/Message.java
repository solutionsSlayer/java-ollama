package com.sbp.ollamaExemple.entity;

import jakarta.persistence.*;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne
    @JoinColumn(name = "conversation_id")
    private Conversation conversation;

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }
} 