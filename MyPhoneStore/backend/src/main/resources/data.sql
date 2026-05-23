INSERT INTO users (name, username, password, email) VALUES
    ('Nguyen Van A', 'user1', '$2a$12$JD3u4hzacca0u0i2fbdFGOqcczJk5rjiyba5tiUHzXFtNotbFrhTu', 'user1_pw_123456@example.com'),
    ('Nguyen Van B', 'user2', '$2a$12$Z4DpAosxp15SMX5Bu3z5GejVfkKyOCA4lULYclHZ8pDM7IKvoqdga', 'user2_pw_12345678@example.com');

INSERT INTO roles (name, description) VALUES
    ('USER', 'A user who purchases products'),
    ('ADMIN', 'An administrator who manages and sells products');

INSERT INTO user_roles (user_id, role_name) VALUES
    (1, 'USER'),
    (1, 'ADMIN'),
    (2, 'USER');

INSERT INTO products (name, price, image_url, description, stock, manufacturer, ram, storage, color, rating, created_at, updated_at) VALUES
-- 01-05
    ('Samsung Galaxy S25 5G 12GB/256GB', 19990000, '1.jpg', 'Mô tả chi tiết (bổ sung sau)', 100, 'Samsung', 12, 256, 'white', 4.8, '2025-03-01 10:50:24', '2025-03-01 10:50:24'),
    ('Samsung Galaxy S25 Ultra 5G 12GB/256GB', 30990000, '2.jpg', 'Mô tả chi tiết (bổ sung sau)', 150, 'Samsung', 12, 256, 'black', 4.7, '2025-03-01 11:00:00', '2025-03-01 11:00:00'),
    ('Samsung Galaxy S24 Ultra 5G 12GB/256GB', 25990000, '3.jpg', 'Mô tả chi tiết (bổ sung sau)', 200, 'Samsung', 12, 256, 'blue', 5, '2025-03-02 09:30:15', '2025-03-02 09:30:15'),
    ('Samsung Galaxy A16 5G 8GB/256GB', 6490000, '4.jpg', 'Mô tả chi tiết (bổ sung sau)', 200, 'Samsung', 8, 256, 'red', 5, '2025-03-02 10:10:25', '2025-03-02 10:10:25'),
    ('Samsung Galaxy Z Fold6 5G 12GB/256GB', 37090000, '5.jpg', 'Mô tả chi tiết (bổ sung sau)', 80, 'Samsung', 12, 256, 'green', 5, '2025-03-02 11:15:35', '2025-03-02 11:15:35'),
-- 06-10
    ('Samsung Galaxy A06 4GB/64GB', 2890000, '6.jpg', 'Mô tả chi tiết (bổ sung sau)', 100, 'Samsung', 4, 64, 'white', 4.9, '2025-03-01 10:50:24', '2025-03-01 10:50:24'),
    ('Samsung Galaxy A35 5G 8GB/256GB', 8090000, '7.jpg', 'Mô tả chi tiết (bổ sung sau)', 150, 'Samsung', 8, 256, 'black', 4.8, '2025-03-01 11:00:00', '2025-03-01 11:00:00'),
    ('Samsung Galaxy A05s 6GB/128GB', 3690000, '8.jpg', 'Mô tả chi tiết (bổ sung sau)', 200, 'Samsung', 6, 128, 'blue', 5, '2025-03-02 09:30:15', '2025-03-02 09:30:15'),
    ('Samsung Galaxy S24 FE 5G 8GB/256GB', 18490000, '9.jpg', 'Mô tả chi tiết (bổ sung sau)', 200, 'Samsung', 8, 256, 'red', 5, '2025-03-02 10:10:25', '2025-03-02 10:10:25'),
    ('Samsung Galaxy A25 5G 8GB/128GB', 6190000, '10.jpg', 'Mô tả chi tiết (bổ sung sau)', 80, 'Samsung', 8, 128, 'green', 4.6, '2025-03-02 11:15:35', '2025-03-02 11:15:35'),
