package com.example.AI_chatbot.service;

import com.example.AI_chatbot.entity.Faq;
import com.example.AI_chatbot.repository.FaqRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatbotService {

    private final FaqRepository faqRepository;
    
    @Value("${openai.api.key:}")
    private String openAiKey;

    public String getResponse(String message) {
        // 1. Keyword matching from DB
        List<Faq> allFaqs = faqRepository.findAll();
        String lowerMessage = message.toLowerCase();
        
        for (Faq faq : allFaqs) {
            String[] keywords = faq.getQuestion().toLowerCase().split(" ");
            for (String keyword : keywords) {
                if (keyword.length() > 3 && lowerMessage.contains(keyword)) {
                    return faq.getAnswer();
                }
            }
        }
        
        // 2. Exact match fallback
        for (Faq faq : allFaqs) {
            if (lowerMessage.contains(faq.getQuestion().toLowerCase())) {
                return faq.getAnswer();
            }
        }
        
        // 3. Optional AI Fallback
        if (openAiKey != null && !openAiKey.isEmpty() && !openAiKey.equals("your-openai-api-key-here")) {
            return callExternalAi(message);
        }
        
        // 4. Default message
        return "I'm still learning! Please contact the college administration for more details on this topic. " +
               "If you want smarter AI answers, please configure the AI API key in application.properties.";
    }
    
    private String callExternalAi(String message) {
        // Placeholder for RestTemplate or WebClient call to OpenAI API
        // This is implemented minimally to satisfy compile and provide structure.
        return "AI Response: I understand you are asking about '" + message + "'. Please refer to our main catalog.";
    }
}
