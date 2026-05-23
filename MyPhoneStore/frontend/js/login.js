async function login() {
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;
    const loginButton = document.getElementById("login-button");

    if (!username || !password) {
        alert("Vui lòng nhập đầy đủ thông tin!");
        return;
    }

    loginButton.disabled = true;

    try {
        const response = await fetch("http://localhost:8080/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                username: username,
                password: password
            })
        });

        const data = await response.json();

        if (response.ok && data.data && data.data.token) {
            const token = data.data.token;

            localStorage.setItem("token", token);

            // Giải mã phần payload của JWT để lấy vai trò
            const payload = JSON.parse(atob(token.split('.')[1]));

            // Tách chuỗi scp thành mảng
            let roles = [];
            if (payload.scp) {
                roles = payload.scp.replace("[", "").replace("]", "").split(",").map(r => r.trim());
            }

            if (roles.length > 1) {
                // Nếu có nhiều vai trò, hiển thị cửa sổ chọn vai trò
                showRoleSelection(roles, token);
            } else if (roles.includes("ROLE_ADMIN")) {
                alert("ADMIN Đăng nhập thành công!");
                window.location.href = "adminManageProduct.html";
            } else if (roles.includes("ROLE_USER")) {
                alert("USER Đăng nhập thành công!");
                window.location.href = "index.html";
            }
        } else {
            alert(data.message || "Đăng nhập thất bại!");
        }
    } catch (error) {
        alert("Lỗi kết nối, vui lòng thử lại sau!");
        console.error("Lỗi:", error);
    }

    loginButton.disabled = false;
}

function showRoleSelection(roles, token) {
    const roleSelectionModal = document.createElement("div");
    roleSelectionModal.className = "role-selection-modal";
    roleSelectionModal.innerHTML = `
        <div class="modal-content">
            <h3>Chọn vai trò để đăng nhập</h3>
            ${roles
                .map(role => {
                    const roleName = role === "ROLE_ADMIN" ? "Quản trị viên" : "Người dùng";
                    return `<button onclick="handleRoleSelection('${role}', '${token}')">${roleName}</button>`;
                })
                .join("")}
            <button class="cancel-button" onclick="closeRoleSelection()">Hủy</button>
        </div>
    `;
    document.body.appendChild(roleSelectionModal);
}

function closeRoleSelection() {
    const modal = document.querySelector(".role-selection-modal");
    if (modal) {
        modal.remove();
    }
}

function handleRoleSelection(role, token) {
    if (role === "ROLE_ADMIN") {
        alert("ADMIN Đăng nhập thành công!");
        window.location.href = "adminManageProduct.html";
    } else if (role === "ROLE_USER") {
        alert("USER Đăng nhập thành công!");
        window.location.href = "index.html";
    }
    localStorage.setItem("token", token);
}