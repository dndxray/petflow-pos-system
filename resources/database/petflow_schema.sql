CREATE DATABASE IF NOT EXISTS petflow_db;
USE petflow_db;

DROP TABLE IF EXISTS transaction_details;
DROP TABLE IF EXISTS transactions;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(100) NOT NULL,
    role ENUM('ADMIN', 'CASHIER') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(20) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    category ENUM('FOOD', 'TOY', 'ACCESSORY') NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE transactions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    transaction_code VARCHAR(20) UNIQUE NOT NULL,
    cashier_id INT NOT NULL,
    total DECIMAL(10,2) NOT NULL,
    paid DECIMAL(10,2) NOT NULL,
    change_amount DECIMAL(10,2) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (cashier_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE transaction_details (
    id INT AUTO_INCREMENT PRIMARY KEY,
    transaction_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    discount DECIMAL(10,2) DEFAULT 0,
    FOREIGN KEY (transaction_id) REFERENCES transactions(id) ON DELETE CASCADE,
    FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);

INSERT INTO users (username, password, name, role) VALUES 
('admin', 'admin123', 'Admin Utama', 'ADMIN'),
('kasir1', 'kasir123', 'Ani Rahayu', 'CASHIER'),
('kasir2', 'kasir123', 'Budi Prasetyo', 'CASHIER'),
('kasir3', 'kasir123', 'Citra Dewi', 'CASHIER');

INSERT INTO products (code, name, category, price, stock) VALUES
-- Food
('F001', 'Whiskas Tuna Cat', 'FOOD', 35000, 20),
('F002', 'Whiskas Salmon Cat', 'FOOD', 37000, 15),
('F003', 'Royal Canin Kitten', 'FOOD', 85000, 10),
('F004', 'Me-O Adult Tuna', 'FOOD', 28000, 25),
('F005', 'Cat Choize Seafood', 'FOOD', 30000, 18),
('F006', 'Bolt Cat Food', 'FOOD', 26000, 30),
('F007', 'Happy Cat Minkas', 'FOOD', 65000, 8),
-- Toys
('T001', 'Bola Mainan Kucing', 'TOY', 15000, 40),
('T002', 'Laser Pointer Cat', 'TOY', 45000, 20),
('T003', 'Mouse Toy Cat', 'TOY', 18000, 35),
('T004', 'Cat Teaser Feather', 'TOY', 25000, 28),
('T005', 'Tunnel Mainan Kucing', 'TOY', 85000, 10),
('T006', 'Interactive Ball Cat', 'TOY', 65000, 14),
('T007', 'Boneka Ikan Elektrik', 'TOY', 75000, 12),
-- Accessories
('A001', 'Kalung Kucing Biru', 'ACCESSORY', 20000, 25),
('A002', 'Tempat Makan Kucing', 'ACCESSORY', 55000, 15),
('A003', 'Litter Box Jumbo', 'ACCESSORY', 120000, 8),
('A004', 'Tas Kucing Transparan', 'ACCESSORY', 250000, 5),
('A005', 'Sisir Bulu Kucing', 'ACCESSORY', 30000, 18),
('A006', 'Tempat Tidur Kucing', 'ACCESSORY', 175000, 7),
('A007', 'Harness Kucing', 'ACCESSORY', 45000, 20);

INSERT INTO transactions (transaction_code, cashier_id, total, paid, change_amount) VALUES
('TRX001', 2, 85000, 100000, 15000),
('TRX002', 2, 120000, 150000, 30000),
('TRX003', 3, 65000, 70000, 5000),
('TRX004', 4, 175000, 200000, 25000),
('TRX005', 2, 90000, 100000, 10000);

INSERT INTO transaction_details (transaction_id, product_id, quantity, price, discount) VALUES
(1, 1, 2, 35000, 5),
(1, 8, 1, 15000, 10),
(2, 16, 1, 55000, 0),
(2, 12, 1, 85000, 10),
(3, 4, 1, 28000, 5),
(3, 10, 2, 18000, 10),
(4, 20, 1, 175000, 0),
(5, 2, 1, 37000, 5),
(5, 11, 2, 25000, 10);