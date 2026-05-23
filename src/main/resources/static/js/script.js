// General Scripts for Navigation & Auth Modals

document.addEventListener("DOMContentLoaded", () => {
    checkAuthState();
});

function checkAuthState() {
    const token = localStorage.getItem('ai_chat_token');
    const userName = localStorage.getItem('ai_chat_name');
    const authNav = document.getElementById('nav-auth');
    if (!authNav) return;

    if (token && userName) {
        authNav.innerHTML = `
            <span style="margin-right: 15px; color: var(--text-secondary);">Hi, ${userName}</span>
            <button class="btn-outline" onclick="logout()">Logout</button>
        `;
    } else {
        authNav.innerHTML = `
            <button class="btn-outline" onclick="openLoginModal()" style="margin-right: 10px;">Login</button>
            <button class="btn-primary" onclick="openRegisterModal()">Register</button>
            
            <!-- Auth Modals -->
            <div id="auth-modal" class="modal">
                <div class="modal-content">
                    <span class="close-modal" onclick="closeModal()">&times;</span>
                    <h2 id="modal-title">Login</h2>
                    
                    <form id="auth-form" onsubmit="handleAuthSubmit(event)">
                        <div id="name-field" class="form-group" style="display:none;">
                            <label>Name</label>
                            <input type="text" id="auth-name" placeholder="John Doe">
                        </div>
                        <div class="form-group">
                            <label>Email</label>
                            <input type="email" id="auth-email" placeholder="john@example.com" required>
                        </div>
                        <div class="form-group">
                            <label>Password</label>
                            <input type="password" id="auth-password" placeholder="********" required>
                        </div>
                        <button type="submit" class="btn-primary" style="width: 100%; margin-top: 10px;">Submit</button>
                    </form>
                    <div class="switch-modal" id="modal-switch" onclick="switchModalMode()">
                        Don't have an account? Register
                    </div>
                </div>
            </div>
        `;
    }
}

let isLoginMode = true;

function openLoginModal() {
    isLoginMode = true;
    updateModalUI();
    document.getElementById('auth-modal').classList.add('active');
}

function openRegisterModal() {
    isLoginMode = false;
    updateModalUI();
    document.getElementById('auth-modal').classList.add('active');
}

function closeModal() {
    document.getElementById('auth-modal').classList.remove('active');
}

function switchModalMode() {
    isLoginMode = !isLoginMode;
    updateModalUI();
}

function updateModalUI() {
    document.getElementById('modal-title').innerText = isLoginMode ? 'Login' : 'Register';
    document.getElementById('name-field').style.display = isLoginMode ? 'none' : 'flex';
    document.getElementById('modal-switch').innerText = isLoginMode ? "Don't have an account? Register" : "Already have an account? Login";
}

async function handleAuthSubmit(e) {
    e.preventDefault();
    const email = document.getElementById('auth-email').value;
    const password = document.getElementById('auth-password').value;
    const name = document.getElementById('auth-name').value;
    
    const url = isLoginMode ? '/api/login' : '/api/register';
    const body = isLoginMode ? { email, password } : { name, email, password };
    
    try {
        const response = await fetch(url, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(body)
        });
        
        const data = await response.json();
        
        if (response.ok) {
            localStorage.setItem('ai_chat_token', data.token);
            localStorage.setItem('ai_chat_name', data.name);
            closeModal();
            checkAuthState();
            
            // Re-render chatbot if available
            if (typeof renderChatbotUI === "function") {
                renderChatbotUI();
            }
        } else {
            alert(data.message || 'Authentication failed');
        }
    } catch (err) {
        console.error(err);
        alert('An error occurred during authentication');
    }
}

function logout() {
    localStorage.removeItem('ai_chat_token');
    localStorage.removeItem('ai_chat_name');
    checkAuthState();
    if (typeof renderChatbotUI === "function") {
        renderChatbotUI();
    }
}
