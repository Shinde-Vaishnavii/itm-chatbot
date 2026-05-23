package com.example.model;



import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "CHAT_HISTORY")
public class ChatHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chat_seq")
    @SequenceGenerator(name = "chat_seq", sequenceName = "CHAT_HISTORY_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Column(name = "USER_MESSAGE", length = 1000)
    private String userMessage;

    @Column(name = "BOT_REPLY", length = 4000)
    private String botReply;

    @Column(name = "SESSION_ID", length = 100)
    private String sessionId;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    public ChatHistory() {}

    public ChatHistory(String userMessage, String botReply, String sessionId) {
        this.userMessage = userMessage;
        this.botReply = botReply;
        this.sessionId = sessionId;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getUserMessage() { return userMessage; }
    public void setUserMessage(String userMessage) { this.userMessage = userMessage; }

    public String getBotReply() { return botReply; }
    public void setBotReply(String botReply) { this.botReply = botReply; }

    public String getSessionId() { return sessionId; }
    public void setSessionId(String sessionId) { this.sessionId = sessionId; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}