-- 11-15
    ('Samsung Galaxy S24 5G 8GB/256GB', 17990000, '11.jpg', 'Mô tả chi tiết (bổ sung sau)', 100, 'Samsung', 8, 256, 'white', 4.7, '2025-03-01 10:50:24', '2025-03-01 10:50:24'),
    ('Samsung Galaxy S24+ 5G 12GB/256GB', 18990000, '12.jpg', 'Mô tả chi tiết (bổ sung sau)', 150, 'Samsung', 12, 256, 'black', 5, '2025-03-01 11:00:00', '2025-03-01 11:00:00'),
    ('Samsung Galaxy A55 5G 12GB/256GB', 10890000, '13.jpg', 'Mô tả chi tiết (bổ sung sau)', 200, 'Samsung', 12, 256, 'blue', 4.7, '2025-03-02 09:30:15', '2025-03-02 09:30:15'),
    ('Samsung Galaxy M35 5G 8GB/256GB', 7990000, '14.jpg', 'Mô tả chi tiết (bổ sung sau)', 200, 'Samsung', 8, 256, 'red', 4.5, '2025-03-02 10:10:25', '2025-03-02 10:10:25'),
    ('Samsung Galaxy M15 5G 6GB/128GB', 4490000, '15.jpg', 'Mô tả chi tiết (bổ sung sau)', 80, 'Samsung', 6, 128, 'green', 4.6, '2025-03-02 11:15:35', '2025-03-02 11:15:35'),
-- 16-20
    ('Samsung Galaxy A16 8GB/128GB', 5390000, '16.jpg', 'Mô tả chi tiết (bổ sung sau)', 100, 'Samsung', 8, 128, 'white', 4.7, '2025-03-01 10:50:24', '2025-03-01 10:50:24'),
    ('Samsung Galaxy Z Flip6 5G 12GB/256GB', 21350000, '17.jpg', 'Mô tả chi tiết (bổ sung sau)', 150, 'Samsung', 12, 256, 'black', 4.6, '2025-03-01 11:00:00', '2025-03-01 11:00:00'),
    ('Samsung Galaxy S25 Plus 5G 12GB/256GB', 23990000, '18.jpg', 'Mô tả chi tiết (bổ sung sau)', 200, 'Samsung', 12, 256, 'blue', 5, '2025-03-02 09:30:15', '2025-03-02 09:30:15'),
    ('Samsung Galaxy S25 5G 12GB/512GB', 23490000, '19.jpg', 'Mô tả chi tiết (bổ sung sau)', 200, 'Samsung', 12, 512, 'red', 4.8, '2025-03-02 10:10:25', '2025-03-02 10:10:25'),
    ('Samsung Galaxy S25 Ultra 5G 12GB/512GB', 34450000, '20.jpg', 'Mô tả chi tiết (bổ sung sau)', 80, 'Samsung', 12, 512, 'green', 4.9, '2025-03-02 11:15:35', '2025-03-02 11:15:35'),
-- 21-25
    ('Samsung Galaxy S25 Ultra 5G 12GB/1TB', 41790000, '21.jpg', 'Mô tả chi tiết (bổ sung sau)', 100, 'Samsung', 12, 1024, 'white', 4.9, '2025-03-01 10:50:24', '2025-03-01 10:50:24'),
    ('Samsung Galaxy S24 Ultra 5G 12GB/512GB', 28590000, '22.jpg', 'Mô tả chi tiết (bổ sung sau)', 150, 'Samsung', 12, 512, 'black', 4.8, '2025-03-01 11:00:00', '2025-03-01 11:00:00'),
    ('Samsung Galaxy A16 5G 8GB/128GB', 5890000, '23.jpg', 'Mô tả chi tiết (bổ sung sau)', 200, 'Samsung', 8, 128, 'blue', 4.6, '2025-03-02 09:30:15', '2025-03-02 09:30:15'),
    ('Samsung Galaxy Z Fold6 5G 12GB/512GB', 39990000, '24.jpg', 'Mô tả chi tiết (bổ sung sau)', 200, 'Samsung', 12, 512, 'black', 5, '2025-03-02 10:10:25', '2025-03-02 10:10:25'),
    ('Samsung Galaxy A06 4GB/128GB', 3190000, '25.jpg', 'Mô tả chi tiết (bổ sung sau)', 80, 'Samsung', 4, 128, 'green', 5, '2025-03-02 11:15:35', '2025-03-02 11:15:35'),
