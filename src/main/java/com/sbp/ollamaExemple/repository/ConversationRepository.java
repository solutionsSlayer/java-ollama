package com.sbp.ollamaExemple.repository;

import com.sbp.ollamaExemple.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
} 