# AI-Powered FAQ Chatbot for College Website

A complete, production-ready full-stack application built for a modern college website. This repository features a responsive, beautifully designed frontend integrated with a powerful Spring Boot backend, utilizing an AI-powered conversational agent.

This project handles student inquiries by utilizing a sophisticated database-driven keyword matching engine with built-in hooks for fall-backs to OpenAI (or Ollama/Anthropic), thus resolving FAQs automatically before escalating to human support.

## 🚀 Features

### Frontend (HTML/CSS/JS)
- **Modern UI/UX**: Premium aesthetic with dark-mode elements, smooth transitions, and glassmorphism.
- **Dynamic Content**: Floating chatbot interface that allows auto-scrolling, typing indicators, and message histories.
- **Integrated Modals**: Login and Registration via JWT fully handled via AJAX JavaScript.
- **No Heavy Frameworks**: Pure HTML/CSS/JS architecture for rapid loading and SEO-friendliness.

### Backend (Java Spring Boot)
- **Robust REST API**: Well-structured MVC architecture isolating controllers, services, repositories, and models.
- **Spring Security & JWT**: State-less security architecture. Passwords encrypted using BCrypt.
- **AI-Hybrid Engine**: Fallback NLP logic where local DB FAQs are scanned via Keyword-matching; if unavailable, it can trigger an external AI API call (e.g. OpenAI GPT-3.5-turbo).
- **History Tracking**: Securely tracks and restores past conversations tied to the user's logged-in identity.
- **Oracle DB Integration**: Uses Oracle Database for persistence (`users`, `faqs`, `chat_history`).

## 🛠 Tech Stack
- **Frontend**: HTML5, Vanilla JavaScript, CSS3
- **Backend**: Java 21, Spring Boot 3
- **Database**: Oracle DB (ojdbc11)
- **Security**: Spring Security, JWT (JSON Web Tokens), BCrypt
- **Build Tool**: Maven

## 📋 How to Run

1. **Clone the Repository**
2. **Database Setup**
   Ensure an Oracle Database is running on `localhost:1521` with Service Name `ORCL`. 
   Update credentials in `src/main/resources/application.properties` if needed:
   ```properties
   spring.datasource.username=system
   spring.datasource.password=tiger
   ```
3. **OpenAI Key / Anthropic Key (Optional)**
   Add your generated API keys to `application.properties` to switch from keyword-matching to generative AI fallback.
4. **Build and Run**
   Open a terminal in the project directory and run:
   ```bash
   ./mvnw spring-boot:run
   ```
5. **Access the App**
   Open your browser and navigate to: `http://localhost:8080/index.html`

## 👔 Resume Description

*Consider adding this snippet to your resume under "Projects" or "Professional Experience":*

**Full-Stack AI Chatbot Developer | Java Spring Boot & Vanilla JavaScript** 
- *Designed and developed a production-ready AI conversational assistant integrated into a modern university portal, addressing student inquiries with highly scalable REST APIs.*
- *Architected a robust Spring Boot backend featuring Spring Security, JWT stateless authentication, and BCrypt password encoding to strictly protect student PII.*
- *Implemented a hybrid-NLP solution querying an Oracle Database for standard FAQs while maintaining expandable hooks to fall back on OpenAI large language models (LLMs) for complex, long-tail queries.*
- *Created a zero-dependency frontend using responsive CSS3 and Vanilla JS, delivering a premium "glassmorphism" aesthetic, real-time typing indicators, and auto-restoring chat histories.*