-- 26-30
    ('Samsung Galaxy S24 FE 5G 8GB/128GB', 13990000, '26.jpg', 'Mô tả chi tiết (bổ sung sau)', 100, 'Samsung', 8, 128, 'white', 4.9, '2025-03-01 10:50:24', '2025-03-01 10:50:24'),
    ('Samsung Galaxy A55 5G 8GB/128GB', 8790000, '27.jpg', 'Mô tả chi tiết (bổ sung sau)', 150, 'Samsung', 8, 128, 'black', 4.8, '2025-03-01 11:00:00', '2025-03-01 11:00:00'),
    ('Samsung Galaxy A55 5G 8GB/256GB', 9890000, '28.jpg', 'Mô tả chi tiết (bổ sung sau)', 200, 'Samsung', 8, 256, 'blue', 5, '2025-03-02 09:30:15', '2025-03-02 09:30:15'),
    ('Samsung Galaxy A16 8GB/256GB', 5890000, '29.jpg', 'Mô tả chi tiết (bổ sung sau)', 200, 'Samsung', 8, 256, 'red', 4.6, '2025-03-02 10:10:25', '2025-03-02 10:10:25'),
    ('Samsung Galaxy Z Flip6 5G 12GB/512GB', 24990000, '30.jpg', 'Mô tả chi tiết (bổ sung sau)', 80, 'Samsung', 12, 512, 'green', 4.6, '2025-03-02 11:15:35', '2025-03-02 11:15:35'),
-- 31-35
    ('iPhone 16 Pro Max 256GB', 31390000, '31.jpg', 'Mô tả chi tiết (bổ sung sau)', 100, 'Apple', 8, 256, 'white', 4.9, '2025-03-01 10:50:24', '2025-03-01 10:50:24'),
    ('iPhone 16 Pro Max 512GB', 37790000, '32.jpg', 'Mô tả chi tiết (bổ sung sau)', 150, 'Apple', 8, 512, 'black', 5, '2025-03-01 11:00:00', '2025-03-01 11:00:00'),
    ('iPhone 16 Pro Max 1TB', 43790000, '33.jpg', 'Mô tả chi tiết (bổ sung sau)', 200, 'Apple', 8, 1024, 'blue', 4.9, '2025-03-02 09:30:15', '2025-03-02 09:30:15'),
    ('iPhone 16 Pro 128GB', 25590000, '34.jpg', 'Mô tả chi tiết (bổ sung sau)', 200, 'Apple', 8, 128, 'red', 4.8, '2025-03-02 10:10:25', '2025-03-02 10:10:25'),
    ('iPhone 16 Pro 256GB', 28790000, '35.jpg', 'Mô tả chi tiết (bổ sung sau)', 80, 'Apple', 8, 256, 'green', 4.9, '2025-03-02 11:15:35', '2025-03-02 11:15:35'),
-- 36-40
    ('iPhone 16 Pro 512GB', 34790000, '36.jpg', 'Mô tả chi tiết (bổ sung sau)', 100, 'Apple', 8, 512, 'white', 4.8, '2025-03-01 10:50:24', '2025-03-01 10:50:24'),
    ('iPhone 16 Pro 1TB', 40790000, '37.jpg', 'Mô tả chi tiết (bổ sung sau)', 150, 'Apple', 8, 1024, 'black', 4.6, '2025-03-01 11:00:00', '2025-03-01 11:00:00'),
    ('iPhone 16 Plus 128GB', 22990000, '38.jpg', 'Mô tả chi tiết (bổ sung sau)', 200, 'Apple', 8, 128, 'blue', 5, '2025-03-02 09:30:15', '2025-03-02 09:30:15'),
    ('iPhone 16 Plus 256GB', 25990000, '39.jpg', 'Mô tả chi tiết (bổ sung sau)', 200, 'Apple', 8, 256, 'red', 4.8, '2025-03-02 10:10:25', '2025-03-02 10:10:25'),
    ('iPhone 16 Plus 512GB', 31990000, '40.jpg', 'Mô tả chi tiết (bổ sung sau)', 80, 'Apple', 12, 512, 'green', 4.9, '2025-03-02 11:15:35', '2025-03-02 11:15:35'),
