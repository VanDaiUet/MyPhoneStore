# 📱 MyPhoneStore

Website bán điện thoại với kiến trúc tách lớp:
- **Backend**: Spring Boot 3 (Java 21), JWT, PostgreSQL, Elasticsearch
- **Frontend**: HTML/CSS/JavaScript thuần, phục vụ bằng Nginx
- **Hạ tầng**: Docker Compose (frontend + backend + PostgreSQL + Elasticsearch)

---

## 1) Tổng quan dự án

Repository chính:
- `MyPhoneStore/backend`: API server
- `MyPhoneStore/frontend`: giao diện web tĩnh
- `MyPhoneStore/compose.yaml`: chạy toàn bộ hệ thống bằng Docker

Các chức năng nổi bật:
- Đăng ký / đăng nhập JWT
- Quản lý sản phẩm (xem, thêm, sửa, xóa)
- Giỏ hàng và đặt hàng
- Đánh giá sản phẩm sau mua
- Tìm kiếm sản phẩm qua Elasticsearch
- Quản trị người dùng/đơn hàng

---

## 2) Công nghệ sử dụng

### Backend
- Java 21
- Spring Boot 3.4.x
- Spring Security + JWT (`jjwt`)
- Spring Data JPA
- PostgreSQL
- Spring Data Elasticsearch
- MapStruct, Lombok

### Frontend
- HTML / CSS / JavaScript
- Nginx (container web server)

### DevOps
- Docker + Docker Compose

---

## 3) Cấu trúc thư mục

```
MyPhoneStore/
├─ compose.yaml
├─ backend/
│  ├─ pom.xml
│  └─ src/main/
│     ├─ java/com/nwdy/phonevip/
│     │  ├─ controller/
│     │  ├─ service/
│     │  ├─ repository/
│     │  ├─ security/
│     │  └─ config/
│     └─ resources/
│        ├─ application.yaml
│        ├─ data.sql
│        └─ secrets.properties
└─ frontend/
	├─ html/
	├─ css/
	├─ js/
	├─ images/
	├─ default.conf
	└─ Dockerfile
```

---

## 4) Yêu cầu môi trường

Để chạy bằng Docker:
- Docker Desktop
- Docker Compose

Để chạy local (không Docker):
- JDK 21
- Maven 3.9+
- PostgreSQL 17+
- Elasticsearch 8+

---

## 5) Chạy nhanh bằng Docker

Từ thư mục `MyPhoneStore/`, chạy:

```bash
docker compose up --build
```

Sau khi chạy thành công:
- Frontend: `http://localhost:80`
- Backend API: `http://localhost:8080`
- PostgreSQL: `localhost:5432`
- Elasticsearch: `http://localhost:9200`

> Backend phụ thuộc DB + Elasticsearch (đã khai báo healthcheck trong `compose.yaml`).

---

## 6) Chạy local từng phần

### 6.1 Backend
1. Mở thư mục `MyPhoneStore/backend`
2. Đảm bảo PostgreSQL + Elasticsearch đang chạy
3. Cấu hình biến trong `src/main/resources/secrets.properties`
4. Chạy ứng dụng:

```bash
mvn spring-boot:run
```

Backend dùng `application.yaml` và import `secrets.properties`.

### 6.2 Frontend
- Frontend là static files trong `MyPhoneStore/frontend/html`.
- Có thể mở trực tiếp file HTML hoặc chạy bằng Nginx container (qua Docker Compose).

---

## 7) Cấu hình quan trọng

### Backend `application.yaml`
- Datasource: PostgreSQL
- JPA: `ddl-auto: create-drop` (local)
- SQL init: `data.sql` luôn được nạp
- Elasticsearch URI lấy từ biến môi trường / `secrets.properties`

### File cấu hình bí mật
- Mẫu cấu hình: `MyPhoneStore/backend/src/main/resources/sample.secrets.properties`
- File thật để chạy local: `MyPhoneStore/backend/src/main/resources/secrets.properties`
- File `secrets.properties` đã được đưa vào `.gitignore`, nên không nên commit lên repository
- Cách dùng nhanh: copy `sample.secrets.properties` thành `secrets.properties` rồi điền giá trị thật cho môi trường của bạn

## 8) API chính

### Auth
- `POST /register`
- `POST /login`

### Product
- `GET /products`
- `GET /products/{id}`
- `POST /products`
- `PUT /products/{id}`
- `DELETE /products/{id}`

### Search
- `GET /search?keyword=...&page=...&size=...&sort=...`

### Cart
- `GET /carts/me`
- `POST /carts/me/products`
- `PUT /carts/me/cartItems/{cartItemId}`
- `DELETE /carts/me/cartItems/{cartItemId}`

### Order & Payment
- `GET /purchase`
- `GET /orders/history`
- `GET /orders/{orderId}`
- `GET /orders` (admin)
- `POST /submitOrder`
- `GET /ipn` (VNPay callback)

### User
- `GET /users/me`
- `PUT /users/me`
- `PUT /users/me/password`
- `GET /users` (admin)
- `GET /users/{id}` (admin)
- `PUT /users/{id}` (admin)
- `DELETE /users/{id}` (admin)

### Review
- `POST /reviews`

---

## 9) Dữ liệu mẫu

`backend/src/main/resources/data.sql` đã seed:
- Role: `USER`, `ADMIN`
- Người dùng mẫu
- ~100 sản phẩm
- Giỏ hàng mẫu

Bạn có thể dùng dữ liệu này để demo nhanh UI/API sau khi khởi động backend.

---

## 10) Lưu ý khi phát triển

- Frontend đang gọi API cứng `http://localhost:8080` trong nhiều file JS.
- Cần bật backend trước khi thao tác frontend.
- Tìm kiếm phụ thuộc Elasticsearch; nếu ES chưa sẵn sàng, endpoint `/search` có thể lỗi.

---

## 11) Tác giả & giấy phép

- License: xem file `MyPhoneStore/LICENSE`

Nếu bạn muốn, mình có thể tiếp tục tạo thêm:
- README tiếng Anh
- phần ảnh demo màn hình
- file `.env.example` cho backend để tách secrets khỏi source code.
