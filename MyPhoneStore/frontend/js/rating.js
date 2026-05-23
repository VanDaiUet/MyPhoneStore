// Hàm lấy thông tin chi tiết đơn hàng và hiển thị sản phẩm để đánh giá
async function loadOrderDetails() {
    const urlParams = new URLSearchParams(window.location.search);
    const orderId = urlParams.get('orderId');
    document.getElementById('order-id').textContent = orderId;

    try {
        const token = localStorage.getItem("token");
        const response = await fetch(`http://localhost:8080/orders/${orderId}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            }
        });

        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }

        const apiResponse = await response.json();
        const orderItems = apiResponse.data;

        console.log(orderItems);

        displayOrderItems(orderItems);
    } catch (error) {
        console.error('Lỗi khi tải chi tiết đơn hàng:', error);
        alert('Không thể tải chi tiết đơn hàng. Vui lòng thử lại sau.');
    }
}

// Hàm hiển thị sản phẩm trong đơn hàng
function displayOrderItems(orderItems) {
    const ratingContainer = document.getElementById('rating-container');
    ratingContainer.innerHTML = '';

    orderItems.forEach(item => {
        const ratingItem = document.createElement('div');
        ratingItem.classList.add('rating-item');
        ratingItem.innerHTML = `
            <img src="../images/${item.productImageUrl}" alt="${item.productName}" class="product-image">
            <div class="product-info">
                <p><strong>${item.productName}</strong></p>
                <p>${item.productPrice.toLocaleString()} đ</p>
                <p>Rating: 
                    <select class="rating-select" data-order-item-id="${item.orderItemId}">
                        <option value="1">1 ⭐</option>
                        <option value="2">2 ⭐⭐</option>
                        <option value="3">3 ⭐⭐⭐</option>
                        <option value="4">4 ⭐⭐⭐⭐</option>
                        <option value="5">5 ⭐⭐⭐⭐⭐</option>
                    </select>
                </p>
                <p>Bình luận:</p>
                <textarea class="comment-input" data-order-item-id="${item.orderItemId}" rows="4" placeholder="Nhập bình luận của bạn..."></textarea>
                <button class="submit-rating-button" data-order-item-id="${item.orderItemId}">Đánh giá</button>
            </div>
        `;
        ratingContainer.appendChild(ratingItem);
    });

    // Gắn sự kiện cho các nút "Đánh giá"
    document.querySelectorAll('.submit-rating-button').forEach(button => {
        button.addEventListener('click', async (event) => {
            const orderItemId = event.target.getAttribute('data-order-item-id');
            await submitSingleRating(orderItemId);
        });
    });
}

// Hàm gửi đánh giá
async function submitSingleRating(orderItemId) {
    const token = localStorage.getItem("token");

    // Lấy thông tin đánh giá từ giao diện
    const ratingSelect = document.querySelector(`.rating-select[data-order-item-id="${orderItemId}"]`);
    const commentInput = document.querySelector(`.comment-input[data-order-item-id="${orderItemId}"]`);

    const rating = parseInt(ratingSelect.value);
    const comment = commentInput.value;

    const ratingData = {
        orderItemId: parseInt(orderItemId),
        rating: rating,
        comment: comment
    };

    console.log("Dữ liệu gửi đi:", JSON.stringify(ratingData, null, 2));

    try {
        const response = await fetch('http://localhost:8080/reviews', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify(ratingData)
        });

        const responseData = await response.json();
        console.log("Phản hồi từ backend:", responseData);

        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }

        alert(`Đánh giá cho sản phẩm đã được gửi thành công!`);

        // Xóa sản phẩm đã đánh giá khỏi giao diện
        const ratingItem = document.querySelector(`.rating-item .submit-rating-button[data-order-item-id="${orderItemId}"]`).closest('.rating-item');
        if (ratingItem) {
            ratingItem.remove();
        }
    } catch (error) {
        console.error(`Lỗi khi gửi đánh giá cho sản phẩm ${orderItemId}:`, error);
        if (error.message && error.message.includes('400')) {
            alert('Bạn đã thực hiện đánh giá sản phẩm này trước đây rồi.');
        } else {
            alert(`Không thể gửi đánh giá cho sản phẩm ${orderItemId}. Vui lòng thử lại sau.`);
        }
    }
}

function goBack() {
    window.history.back();
}

loadOrderDetails();