-- 41-45
    ('iPhone 16 128GB', 19890000, '41.jpg', 'Mô tả chi tiết (bổ sung sau)', 100, 'Apple', 8, 128, 'white', 4.7, '2025-03-01 10:00:00', '2025-03-01 10:00:00'),
    ('iPhone 16 256GB', 22990000, '42.jpg', 'Mô tả chi tiết (bổ sung sau)', 100, 'Apple', 8, 256, 'white', 4.7, '2025-03-01 10:00:00', '2025-03-01 10:00:00'),
    ('iPhone 16 512GB', 28990000, '43.jpg', 'Mô tả chi tiết (bổ sung sau)', 100, 'Apple', 8, 512, 'white', 4.7, '2025-03-01 10:00:00', '2025-03-01 10:00:00'),
    ('iPhone 15 Pro Max 256GB', 29990000, '44.jpg', 'Mô tả chi tiết (bổ sung sau)', 100, 'Apple', 8, 256, 'white', 4.7, '2025-03-01 10:00:00', '2025-03-01 10:00:00'),
    ('iPhone 15 Pro Max 512GB', 34590000, '45.jpg', 'Mô tả chi tiết (bổ sung sau)', 100, 'Apple', 8, 512, 'white', 4.7, '2025-03-01 10:00:00', '2025-03-01 10:00:00'),
-- 46-50
    ('iPhone 15 Pro Max 1TB', 39990000, '46.jpg', 'Mô tả chi tiết (bổ sung sau)', 100, 'Apple', 8, 1, 'white', 4.7, '2025-03-01 10:00:00', '2025-03-01 10:00:00'),
    ('iPhone 15 Plus 128GB', 19790000, '47.jpg', 'Mô tả chi tiết (bổ sung sau)', 100, 'Apple', 6, 128, 'white', 4.7, '2025-03-01 10:00:00', '2025-03-01 10:00:00'),
    ('iPhone 15 Plus 256GB', 22790000, '48.jpg', 'Mô tả chi tiết (bổ sung sau)', 100, 'Apple', 6, 256, 'white', 4.7, '2025-03-01 10:00:00', '2025-03-01 10:00:00'),
    ('iPhone 15 Plus 512GB', 28790000, '49.jpg', 'Mô tả chi tiết (bổ sung sau)', 100, 'Apple', 6, 512, 'white', 4.7, '2025-03-01 10:00:00', '2025-03-01 10:00:00'),
    ('iPhone 15 128GB', 15990000, '50.jpg', 'Mô tả chi tiết (bổ sung sau)', 100, 'Apple', 6, 128, 'white', 4.7, '2025-03-01 10:00:00', '2025-03-01 10:00:00'),
-- 51-55
    ('iPhone 15 256GB', 18990000, '51.jpg', 'Mô tả chi tiết (bổ sung sau)', 100, 'Apple', 6, 256, 'white', 4.7, '2025-03-01 10:00:00', '2025-03-01 10:00:00'),
    ('iPhone 14 Plus 256GB', 21090000, '52.jpg', 'Mô tả chi tiết (bổ sung sau)', 100, 'Apple', 6, 256, 'white', 4.7, '2025-03-01 10:00:00', '2025-03-01 10:00:00'),
    ('iPhone 14 Plus 512GB', 22990000, '53.jpg', 'Mô tả chi tiết (bổ sung sau)', 100, 'Apple', 6, 512, 'white', 4.7, '2025-03-01 10:00:00', '2025-03-01 10:00:00'),
    ('iPhone 14 128GB', 13090000, '54.jpg', 'Mô tả chi tiết (bổ sung sau)', 100, 'Apple', 6, 128, 'white', 4.7, '2025-03-01 10:00:00', '2025-03-01 10:00:00'),
    ('iPhone 13 128GB', 11990000, '55.jpg', 'Mô tả chi tiết (bổ sung sau)', 100, 'Apple', 4, 128, 'white', 4.7, '2025-03-01 10:00:00', '2025-03-01 10:00:00'),
