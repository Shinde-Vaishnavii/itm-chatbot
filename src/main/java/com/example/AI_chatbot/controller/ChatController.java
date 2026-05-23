package com.example.AI_chatbot.controller;

import com.example.AI_chatbot.dto.ChatRequest;
import com.example.AI_chatbot.dto.ChatResponse;
import com.example.AI_chatbot.entity.ChatHistory;
import com.example.AI_chatbot.entity.User;
import com.example.AI_chatbot.repository.ChatHistoryRepository;
import com.example.AI_chatbot.repository.UserRepository;
import com.example.AI_chatbot.service.ChatbotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ChatController {

    private final ChatbotService chatbotService;
    private final ChatHistoryRepository chatHistoryRepository;
    private final UserRepository userRepository;

    @PostMapping("/chat")
    public ResponseEntity<ChatResponse> chat(@RequestBody ChatRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = auth.getName();
        
        String aiResponse = chatbotService.getResponse(request.getMessage());
        
        User user = userRepository.findByEmail(userEmail).orElse(null);
        if (user != null) {
            ChatHistory history = ChatHistory.builder()
                    .user(user)
                    .message(request.getMessage())
                    .response(aiResponse)
                    .timestamp(LocalDateTime.now())
                    .build();
            chatHistoryRepository.save(history);
        }

        return ResponseEntity.ok(new ChatResponse(aiResponse));
    }
    
    @GetMapping("/history")
    public ResponseEntity<List<ChatHistory>> getHistory() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(auth.getName()).orElse(null);
        if (user != null) {
            return ResponseEntity.ok(chatHistoryRepository.findByUserIdOrderByTimestampAsc(user.getId()));
        }
        return ResponseEntity.badRequest().build();
    }
}
