let currentPage = 0;
const pageSize = 10;
let currentSort = '';

async function fetchProducts(page = 0, size = 10, sort = '') {
    try {
        const response = await fetch(`http://localhost:8080/products?page=${page}&size=${size}&sort=${sort}`, {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }

        const apiResponse = await response.json();
        const products = apiResponse.data;

        displayProducts(products);
        updatePaginationButtons(apiResponse.totalPages);
    } catch (error) {
        console.error('Lỗi khi tải sản phẩm:', error);
        document.getElementById('product-list').innerHTML = '<p>Lỗi khi tải sản phẩm. Vui lòng thử lại sau.</p>';
    }
}

function displayProducts(products) {
    const productList = document.getElementById('product-list');
    productList.innerHTML = '';

    products.forEach(product => {
        const productItem = document.createElement('div');
        productItem.classList.add('product');
        productItem.innerHTML = `
            <a href="product.html?id=${product.id}" class="product-link">
                <img src="../images/${product.imageUrl}" alt="${product.name}" class="product-image">
                <div class="product-info">
                    <h3 class="product-name">${product.name}</h3>
                    <p class="product-price">${product.price.toLocaleString()} đ</p>
                    <p class="product-rating">⭐ ${product.rating}</p>
                </div>
            </a>
        `;
        productList.appendChild(productItem);
    });
}

function changePage(direction) {
    currentPage += direction; // Tăng hoặc giảm trang hiện tại
    fetchProducts(currentPage, pageSize, currentSort);
}

function updatePaginationButtons(totalPages) {
    const prevButton = document.getElementById('prev-button');
    const nextButton = document.getElementById('next-button');

    prevButton.disabled = currentPage === 0;
    nextButton.disabled = currentPage >= totalPages - 1;
}

function filterProducts(sortCriteria) {
    currentSort = sortCriteria;
    currentPage = 0;
    fetchProducts(currentPage, pageSize, currentSort);
}

fetchProducts(currentPage, pageSize);