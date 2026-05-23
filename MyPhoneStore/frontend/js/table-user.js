async function fetchUser() {
    const token = localStorage.getItem("token");
    try {
        const response = await fetch('http://localhost:8080/users', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            }
        });
        
        if (!response.ok) {
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
        
        const data = await response.json();
        displayUser(data.data);
    } catch (error) {
        console.error('Lỗi khi tải User:', error);
        const tableBody = document.getElementById('user-table-body');
        tableBody.innerHTML = '<tr><td colspan="9">Lỗi khi tải User. Vui lòng thử lại sau.</td></tr>';
    }
}

function displayUser(users) {
    const tableBody = document.getElementById('user-table-body');
    tableBody.innerHTML = '';

    users.sort((a, b) => a.id - b.id);
    users.forEach(user => {
        if (!user.id || !user.name || !user.username || !user.email) {
            console.warn('Bỏ qua user thiếu dữ liệu:', user);
            return;
        }

        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${user.id}</td>
            <td>${user.username}</td>
            <td>${user.name || 'N/A'}</td>
            <td>${user.email || 'N/A'}</td>
            <td>
                <button onclick="deleteuser(${user.id})">🗑️Xóa</button>
            </td>
        `;
        tableBody.appendChild(row);
    });
}

fetchUser();

async function deleteuser(id) {
    const confirmDelete = confirm("Bạn có chắc chắn muốn xóa user này?");
    if (!confirmDelete) return;

    const token = localStorage.getItem("token");
    try {
        const response = await fetch(`http://localhost:8080/users/${id}`, {
            method: 'DELETE',
            headers: {
                'Authorization': `Bearer ${token}`
            }
        });

        if (response.ok) {
            alert("Xóa user thành công!");
            fetchUser();
        } else {
            const errorData = await response.json();
            alert("Lỗi khi xóa user: " + errorData.message);
        }
    } catch (error) {
        console.error("Lỗi khi gửi yêu cầu xóa:", error);
        alert("Xảy ra lỗi khi xóa user.");
    }
}