-- 56-60
    ('iPhone 13 512GB', 21990000, '56.jpg', 'Mô tả chi tiết (bổ sung sau)', 100, 'Apple', 4, 512, 'white', 4.7, '2025-03-01 10:00:00', '2025-03-01 10:00:00'),
    ('iPhone 12 64GB', 10590000, '57.jpg', 'Mô tả chi tiết (bổ sung sau)', 100, 'Apple', 4, 64, 'white', 4.7, '2025-03-01 10:00:00', '2025-03-01 10:00:00'),
    ('iPhone 12 128GB', 11590000, '58.jpg', 'Mô tả chi tiết (bổ sung sau)', 100, 'Apple', 4, 128, 'white', 4.7, '2025-03-01 10:00:00', '2025-03-01 10:00:00'),
    ('iPhone 16e 128GB', 16990000, '59.jpg', 'Mô tả chi tiết (bổ sung sau)', 100, 'Apple', 8, 128, 'white', 4.7, '2025-03-01 10:00:00', '2025-03-01 10:00:00'),
    ('iPhone 16e 256GB', 19990000, '60.jpg', 'Mô tả chi tiết (bổ sung sau)', 100, 'Apple', 8, 256, 'white', 4.7, '2025-03-01 10:00:00', '2025-03-01 10:00:00'),
-- 61-65
    ('iPhone 16e 512GB', 25990000, '61.jpg', 'Mô tả chi tiết (bổ sung sau)', 100, 'Apple', 8, 512, 'white', 4.7, '2025-03-01 10:00:00', '2025-03-01 10:00:00'),
    ('OPPO Reno13 5G 12GB/256GB', 15490000, '62.jpg', 'Mô tả chi tiết (bổ sung sau)', 100, 'OPPO', 12, 256, 'white', 4.7, '2025-03-01 10:00:00', '2025-03-01 10:00:00'),
    ('OPPO Reno13 5G 12GB/512GB', 16990000, '63.jpg', 'Mô tả chi tiết (bổ sung sau)', 100, 'OPPO', 12, 512, 'white', 4.7, '2025-03-01 10:00:00', '2025-03-01 10:00:00'),
    ('OPPO A60 8GB/128GB', 5490000, '64.jpg', 'Mô tả chi tiết (bổ sung sau)', 100, 'OPPO', 8, 128, 'white', 4.7, '2025-03-01 10:00:00', '2025-03-01 10:00:00'),
    ('OPPO A60 8GB/256GB', 6090000, '65.jpg', 'Mô tả chi tiết (bổ sung sau)', 100, 'OPPO', 8, 256, 'white', 4.7, '2025-03-01 10:00:00', '2025-03-01 10:00:00'),
-- 66-70
    ('OPPO A3 8GB/128GB', 5290000, '66.jpg', 'Mô tả chi tiết (bổ sung sau)', 100, 'OPPO', 8, 128, 'white', 4.7, '2025-03-01 10:00:00', '2025-03-01 10:00:00'),
    ('OPPO A3 8GB/256GB', 6090000, '67.jpg', 'Mô tả chi tiết (bổ sung sau)', 100, 'OPPO', 8, 256, 'white', 4.7, '2025-03-01 10:00:00', '2025-03-01 10:00:00'),
    ('OPPO Reno12 5G 12GB/512GB', 12990000, '68.jpg', 'Mô tả chi tiết (bổ sung sau)', 100, 'OPPO', 12, 512, 'white', 4.7, '2025-03-01 10:00:00', '2025-03-01 10:00:00'),
    ('OPPO Reno12 5G 12GB/256GB', 11990000, '69.jpg', 'Mô tả chi tiết (bổ sung sau)', 100, 'OPPO', 12, 256, 'white', 4.7, '2025-03-01 10:00:00', '2025-03-01 10:00:00'),
    ('Xiaomi Redmi Note 14 8GB/128GB', 5490000, '70.jpg', 'Mô tả chi tiết (bổ sung sau)', 100, 'Xiaomi', 8, 128, 'white', 4.7, '2025-03-01 10:00:00', '2025-03-01 10:00:00'),
