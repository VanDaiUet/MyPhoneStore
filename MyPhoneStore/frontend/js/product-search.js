let currentPage_search = 0;
const pageSize_search = 10;
let currentSort_search = '';
let searchKey_search = '';

async function fetchProducts(page = 0, size = 10, sort = '', searchKey = '') {
    try {
        const response = await fetch(`http://localhost:8080/search?page=${page}&size=${size}&sort=${sort}&keyword=${searchKey}`, {
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

        console.log(products);

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

    if (products.length === 0) {
        productList.innerHTML = '<p style="text-align:center; padding: 20px;">Không tìm thấy sản phẩm</p>';
        return;
    }

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

async function searchProducts() {
    searchKey_search = document.getElementById('search-input').value.trim();

    if (!searchKey_search) {
        alert('Vui lòng nhập từ khóa để tìm kiếm!');
        return;
    }

    // Ẩn phần quảng cáo
    const adsElement = document.querySelector('.ads');
    if (adsElement) {
        adsElement.style.display = 'none';
    }

    fetchProducts(0, pageSize_search, currentSort_search, searchKey_search);
}

function changePage(direction) {
    currentPage_search += direction; // Tăng hoặc giảm trang hiện tại
    fetchProducts(currentPage_search, pageSize_search, currentSort_search, searchKey_search);
}

function updatePaginationButtons(totalPages) {
    const prevButton = document.getElementById('prev-button');
    const nextButton = document.getElementById('next-button');

    prevButton.disabled = currentPage_search === 0;
    nextButton.disabled = currentPage_search >= totalPages - 1;
}

function filterProducts(sortCriteria) {
    currentSort_search = sortCriteria;
    currentPage_search = 0;
    fetchProducts(currentPage_search, pageSize_search, currentSort_search, searchKey_search);
}