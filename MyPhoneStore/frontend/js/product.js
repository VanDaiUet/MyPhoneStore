document.addEventListener("DOMContentLoaded", async function () {
    const urlParams = new URLSearchParams(window.location.search);
    const productId = urlParams.get("id");

    if (!productId) {
        console.error("Không tìm thấy ID sản phẩm");
        document.querySelector(".information").innerHTML = "<p>Lỗi: Không tìm thấy sản phẩm.</p>";
        return;
    }

    const apiUrl = `http://localhost:8080/products/${productId}`;

    try {
        const response = await fetch(apiUrl, {
            method: "GET",
            headers: {
                "Content-Type": "application/json"
            }
        });

        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }

        const data = await response.json();
        const product = data.data;

        displayProductDetails(product);

        displayProductReviews(product.reviews);
    } catch (error) {
        console.error("Lỗi khi tải chi tiết sản phẩm:", error);
        document.querySelector(".information").innerHTML = "<p>Lỗi khi tải sản phẩm. Vui lòng thử lại sau.</p>";
    }
});

// Hàm hiển thị thông tin chi tiết sản phẩm
function displayProductDetails(product) {
    document.querySelector(".product-title").textContent = product.name;
    document.querySelector(".product-info p").textContent = `${product.rating.toFixed(1)} ⭐ | ${product.reviews.length} đánh giá`;
    document.querySelector(".price").textContent = `${product.price.toLocaleString()} đ`;
    document.querySelector(".product-description").innerHTML = `
        <p><strong>Mô tả:</strong> ${product.description}</p>
        <p><strong>Nhà sản xuất:</strong> ${product.manufacturer}</p>
        <p><strong>RAM:</strong> ${product.ram} GB</p>
        <p><strong>Bộ nhớ:</strong> ${product.storage} GB</p>
        <p><strong>Màu sắc:</strong> ${product.color}</p>
        <p><strong>Còn lại:</strong> ${product.stock} sản phẩm</p>
    `;
    document.querySelector(".product-image img").src = `../images/${product.imageUrl}`;
    document.querySelector(".product-image img").alt = product.name;
}

// Hàm hiển thị danh sách đánh giá sản phẩm
function displayProductReviews(reviews) {
    const reviewsContainer = document.getElementById("reviews-container");
    reviewsContainer.innerHTML = "";

    if (reviews.length === 0) {
        reviewsContainer.innerHTML = "<p>Chưa có đánh giá nào cho sản phẩm này.</p>";
        return;
    }

    reviews.forEach(review => {
        const reviewElement = document.createElement("div");
        reviewElement.classList.add("review-item");
        reviewElement.innerHTML = `
            <p><strong>${review.username}</strong></p>
            <p class="review-date">(${new Date(review.createdAt).toLocaleDateString()})</p>
            <p>${review.comment}</p>
            <p class="review-rating">Rating: ${review.rating} ⭐</p>
        `;
        reviewsContainer.appendChild(reviewElement);
    });
}

// Hàm cập nhật số lượng sản phẩm
let quantity = 1;

function updateQuantity(change) {
    quantity = Math.max(1, quantity + change);
    document.getElementById("quantity").innerText = quantity;
}

// Hàm lấy token
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

// Hàm thêm sản phẩm vào giỏ hàng
async function addToCart() {
    try {
        let token = localStorage.getItem("token");
        if (!token) {
            token = await fetchToken();
        }

        if (!token) {
            console.error("Không thể lấy token, dừng thêm vào giỏ hàng.");
            alert("Bạn cần đăng nhập để thêm sản phẩm vào giỏ hàng.");
            return;
        }

        const productId = new URLSearchParams(window.location.search).get("id");
        const quantity = parseInt(document.getElementById("quantity").innerText, 10);

        if (!productId) {
            console.error("Không tìm thấy ID sản phẩm.");
            return;
        }

        const response = await fetch("http://localhost:8080/carts/me/products", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${token}`
            },
            body: JSON.stringify({ productId, quantity })
        });

        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }

        alert("Thêm vào giỏ hàng thành công!");
    } catch (error) {
        console.error("Lỗi khi thêm vào giỏ hàng:", error);
        alert(`Có lỗi: ${error.message || "Không xác định"}`);
    }
}