-- 71-75
    ('Xiaomi Redmi Note 14 8GB/256GB', 6090000, '71.jpg', 'Mô tả chi tiết (bổ sung sau)', 100, 'Xiaomi', 8, 256, 'white', 4.7, '2025-03-01 10:00:00', '2025-03-01 10:00:00'),
    ('Xiaomi Redmi Note 14 Pro 8GB/256GB', 7590000, '72.jpg', 'Mô tả chi tiết (bổ sung sau)', 100, 'Xiaomi', 8, 256, 'white', 4.7, '2025-03-01 10:00:00', '2025-03-01 10:00:00'),
    ('Xiaomi Redmi Note 14 Pro 5G 8GB/256GB', 8790000, '73.jpg', 'Mô tả chi tiết (bổ sung sau)', 100, 'Xiaomi', 8, 256, 'white', 4.7, '2025-03-01 10:00:00', '2025-03-01 10:00:00'),
    ('Xiaomi Redmi Note 14 Pro+ 5G 8GB/256GB', 10690000, '74.jpg', 'Mô tả chi tiết (bổ sung sau)', 100, 'Xiaomi', 8, 256, 'white', 4.7, '2025-03-01 10:00:00', '2025-03-01 10:00:00'),
    ('OPPO A58 8GB/128GB', 4990000, '75.jpg', 'Mô tả chi tiết (bổ sung sau)', 100, 'OPPO', 8, 128, 'white', 4.7, '2025-03-01 10:00:00', '2025-03-01 10:00:00'),
-- 76-80
    ('OPPO Find N3 5G 16GB/512GB', 38990000, '76.jpg', 'Mô tả chi tiết (bổ sung sau)', 100, 'OPPO', 16, 512, 'white', 4.7, '2025-03-01 10:00:00', '2025-03-01 10:00:00'),
    ('realme C75 8GB/128GB', 5690000, '77.jpg', 'Mô tả chi tiết (bổ sung sau)', 100, 'realme', 8, 128, 'white', 4.7, '2025-03-01 10:00:00', '2025-03-01 10:00:00'),
    ('realme C75 8GB/256GB', 6490000, '78.jpg', 'Mô tả chi tiết (bổ sung sau)', 100, 'realme', 8, 256, 'white', 4.7, '2025-03-01 10:00:00', '2025-03-01 10:00:00'),
    ('realme C75 8GB/512GB', 6790000, '79.jpg', 'Mô tả chi tiết (bổ sung sau)', 100, 'realme', 8, 512, 'white', 4.7, '2025-03-01 10:00:00', '2025-03-01 10:00:00'),
    ('realme Note 60x 4GB/64GB', 2990000, '80.jpg', 'Mô tả chi tiết (bổ sung sau)', 100, 'realme', 4, 64, 'white', 4.7, '2025-03-01 10:00:00', '2025-03-01 10:00:00'),
-- 81-85
    ('realme Note 60x 3GB/64GB', 2490000, '81.jpg', 'Mô tả chi tiết (bổ sung sau)', 100, 'realme', 3, 64, 'white', 4.7, '2025-03-01 10:00:00', '2025-03-01 10:00:00'),
    ('realme 13+ 5G 12GB/256GB', 9990000, '82.jpg', 'Mô tả chi tiết (bổ sung sau)', 100, 'realme', 12, 256, 'white', 4.7, '2025-03-01 10:00:00', '2025-03-01 10:00:00'),
    ('realme C65s 6GB/128GB', 3290000, '83.jpg', 'Mô tả chi tiết (bổ sung sau)', 100, 'realme', 6, 128, 'white', 4.7, '2025-03-01 10:00:00', '2025-03-01 10:00:00'),
    ('realme C65s 8GB/128GB', 4090000, '84.jpg', 'Mô tả chi tiết (bổ sung sau)', 100, 'realme', 6, 128, 'white', 4.7, '2025-03-01 10:00:00', '2025-03-01 10:00:00'),
    ('realme C65s 8GB/256GB', 4790000, '85.jpg', 'Mô tả chi tiết (bổ sung sau)', 100, 'realme', 6, 256, 'white', 4.7, '2025-03-01 10:00:00', '2025-03-01 10:00:00'),
