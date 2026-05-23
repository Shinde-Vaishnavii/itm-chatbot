// Chatbot Logic
const chatbotWrapper = document.getElementById('chatbot-wrapper');

document.addEventListener("DOMContentLoaded", () => {
    if (chatbotWrapper) {
        renderChatbotUI();
    }
});

function renderChatbotUI() {
    const token = localStorage.getItem('ai_chat_token');
    
    chatbotWrapper.innerHTML = `
        <button class="chatbot-toggler" onclick="toggleChatbot()">
            <svg viewBox="0 0 24 24"><path d="M20 2H4c-1.1 0-2 .9-2 2v18l4-4h14c1.1 0 2-.9 2-2V4c0-1.1-.9-2-2-2z"/></svg>
        </button>

        <div class="chatbot-window" id="chatbot-window">
            <div class="chatbot-header">
                <h3>Campus FAQ Bot</h3>
                <button class="close-chatbot" onclick="toggleChatbot()">&times;</button>
            </div>
            
            ${!token ? `
                <div class="chatbot-needs-login">
                    <svg viewBox="0 0 24 24" width="48" height="48" fill="#94a3b8"><path d="M12 2C6.48 2 2 6.48 2 12s4.48 10 10 10 10-4.48 10-10S17.52 2 12 2zm0 3c1.66 0 3 1.34 3 3s-1.34 3-3 3-3-1.34-3-3 1.34-3 3-3zm0 14.2c-2.5 0-4.71-1.28-6-3.22.03-1.99 4-3.08 6-3.08 1.99 0 5.97 1.09 6 3.08-1.29 1.94-3.5 3.22-6 3.22z"/></svg>
                    <p>Please log in to chat with our AI assistant.</p>
                    <button class="btn-primary" onclick="openLoginModal(); toggleChatbot();">Login Now</button>
                </div>
            ` : `
                <div class="chatbot-body" id="chatbot-body">
                    <div class="message bot">
                        Hi ${localStorage.getItem('ai_chat_name')}! I'm the campus AI assistant. How can I help you today?
                    </div>
                </div>
                <div class="chatbot-footer">
                    <input type="text" id="chat-input" placeholder="Type your question..." onkeypress="handleEnter(event)">
                    <button onclick="sendMessage()">
                        <svg viewBox="0 0 24 24"><path d="M2.01 21L23 12 2.01 3 2 10l15 2-15 2z"/></svg>
                    </button>
                </div>
            `}
        </div>
    `;

    if (token) {
        loadChatHistory();
    }
}

function toggleChatbot() {
    const w = document.getElementById('chatbot-window');
    w.classList.toggle('show');
}

function handleEnter(e) {
    if (e.key === 'Enter') {
        sendMessage();
    }
}

async function sendMessage() {
    const input = document.getElementById('chat-input');
    const msg = input.value.trim();
    if (!msg) return;

    input.value = '';
    
    // Append User MSG
    appendMessage(msg, 'user');
    
    // Append Loading
    const loadingId = appendLoading();
    
    // API Call
    const token = localStorage.getItem('ai_chat_token');
    try {
        const response = await fetch('/api/chat', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer ' + token
            },
            body: JSON.stringify({ message: msg })
        });
        
        removeElement(loadingId);
        
        if (response.ok) {
            const data = await response.json();
            appendMessage(data.response, 'bot');
        } else if (response.status === 401 || response.status === 403) {
            appendMessage('Session expired. Please log in again.', 'bot');
            logout();
        } else {
            appendMessage('Sorry, something went wrong on the server.', 'bot');
        }
    } catch(err) {
        removeElement(loadingId);
        appendMessage('Network error. Failed to reach the chatbot.', 'bot');
    }
}

function appendMessage(text, sender) {
    const body = document.getElementById('chatbot-body');
    if (!body) return;
    
    const div = document.createElement('div');
    div.className = `message ${sender}`;
    div.innerText = text;
    
    body.appendChild(div);
    body.scrollTop = body.scrollHeight;
}

function appendLoading() {
    const body = document.getElementById('chatbot-body');
    const id = 'loading-' + Date.now();
    
    const div = document.createElement('div');
    div.id = id;
    div.className = 'typing-indicator';
    div.innerHTML = `<span></span><span></span><span></span>`;
    
    body.appendChild(div);
    body.scrollTop = body.scrollHeight;
    return id;
}

function removeElement(id) {
    const el = document.getElementById(id);
    if (el) el.remove();
}

async function loadChatHistory() {
    const token = localStorage.getItem('ai_chat_token');
    try {
        const response = await fetch('/api/history', {
            headers: { 'Authorization': 'Bearer ' + token }
        });
        
        if (response.ok) {
            const history = await response.json();
            const body = document.getElementById('chatbot-body');
            
            if (history && history.length > 0) {
                // Clear default greeting if we have history
                body.innerHTML = ''; 
                
                history.forEach(session => {
                    if(session.message) appendMessage(session.message, 'user');
                    if(session.response) appendMessage(session.response, 'bot');
                });
            }
        }
    } catch(err) {
        console.error("Failed to load history", err);
    }
}
