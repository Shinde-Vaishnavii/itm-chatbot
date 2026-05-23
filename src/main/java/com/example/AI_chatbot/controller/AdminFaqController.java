package com.example.AI_chatbot.controller;

import com.example.AI_chatbot.entity.Faq;
import com.example.AI_chatbot.repository.FaqRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/faqs")
@RequiredArgsConstructor
public class AdminFaqController {

    private final FaqRepository faqRepository;

    @GetMapping
    public List<Faq> getAllFaqs() {
        return faqRepository.findAll();
    }

    @PostMapping
    public Faq addFaq(@RequestBody Faq faq) {
        return faqRepository.save(faq);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFaq(@PathVariable Long id) {
        faqRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
