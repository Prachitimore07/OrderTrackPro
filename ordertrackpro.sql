-- =====================================
-- ORDER TRACK PRO DATABASE
-- =====================================

DROP DATABASE IF EXISTS ordertrackpro;

CREATE DATABASE ordertrackpro;

USE ordertrackpro;

-- =====================================
-- PRODUCTS TABLE
-- =====================================

CREATE TABLE products (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    product_name VARCHAR(100) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    quantity INT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =====================================
-- CUSTOMERS TABLE
-- =====================================

CREATE TABLE customers (
    customer_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE,
    phone VARCHAR(15),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =====================================
-- ORDERS TABLE
-- =====================================

CREATE TABLE orders (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT NOT NULL,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (customer_id)
    REFERENCES customers(customer_id)
);

-- =====================================
-- ORDER DETAILS TABLE
-- =====================================

CREATE TABLE order_details (
    detail_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    total_price DECIMAL(10,2) NOT NULL,

    FOREIGN KEY (order_id)
    REFERENCES orders(order_id),

    FOREIGN KEY (product_id)
    REFERENCES products(product_id)
);

-- =====================================
-- PRODUCTS DATA (20 PRODUCTS)
-- =====================================

INSERT INTO products (product_name, price, quantity)
VALUES
('Laptop',55000,15),
('Mouse',500,100),
('Keyboard',1500,50),
('Monitor',12000,20),
('Printer',9000,10),
('Webcam',2500,25),
('Speaker',3000,30),
('Hard Disk 1TB',4500,40),
('SSD 512GB',5500,35),
('Router',2200,25),
('Power Bank',1800,40),
('USB Drive 64GB',700,80),
('Graphics Card',35000,8),
('RAM 16GB',4200,20),
('CPU Cooler',2500,15),
('Microphone',2800,18),
('Gaming Chair',12000,12),
('Smart Watch',5000,22),
('Tablet',18000,10),
('Projector',25000,5);

-- =====================================
-- CUSTOMERS DATA (20 CUSTOMERS)
-- =====================================

INSERT INTO customers (customer_name,email,phone)
VALUES
('Rahul Sharma','rahul@gmail.com','9876543210'),
('Priya Singh','priya@gmail.com','9876543211'),
('Amit Kumar','amit@gmail.com','9876543212'),
('Neha Gupta','neha@gmail.com','9876543213'),
('Vikas Patel','vikas@gmail.com','9876543214'),
('Anjali Verma','anjali@gmail.com','9876543215'),
('Rohit Mehta','rohit@gmail.com','9876543216'),
('Sneha Joshi','sneha@gmail.com','9876543217'),
('Karan Malhotra','karan@gmail.com','9876543218'),
('Pooja Sharma','pooja@gmail.com','9876543219'),
('Arjun Reddy','arjun@gmail.com','9876543220'),
('Meera Nair','meera@gmail.com','9876543221'),
('Siddharth Jain','siddharth@gmail.com','9876543222'),
('Kavya Iyer','kavya@gmail.com','9876543223'),
('Nikhil Agarwal','nikhil@gmail.com','9876543224'),
('Ritika Kapoor','ritika@gmail.com','9876543225'),
('Manish Yadav','manish@gmail.com','9876543226'),
('Shreya Das','shreya@gmail.com','9876543227'),
('Aditya Mishra','aditya@gmail.com','9876543228'),
('Ayesha Khan','ayesha@gmail.com','9876543229');

-- =====================================
-- SAMPLE ORDERS
-- =====================================

INSERT INTO orders (customer_id)
VALUES
(1),(2),(3),(4),(5),
(6),(7),(8),(9),(10);

-- =====================================
-- SAMPLE ORDER DETAILS
-- =====================================

INSERT INTO order_details
(order_id,product_id,quantity,total_price)
VALUES
(1,1,1,55000),
(1,2,2,1000),

(2,3,1,1500),
(2,4,1,12000),

(3,5,1,9000),
(3,6,2,5000),

(4,7,1,3000),
(4,8,1,4500),

(5,9,2,11000),
(5,10,1,2200),

(6,11,2,3600),
(6,12,3,2100),

(7,13,1,35000),
(7,14,2,8400),

(8,15,1,2500),
(8,16,1,2800),

(9,17,1,12000),
(9,18,2,10000),

(10,19,1,18000),
(10,20,1,25000);

-- =====================================
-- REPORT QUERIES
-- =====================================

-- Total Revenue

SELECT SUM(total_price) AS Total_Revenue
FROM order_details;

-- Total Customers

SELECT COUNT(*) AS Total_Customers
FROM customers;

-- Total Products

SELECT COUNT(*) AS Total_Products
FROM products;

-- Total Orders

SELECT COUNT(*) AS Total_Orders
FROM orders;

-- Inventory Report

SELECT *
FROM products;

-- Low Stock Alert

SELECT *
FROM products
WHERE quantity < 10;

-- Customer Order Report

SELECT
o.order_id,
c.customer_name,
o.order_date
FROM orders o
JOIN customers c
ON o.customer_id = c.customer_id;

-- Product Sales Report

SELECT
p.product_name,
SUM(od.quantity) AS Total_Sold
FROM products p
JOIN order_details od
ON p.product_id = od.product_id
GROUP BY p.product_name
ORDER BY Total_Sold DESC;

USE ordertrackpro;

SELECT COUNT(*) FROM customers;
SELECT COUNT(*) FROM products;
SELECT COUNT(*) FROM customers;
SELECT COUNT(*) FROM orders;
SELECT COUNT(*) FROM order_details;