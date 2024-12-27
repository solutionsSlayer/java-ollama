package com.sbp.ollamaExemple.repository;

import com.sbp.ollamaExemple.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
} 