create database ecommerce;
USE ecommerce;
CREATE TABLE users (
  id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  username varchar(255) NOT NULL,
  email varchar(255) NOT NULL,
  password varchar(255) NOT NULL,
  phonenumber varchar(255) NOT NULL
);
CREATE TABLE products (
  id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  name varchar(255) NOT NULL,
  description varchar(255) NOT NULL,
  price float NOT NULL
);
CREATE TABLE carts (
  id int4 NOT NULL AUTO_INCREMENT PRIMARY KEY,
  user_id int NOT NULL,
  FOREIGN KEY (user_id) REFERENCES users(id)
);
CREATE TABLE cart_products (
  id int4 NOT NULL AUTO_INCREMENT PRIMARY KEY,
  cart_id int4 NOT NULL,
  product_id int NOT NULL,
  amount INT DEFAULT 1,
  UNIQUE (cart_id, product_id),
  FOREIGN KEY (cart_id) REFERENCES ecommerce.carts(id),
  FOREIGN KEY (product_id) REFERENCES products(id)
);
INSERT INTO users (id, username, email, password, phonenumber)
VALUES (1, 'john_doe', 'johndoe@example.com', 'password123', '1234567890'),
       (2, 'jane_doe', 'janedoe@example.com', 'password456', '2345678901'),
       (3, 'bob_smith', 'bobsmith@example.com', 'password789', '3456789012');
INSERT INTO products (name, description, price)
VALUES 
  ("Smartphone", "The latest smartphone with advanced features", 500),
  ("Tablet", "A tablet with a large screen and high-speed processor", 400),
  ("Laptop", "A powerful laptop for work and play", 1000),
  ("Smartwatch", "A smartwatch with advanced fitness tracking features", 300),
  ("Bluetooth Headphones", "High-quality wireless headphones for music lovers", 200),
  ("Fitness Tracker", "A wearable device to track your fitness and health", 150),
  ("Gaming Console", "A powerful gaming console for immersive gaming experiences", 500),
  ("Smart Home Hub", "A smart home device to control all your smart home devices", 200),
  ("Drone", "A high-tech drone for aerial photography and videography", 500),
  ("Virtual Reality Headset", "A VR headset for a truly immersive experience", 400);
INSERT INTO carts (user_id)
VALUES
  (1),
  (2),
  (3);

INSERT INTO cart_products (cart_id, product_id)
VALUES
  (1, 1),
  (1, 2),
  (1, 3),
  (2, 4),
  (2, 5, 2),
  (2, 1),
  (2, 6),
  (3, 7),
  (3, 8),
  (3, 9),
  (3, 10);
SELECT * FROM users;
SELECT * FROM products;
SELECT * FROM carts;
SELECT * FROM cart_products;
