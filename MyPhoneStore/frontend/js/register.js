async function signup() {
    const name = document.getElementById("name").value;
    const email = document.getElementById("email").value;
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;
    const registerButton = document.getElementById("register-button");
    
    if (!name || !email || !username || !password) {
        alert("Vui lòng nhập đầy đủ thông tin!");
        return;
    }

    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(email)) {
        alert("Email không hợp lệ! Vui lòng nhập đúng định dạng (ví dụ: example@gmail.com).");
        return;
    }
    
    registerButton.disabled = true;
    
    try {
        const response = await fetch(" http://localhost:8080/register", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                name: name,
                username: username,
                password: password,
                email: email
            })
        });
        
        const data = await response.json();
        
        if (response.ok && data.success) {
            alert("Đăng ký thành công! Vui lòng đăng nhập.");
            window.location.href = "login.html";
        } else {
            alert(data.message || "Đăng ký thất bại!");
        }
    } catch (error) {
        alert("Lỗi kết nối, vui lòng thử lại sau!");
        console.error("Lỗi:", error);
    }
    
    registerButton.disabled = false;
}
