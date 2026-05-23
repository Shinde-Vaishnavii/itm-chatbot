package com.example.AI_chatbot.repository;

import com.example.AI_chatbot.entity.Faq;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FaqRepository extends JpaRepository<Faq, Long> {
}