-- 86-90
    ('realme 12 8GB/256GB', 7390000, '86.jpg', 'Mô tả chi tiết (bổ sung sau)', 100, 'realme', 8, 256, 'white', 4.7, '2025-03-01 10:00:00', '2025-03-01 10:00:00'),
    ('realme 12 8GB/512GB', 7690000, '87.jpg', 'Mô tả chi tiết (bổ sung sau)', 100, 'realme', 8, 512, 'white', 4.7, '2025-03-01 10:00:00', '2025-03-01 10:00:00'),
    ('realme Note 60x 4GB/64GB', 2990000, '88.jpg', 'Mô tả chi tiết (bổ sung sau)', 100, 'realme', 4, 64, 'white', 4.7, '2025-03-01 10:00:00', '2025-03-01 10:00:00'),
    ('realme Note 60x 3GB/64GB', 2490000, '89.jpg', 'Mô tả chi tiết (bổ sung sau)', 100, 'realme', 3, 64, 'white', 4.7, '2025-03-01 10:00:00', '2025-03-01 10:00:00'),
    ('realme 13+ 5G 12GB/256GB', 9990000, '90.jpg', 'Mô tả chi tiết (bổ sung sau)', 100, 'realme', 12, 256, 'white', 4.7, '2025-03-01 10:00:00', '2025-03-01 10:00:00'),
-- 91-95
    ('realme C65s 6GB/128GB', 3290000, '91.jpg', 'Mô tả chi tiết (bổ sung sau)', 100, 'realme', 6, 128, 'white', 4.7, '2025-03-01 10:00:00', '2025-03-01 10:00:00'),
    ('realme C65s 8GB/128GB', 4090000, '92.jpg', 'Mô tả chi tiết (bổ sung sau)', 100, 'realme', 8, 128, 'white', 4.7, '2025-03-01 10:00:00', '2025-03-01 10:00:00'),
    ('realme C65s 8GB/256GB', 4790000, '93.jpg', 'Mô tả chi tiết (bổ sung sau)', 100, 'realme', 8, 256, 'white', 4.7, '2025-03-01 10:00:00', '2025-03-01 10:00:00'),
    ('realme 12 8GB/256GB', 7390000, '94.jpg', 'Mô tả chi tiết (bổ sung sau)', 100, 'realme', 8, 256, 'white', 4.7, '2025-03-01 10:00:00', '2025-03-01 10:00:00'),
    ('realme 12 8GB/512GB', 7690000, '95.jpg', 'Mô tả chi tiết (bổ sung sau)', 100, 'realme', 8, 512, 'white', 4.7, '2025-03-01 10:00:00', '2025-03-01 10:00:00'),
-- 96-100
    ('realme Note 60 6GB/128GB', 3790000, '96.jpg', 'Mô tả chi tiết (bổ sung sau)', 100, 'realme', 6, 128, 'white', 4.7, '2025-03-01 10:00:00', '2025-03-01 10:00:00'),
    ('realme Note 60 4GB/64GB', 2790000, '97.jpg', 'Mô tả chi tiết (bổ sung sau)', 100, 'realme', 4, 64, 'white', 4.7, '2025-03-01 10:00:00', '2025-03-01 10:00:00'),
    ('realme Note 60 6GB/128GB', 3790000, '98.jpg', 'Mô tả chi tiết (bổ sung sau)', 100, 'realme', 6, 128, 'white', 4.7, '2025-03-01 10:00:00', '2025-03-01 10:00:00'),
    ('realme Note 50 3GB/64GB', 2490000, '99.jpg', 'Mô tả chi tiết (bổ sung sau)', 100, 'realme', 3, 64, 'white', 4.7, '2025-03-01 10:00:00', '2025-03-01 10:00:00'),
    ('realme Note 50 4GB/128GB', 2890000, '100.jpg', 'Mô tả chi tiết (bổ sung sau)', 100, 'realme', 4, 128, 'white', 4.7, '2025-03-01 10:00:00', '2025-03-01 10:00:00');

INSERT INTO carts (user_id) VALUES
    (2);

INSERT INTO cart_items (quantity, product_id, updated_at, cart_id, selected) VALUES
    (1, 1, '2025-03-03 09:30:15', 1, true),
    (2, 2, '2025-03-03 09:32:00', 1, true);

-- INSERT INTO orders (total_price, created_at, user_id, payment_status) VALUES
--     (38000000, '2025-03-03 09:34:10', 2, 'PENDING');
--
-- INSERT INTO order_items (order_id, product_id, quantity) VALUES
--     (1, 1, 1),
--     (1, 2, 2);
