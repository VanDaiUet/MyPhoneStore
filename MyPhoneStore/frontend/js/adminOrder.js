// Hàm gọi API để lấy danh sách đơn hàng
async function fetchOrders() {
    const token = localStorage.getItem("token");
    try {
        const response = await fetch("http://localhost:8080/orders", {
            method: "GET",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${token}`
            }
        });
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
        const apiResponse = await response.json();
        const orders = apiResponse.data;

        console.log(orders);
        populateOrderTable(orders);
    } catch (error) {
        console.error("Lỗi khi tải danh sách đơn hàng:", error);
    }
}

// Hàm hiển thị dữ liệu vào bảng danh sách đơn hàng
function populateOrderTable(orders) {
    const tbody = document.querySelector("table tbody");
    tbody.innerHTML = "";

    orders.forEach(order => {
        const row = document.createElement("tr");
        row.innerHTML = `
            <td>${order.orderId}</td>
            <td>${order.name}</td>
            <td>${order.orderCode}</td>
            <td>${order.totalPrice.toLocaleString()} đ</td>
            <td>${order.paymentStatus}</td>
            <td>${order.transactionNo}</td>
            <td>${new Date(order.createdAt).toLocaleDateString()}</td>
        `;
        tbody.appendChild(row);
    });
}

document.addEventListener("DOMContentLoaded", fetchOrders);