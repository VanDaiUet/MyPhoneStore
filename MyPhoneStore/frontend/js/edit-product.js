// Lấy ID sản phẩm từ URL
const urlParams = new URLSearchParams(window.location.search);
const productId = urlParams.get('id');

const form = document.getElementById('editProductForm');

// Load dữ liệu sản phẩm
async function loadProduct() {
    try {
        const response = await fetch(`http://localhost:8080/products/${productId}`);
        const product = (await response.json()).data;

        document.getElementById('productName').value = product.name;
        document.getElementById('productPrice').value = product.price;
        document.getElementById('productColor').value = product.color;
        document.getElementById('productRAM').value = product.ram;
        document.getElementById('productStorage').value = product.storage;
        document.getElementById('productBrand').value = product.manufacturer;
        document.getElementById('productStock').value = product.stock;
        document.getElementById('description').value = product.description;
        document.getElementById('productImageUrl').value = product.imageUrl || "";
    } catch (error) {
        alert("Không thể tải thông tin sản phẩm.");
        console.error(error);
    }
}

// Gửi cập nhật sản phẩm
form.addEventListener('submit', async (e) => {
    e.preventDefault();

    const token = localStorage.getItem("token");

    const product = {
        name: form.name.value,
        price: form.price.value,
        color: form.color.value,
        ram: form.ram.value,
        storage: form.storage.value,
        manufacturer: form.manufacturer.value,
        stock: form.stock.value,
        description: form.description.value,
        imageUrl: document.getElementById("productImageUrl").value
    };

    try {
        const response = await fetch(`http://localhost:8080/products/${productId}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify(product)
        });

        if (response.ok) {
            alert("Cập nhật sản phẩm thành công!");
            window.location.href = 'adminManageProduct.html';
        } else {
            const err = await response.json();
            alert("Lỗi khi cập nhật: " + (err.message || "Không rõ lỗi"));
        }
    } catch (error) {
        alert("Có lỗi khi gửi yêu cầu cập nhật.");
        console.error(error);
    }
});

loadProduct();
