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

document.getElementById("change-password-button").addEventListener("click", async function () {
    const oldPassword = document.getElementById("old-password").value;
    const confirmOldPassword = document.getElementById("confirm-old-password").value;
    const newPassword = document.getElementById("new-password").value;
    const token = localStorage.getItem("token");

    if (!oldPassword || !confirmOldPassword || !newPassword) {
        alert("Vui lòng nhập đầy đủ thông tin!");
        return;
    }

    if (oldPassword !== confirmOldPassword) {
        alert("Mật khẩu cũ không khớp, vui lòng kiểm tra lại!");
        return;
    }

    try {
        const response = await fetch("http://localhost:8080/users/me/password", {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${token}`
            },
            body: JSON.stringify({
                oldPassword: oldPassword,
                newPassword: newPassword
            })
        });

        if (!response.ok) {
            const errorData = await response.json();
            throw new Error(errorData.message || "Lỗi không xác định");
        }

        alert("Đổi mật khẩu thành công!");
        window.location.href = "account.html";
    } catch (error) {
        console.error("Lỗi khi đổi mật khẩu:", error);
        alert(`Lỗi: ${error.message}`);
    }
});