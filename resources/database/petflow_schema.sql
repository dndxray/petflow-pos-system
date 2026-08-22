CREATE DATABASE IF NOT EXISTS petflow_db;
USE petflow_db;

DROP TABLE IF EXISTS transaction_items;
DROP TABLE IF EXISTS transactions;
DROP TABLE IF EXISTS accessory_products;
DROP TABLE IF EXISTS toy_products;
DROP TABLE IF EXISTS food_products;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id VARCHAR(10) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL
);

CREATE TABLE food_products (
    id VARCHAR(10) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price DOUBLE NOT NULL,
    stock INT NOT NULL
);

CREATE TABLE toy_products (
    id VARCHAR(10) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price DOUBLE NOT NULL,
    stock INT NOT NULL
);

CREATE TABLE accessory_products (
    id VARCHAR(10) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    price DOUBLE NOT NULL,
    stock INT NOT NULL
);

CREATE TABLE transactions (
    id VARCHAR(20) PRIMARY KEY,
    cashier_id VARCHAR(10),
    cashier_name VARCHAR(100),
    total_amount DOUBLE,
    amount_paid DOUBLE,
    change_amount DOUBLE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE transaction_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    transaction_id VARCHAR(20),
    product_id VARCHAR(10),
    product_name VARCHAR(100),
    quantity INT,
    price DOUBLE,
    discount DOUBLE
);

-- Users
INSERT INTO users (id, name, password, role) VALUES 
('U001', 'admin', 'admin123', 'ADMIN'),
('U002', 'kasir1', 'kasir123', 'CASHIER'),
('U003', 'kasir2', 'kasir123', 'CASHIER'),
('U004', 'kasir3', 'kasir123', 'CASHIER');

-- Food Products
INSERT INTO food_products (id, name, price, stock) VALUES
('F001', 'Whiskas Tuna Cat', 35000, 20),
('F002', 'Whiskas Salmon Cat', 37000, 15),
('F003', 'Royal Canin Kitten', 85000, 10),
('F004', 'Me-O Adult Tuna', 28000, 25),
('F005', 'Cat Choize Seafood', 30000, 18),
('F006', 'Bolt Cat Food', 26000, 30),
('F007', 'Happy Cat Minkas', 65000, 8);

-- Toy Products
INSERT INTO toy_products (id, name, price, stock) VALUES
('T001', 'Bola Mainan Kucing', 15000, 40),
('T002', 'Laser Pointer Cat', 45000, 20),
('T003', 'Mouse Toy Cat', 18000, 35),
('T004', 'Cat Teaser Feather', 25000, 28),
('T005', 'Tunnel Mainan Kucing', 85000, 10),
('T006', 'Interactive Ball Cat', 65000, 14),
('T007', 'Boneka Ikan Elektrik', 75000, 12);

-- Accessory Products
INSERT INTO accessory_products (id, name, price, stock) VALUES
('A001', 'Kalung Kucing Biru', 20000, 25),
('A002', 'Tempat Makan Kucing', 55000, 15),
('A003', 'Litter Box Jumbo', 120000, 8),
('A004', 'Tas Kucing Transparan', 250000, 5),
('A005', 'Sisir Bulu Kucing', 30000, 18),
('A006', 'Tempat Tidur Kucing', 175000, 7),
('A007', 'Harness Kucing', 45000, 20);

-- Transactions
INSERT INTO transactions (id, cashier_id, cashier_name, total_amount, amount_paid, change_amount) VALUES
('TRX001', 'U003', 'Ani Rahayu', 85000, 100000, 15000),
('TRX002', 'U003', 'Ani Rahayu', 120000, 150000, 30000),
('TRX003', 'U004', 'Farah Nabila', 65000, 70000, 5000),
('TRX004', 'U003', 'Ani Rahayu', 175000, 200000, 25000),
('TRX005', 'U004', 'Farah Nabila', 90000, 100000, 10000);

-- Transaction Items
INSERT INTO transaction_items (transaction_id, product_id, product_name, quantity, price, discount) VALUES
('TRX001', 'F001', 'Whiskas Tuna Cat', 2, 35000, 5),
('TRX001', 'T001', 'Bola Mainan Kucing', 1, 15000, 10),
('TRX002', 'A002', 'Tempat Makan Kucing', 1, 55000, 0),
('TRX002', 'T005', 'Tunnel Mainan Kucing', 1, 85000, 10),
('TRX003', 'F004', 'Me-O Adult Tuna', 1, 28000, 5),
('TRX003', 'T003', 'Mouse Toy Cat', 2, 18000, 10),
('TRX004', 'A006', 'Tempat Tidur Kucing', 1, 175000, 0),
('TRX005', 'F002', 'Whiskas Salmon Cat', 1, 37000, 5),
('TRX005', 'T004', 'Cat Teaser Feather', 2, 25000, 10);