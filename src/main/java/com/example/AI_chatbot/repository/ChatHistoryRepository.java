package com.example.AI_chatbot.repository;

import com.example.AI_chatbot.entity.ChatHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatHistoryRepository extends JpaRepository<ChatHistory, Long> {
    List<ChatHistory> findByUserIdOrderByTimestampAsc(Long userId);
}
