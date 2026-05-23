package com.example.AI_chatbot.service;

import com.example.AI_chatbot.dto.AuthRequest;
import com.example.AI_chatbot.dto.AuthResponse;
import com.example.AI_chatbot.dto.RegisterRequest;
import com.example.AI_chatbot.entity.User;
import com.example.AI_chatbot.repository.UserRepository;
import com.example.AI_chatbot.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("User already exists");
        }
        
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
                
        userRepository.save(user);
        var jwtToken = jwtUtil.generateToken(user.getEmail());
        return AuthResponse.builder()
                .token(jwtToken)
                .name(user.getName())
                .build();
    }

    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        var jwtToken = jwtUtil.generateToken(user.getEmail());
        return AuthResponse.builder()
                .token(jwtToken)
                .name(user.getName())
                .build();
    }
}
