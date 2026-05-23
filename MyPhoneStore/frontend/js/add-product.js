// Hàm xử lý khi nhấn nút "Hủy"
function handleCancel() {
    if (confirm("Bạn có chắc muốn hủy việc thêm sản phẩm?")) {
        window.location.href = "adminManageProduct.html";
    }
}


// Hàm xử lý khi người dùng nhấn "Thêm sản phẩm"
document.getElementById("addProductForm").addEventListener("submit", async function (e) {
    e.preventDefault();

    const token = localStorage.getItem("token");

    // Lấy dữ liệu từ form
    const name = document.getElementById("productName").value;
    const price = document.getElementById("productPrice").value;
    const color = document.getElementById("productColor").value;
    const ram = document.getElementById("productRAM").value;
    const storage = document.getElementById("productStorage").value;
    const manufacturer = document.getElementById("productBrand").value;
    const stock = document.getElementById("productStock").value;
    const description = document.getElementById("description").value;
    const imageUrl = document.getElementById("productImageUrl").value;

    // Kiểm tra trường rỗng
    if (!name || !price || !color || !ram || !storage || !manufacturer || !stock || !description || !imageUrl) {
        alert("Vui lòng điền đầy đủ thông tin sản phẩm!");
        return;
    }

    const product = {
        name,
        price,
        color,
        ram,
        storage,
        manufacturer,
        stock,
        description,
        imageUrl
    };

    // Gửi dữ liệu đến server
    try {
        const response = await fetch("http://localhost:8080/products", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${token}`
            },
            body: JSON.stringify(product)
        });

        if (response.ok) {
            alert("Thêm sản phẩm thành công!");
            window.location.href = "adminManageProduct.html";
        } else {
            const data = await response.json();
            alert("Lỗi khi thêm sản phẩm: " + (data.message || "Không rõ lỗi"));
        }
    } catch (error) {
        console.error("Lỗi:", error);
        alert("Có lỗi xảy ra khi gửi dữ liệu.");
    }
});
