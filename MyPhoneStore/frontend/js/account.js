async function fetchToken() {
    try {
        const response = await fetch("http://localhost:8080/token", {
            method: "POST",
            headers: { "Content-Type": "application/json" }
        });
        
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
        
        const data = await response.json();
        localStorage.setItem("token", data.data.token);
        return data.data.token;
    } catch (error) {
        console.error("Lỗi khi lấy token:", error);
        return null;
    }
}

document.addEventListener("DOMContentLoaded", async function () {
    let token = localStorage.getItem("token");
    if (!token) {
        token = await fetchToken();
    }
    
    if (!token) {
        console.error("Không thể lấy token, dừng tải dữ liệu người dùng.");
        return;
    }
    
    const apiUrl = "http://localhost:8080/users/me";
    
    try {
        const response = await fetch(apiUrl, {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${token}`
            }
        });
        
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
        
        const userData = await response.json();
        
        document.getElementById("username").textContent = userData.data.username;
        document.getElementById("name").value = userData.data.name;
        document.getElementById("email").value = userData.data.email;
        document.getElementById("phoneNumber").value = userData.data.phoneNumber;
        document.getElementById("address").value = userData.data.address;
    } catch (error) {
        console.error("Lỗi khi tải thông tin người dùng:", error);
        if (error.message && error.message.includes('401')) {
            alert("Bạn chưa đăng nhập, hãy đăng nhập để sử dụng tính năng này.");
        } else {
            alert(`Có lỗi ${error.message || "Không xác định"}`);
        }
    }
});

// Hàm thay đổi thông tin
document.getElementById("save-button").addEventListener("click", async function () {
    const name = document.getElementById("name").value;
    const username = document.getElementById("username").textContent.trim();
    const email = document.getElementById("email").value;
    const phoneNumber = document.getElementById("phoneNumber").value;
    const address = document.getElementById("address").value;
    const token = localStorage.getItem("token");

    try {
        const response = await fetch("http://localhost:8080/users/me", {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${token}`
            },
            body: JSON.stringify({
                name: name,
                username: username,
                email: email,
                phoneNumber: phoneNumber,
                address: address
            })
        });
        
        const responseData = await response.json();
                
        if (!response.ok) {
            throw new Error(`Lỗi cập nhật: ${responseData.message || response.status}`);
        }
                
        alert("Thông tin đã được cập nhật thành công!");
    } catch (error) {
        console.error("Lỗi khi cập nhật thông tin người dùng:", error.message || error);
        alert(`Có lỗi xảy ra khi cập nhật thông tin: ${error.message || "Không xác định"}`);
    }
});