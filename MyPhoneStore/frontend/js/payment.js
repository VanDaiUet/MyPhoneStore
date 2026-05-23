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
    const apiUrl = "http://localhost:8080/purchase";

    try {
        const token = localStorage.getItem("token");
        if (!token) {
            throw new Error("Token không tồn tại. Vui lòng đăng nhập lại.");
        }

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

        const data = await response.json();
        const order = data.data;

        // Hiển thị danh sách sản phẩm
        const orderItemsContainer = document.getElementById("order-items");
        order.orderItemDTOList.forEach((item, index) => {
            const row = document.createElement("tr"); // Tạo một dòng mới
            row.innerHTML = `
                <td>${index + 1}</td>
                <td>${item.productName}</td>
                <td>${item.price.toLocaleString()} đ</td>
                <td>${item.quantity}</td>
            `;
            orderItemsContainer.appendChild(row);
        });

        document.getElementById("num-products").textContent = `Số lượng sản phẩm: ${order.numProducts}`;

        document.getElementById("total-price").textContent = `Tổng giá trị đơn hàng: ${order.totalPrice.toLocaleString()} đ`;

        document.getElementById("address").value = order.address || "";
        document.getElementById("phone").value = order.phoneNumber || "";

    } catch (error) {
        console.error("Lỗi khi tải thông tin đơn hàng:", error);
    }

    document.getElementById("submit-order").addEventListener("click", async function () {
        const address = document.getElementById("address").value;
        const phoneNumber = document.getElementById("phone").value;

        if (!address || !phoneNumber) {
            alert("Vui lòng nhập đầy đủ địa chỉ và số điện thoại!");
            return;
        }

        try {
            const response = await fetch("http://localhost:8080/submitOrder", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    "Authorization": `Bearer ${localStorage.getItem("token")}`
                },
                body: JSON.stringify({
                    address: address,
                    phoneNumber: phoneNumber
                })
            });

            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }

            const result = await response.json();
            const returnUrl = result.data.return_url;

            if (returnUrl) {
                window.location.href = returnUrl;
            } else {
                alert("Không nhận được URL chuyển hướng từ server.");
            }
        } catch (error) {
            console.error("Lỗi khi gửi đơn hàng:", error);
            alert("Đã xảy ra lỗi khi xử lý đơn hàng. Vui lòng thử lại!");
        }
    });
});
