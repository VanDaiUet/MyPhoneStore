let currentPage = 0;
const pageSize = 10;

async function fetchProducts(page = 0) {
    try {
        const response = await fetch(`http://localhost:8080/products?page=${page}&size=${pageSize}&sort=id,asc`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (!response.ok) throw new Error(`HTTP error! Status: ${response.status}`);

        const data = await response.json();
        displayProducts(data.data);  // danh sách sản phẩm
        renderPagination();          // hiển thị phân trang
    } catch (error) {
        console.error('Lỗi khi tải sản phẩm:', error);
        document.getElementById('product-table-body').innerHTML =
            '<tr><td colspan="10">Lỗi khi tải sản phẩm. Vui lòng thử lại sau.</td></tr>';
    }
}

function displayProducts(products) {
    const tableBody = document.getElementById('product-table-body');
    tableBody.innerHTML = '';

    products.forEach(product => {
        if (!product.id || !product.name || !product.imageUrl || !product.price) return;

        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${product.id}</td>
            <td>${product.name}</td>
            <td>${product.stock || 'N/A'}</td>
            <td>${product.color || 'N/A'}</td>
            <td>${product.ram || 'N/A'}</td>
            <td>${product.storage || 'N/A'}</td>
            <td>${product.manufacturer || 'N/A'}</td>
            <td>${Number(product.price).toLocaleString()} đ</td>
            <td><img src="../images/${product.imageUrl}" alt="${product.name}" width="60"></td>
            <td>
                <button onclick="editProduct(${product.id})">✏️ Sửa</button>
                <button onclick="deleteProduct(${product.id})">🗑️ Xóa</button>
            </td>
        `;
        tableBody.appendChild(row);
    });
}

function renderPagination() {
    const pagination = document.getElementById('pagination');
    pagination.innerHTML = `
        <button onclick="changePage(${currentPage - 1})" ${currentPage === 0 ? 'disabled' : ''}>⬅️</button>
        <span>Trang <strong>${currentPage + 1}</strong></span>
        <button onclick="changePage(${currentPage + 1})">➡️</button>
        <input type="number" id="goToPage" placeholder="Trang..." min="1" />
        <button onclick="goToPage()">Đi</button>
    `;
}

function changePage(page) {
    if (page < 0) return;
    currentPage = page;
    fetchProducts(currentPage);
}

function goToPage() {
    const input = document.getElementById("goToPage");
    const page = parseInt(input.value);
    if (isNaN(page) || page < 1) {
        alert("Vui lòng nhập số trang hợp lệ (>= 1)");
        return;
    }
    currentPage = page - 1;
    fetchProducts(currentPage);
}

async function deleteProduct(id) {
    const confirmDelete = confirm("Bạn có chắc chắn muốn xóa sản phẩm này?");
    if (!confirmDelete) return;

    const token = localStorage.getItem("token");
    try {
        const response = await fetch(`http://localhost:8080/products/${id}`, {
            method: 'DELETE',
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });

        if (response.ok) {
            alert("Xóa sản phẩm thành công!");
            fetchProducts(currentPage);
        } else {
            const errorData = await response.json();
            alert("Lỗi khi xóa sản phẩm: " + errorData.message);
        }
    } catch (error) {
        console.error("Lỗi khi gửi yêu cầu xóa:", error);
        alert("Xảy ra lỗi khi xóa sản phẩm.");
    }
}

function editProduct(id) {
    window.location.href = `editProduct.html?id=${id}`;
}

fetchProducts(currentPage);
