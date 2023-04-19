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
  (2, 5),
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
-- real database 
CREATE TABLE `user` (
  `id` INT  AUTO_INCREMENT PRIMARY KEY,
  `username` VARCHAR(50) NOT NULL,
  `email` VARCHAR(255) NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `phone_number` VARCHAR(20) NOT NULL,
  `active` BIT NOT NULL DEFAULT 0,
  `user_role` ENUM('admin', 'user') NOT NULL DEFAULT 'user',
  `dob` DATE NOT NULL,
  `gender` ENUM('male', 'female', 'other') NOT NULL,
  `profile` VARCHAR(255) DEFAULT NULL,
  `google_id` VARCHAR(255) DEFAULT NULL,
  `facebook_id` VARCHAR(255) DEFAULT NULL,
  `create_date` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `last_login_date` TIMESTAMP DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
CREATE TABLE category (
  id INT NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  description VARCHAR(255) DEFAULT NULL,
  create_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  last_update_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
);
CREATE TABLE product (
  id INT AUTO_INCREMENT PRIMARY KEY,
  category_id INT NOT NULL,
  name VARCHAR(255) NOT NULL,
  description TEXT,
  price DECIMAL(10, 2) NOT NULL,
  product_stock INT NOT NULL,
  link_image VARCHAR(255),
  view INT DEFAULT 0,
  sale DECIMAL(5, 2) DEFAULT 0,
  create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (category_id) REFERENCES category(id)
);
CREATE TABLE product_variant (
  id INT NOT NULL AUTO_INCREMENT,
  product_id INT NOT NULL,
  name VARCHAR(255),
  variant_type VARCHAR(50),
  sort_order INT,
  PRIMARY KEY (id),
  FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE
);
CREATE TABLE variant_option (
  id INT PRIMARY KEY AUTO_INCREMENT,
  product_variant_id INT NOT NULL,
  name VARCHAR(255) NOT NULL,
  sort_order INT NOT NULL,
  FOREIGN KEY (product_variant_id) REFERENCES product_variant(id) ON DELETE CASCADE
);
CREATE TABLE product_review (
  id INT PRIMARY KEY AUTO_INCREMENT,
  user_id INT NOT NULL,
  product_id INT NOT NULL,
  rating INT NOT NULL,
  create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,
  FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE
);

CREATE TABLE product_comment (
  id INT PRIMARY KEY AUTO_INCREMENT,
  user_id INT NOT NULL,
  product_id INT NOT NULL,
  comment TEXT NOT NULL,
  create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  parent_comment_id INT,
  FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,
  FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE,
  FOREIGN KEY (parent_comment_id) REFERENCES product_comment(id) ON DELETE CASCADE
);
CREATE TABLE cart (
  id INT PRIMARY KEY AUTO_INCREMENT,
  user_id INT NOT NULL,
  product_id INT NOT NULL,
  amount INT NOT NULL,
  FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,
  FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE
);

CREATE TABLE `order` (
  id INT PRIMARY KEY AUTO_INCREMENT,
  user_id INT NOT NULL,
  order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  shipping_address TEXT NOT NULL,
  billing_address TEXT NOT NULL,
  total_price DECIMAL(10, 2) NOT NULL,
  tracking_number VARCHAR(50),
  payment_method VARCHAR(50) NOT NULL,
  order_status VARCHAR(50) NOT NULL,
  notes TEXT,
  FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE
);

CREATE TABLE order_detail (
  id INT PRIMARY KEY AUTO_INCREMENT,
  order_id INT NOT NULL,
  product_id INT NOT NULL,
  quantity INT NOT NULL,
  price DECIMAL(10, 2) NOT NULL,
  discount DECIMAL(5, 2) NOT NULL DEFAULT 0,
  tax DECIMAL(5, 2) NOT NULL DEFAULT 0,
  update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  create_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (order_id) REFERENCES `order`(id) ON DELETE CASCADE,
  FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE
);
INSERT INTO user (username, email, password, phone_number, active, user_role, dob, gender, profile, google_id, facebook_id, create_date, last_login_date) 
VALUES ('johndoe', 'johndoe@example.com', 'mypassword', '1234567890', 1, 'user', '1990-01-01', 'male', NULL, NULL, NULL, CURRENT_TIMESTAMP, NULL);

INSERT INTO user (username, email, password, phone_number, active, user_role, dob, gender, profile, google_id, facebook_id, create_date, last_login_date) 
VALUES ('janedoe', 'janedoe@example.com', 'mypassword', '0987654321', 1, 'user', '1995-05-05', 'female', NULL, NULL, NULL, CURRENT_TIMESTAMP, NULL);

INSERT INTO user (username, email, password, phone_number, active, user_role, dob, gender, profile, google_id, facebook_id, create_date, last_login_date) 
VALUES ('samsmith', 'samsmith@example.com', 'mypassword', '9876543210', 1, 'user', '1985-03-15', 'male', NULL, NULL, NULL, CURRENT_TIMESTAMP, NULL);

INSERT INTO user (username, email, password, phone_number, active, user_role, dob, gender, profile, google_id, facebook_id, create_date, last_login_date) 
VALUES ('sarahconnor', 'sarahconnor@example.com', 'mypassword', '0123456789', 1, 'user', '1980-12-22', 'female', NULL, NULL, NULL, CURRENT_TIMESTAMP, NULL);

INSERT INTO user (username, email, password, phone_number, active, user_role, dob, gender, profile, google_id, facebook_id, create_date, last_login_date) 
VALUES ('peterparker', 'peterparker@example.com', 'mypassword', '5554443333', 1, 'user', '1998-07-12', 'male', NULL, NULL, NULL, CURRENT_TIMESTAMP, NULL);

INSERT INTO user (username, email, password, phone_number, active, user_role, dob, gender, profile, google_id, facebook_id, create_date, last_login_date) 
VALUES ('maryjane', 'maryjane@example.com', 'mypassword', '3332221111', 1, 'user', '1995-10-20', 'female', NULL, NULL, NULL, CURRENT_TIMESTAMP, NULL);

INSERT INTO user (username, email, password, phone_number, active, user_role, dob, gender, profile, google_id, facebook_id, create_date, last_login_date) 
VALUES ('brucewayne', 'brucewayne@example.com', 'mypassword', '7776665555', 1, 'user', '1970-06-30', 'male', NULL, NULL, NULL, CURRENT_TIMESTAMP, NULL);

INSERT INTO user (username, email, password, phone_number, active, user_role, dob, gender, profile, google_id, facebook_id, create_date, last_login_date) 
VALUES ('clarkkent', 'clarkkent@example.com', 'mypassword', '9998887777', 1, 'user', '1988-04-18', 'male', NULL, NULL, NULL, CURRENT_TIMESTAMP, NULL);
INSERT INTO category (name, description) VALUES ('Gaming', 'Category for gaming gadgets');
INSERT INTO category (name, description) VALUES ('Smartphones', 'Category for smartphones and accessories');
INSERT INTO category (name, description) VALUES ('Laptops', 'Category for laptops and accessories');
INSERT INTO category (name, description) VALUES ('Cameras', 'Category for cameras and photography gadgets');
INSERT INTO category (name, description) VALUES ('Wearables', 'Category for wearable gadgets');
INSERT INTO category (name, description) VALUES ('Drones', 'Category for drones and accessories');
INSERT INTO category (name, description) VALUES ('Smart Home', 'Category for smart home gadgets');
INSERT INTO category (name, description) VALUES ('Audio', 'Category for audio gadgets and accessories');
INSERT INTO category (name, description) VALUES ('Tablets', 'Category for tablets and accessories');
INSERT INTO category (name, description) VALUES ('TVs', 'Category for TVs and home entertainment gadgets');
select * from category;
INSERT INTO product (category_id, name, description, price, product_stock, link_image, view, sale, create_date, update_date) VALUES
(1, 'Gaming Laptop', 'Powerful gaming laptop with high-end specs', 2000.00, 50, null, 150, 10.0, NOW(), NOW()),
(1, 'Gaming Mouse', 'High-performance gaming mouse with customizable RGB lighting', 80.00, 200, null, 75, 5.5, NOW(), NOW()),
(1, 'Gaming Chair', 'Ergonomic gaming chair with adjustable armrests and lumbar support', 300.00, 100, null, 120, 20.0, NOW(), NOW());
INSERT INTO product (category_id, name, description, price, product_stock, link_image, view, sale, create_date, update_date) VALUES
(2, 'Samsung Galaxy S21 Ultra 5G', 'The Samsung Galaxy S21 Ultra 5G is the company’s most premium phone yet. It has a 6.8-inch OLED screen with a 120Hz refresh rate and supports 5G connectivity. The phone runs on Android 11 and is powered by a Snapdragon 888 chipset. It has a 108MP primary camera, a 12MP ultra-wide camera, and two 10MP telephoto cameras, along with a 40MP front-facing camera. The phone comes with up to 16GB of RAM and up to 512GB of storage, and has a 5000mAh battery with support for fast charging and wireless charging.', 1199.99, 50, 'https://www.samsung.com/us/smartphones/galaxy-s21-5g/', 375, 0.15, NOW(), NOW()),
(2, 'iPhone 13 Pro Max', 'The iPhone 13 Pro Max is the latest flagship phone from Apple. It has a 6.7-inch OLED screen with a 120Hz refresh rate and supports 5G connectivity. The phone runs on iOS 15 and is powered by an A15 Bionic chip. It has a triple-camera system with 12MP ultra-wide, wide, and telephoto cameras, along with a LiDAR scanner. The phone comes with up to 1TB of storage, and has a 4373mAh battery with support for fast charging and wireless charging.', 1099.00, 100, 'https://www.apple.com/iphone-13-pro/', 280, 0.12, NOW(), NOW()),
(2, 'OnePlus 9 Pro', 'The OnePlus 9 Pro is a flagship phone from OnePlus. It has a 6.7-inch OLED screen with a 120Hz refresh rate and supports 5G connectivity. The phone runs on Android 11 and is powered by a Snapdragon 888 chipset. It has a quad-camera system with 48MP ultra-wide, wide, and telephoto cameras, along with a monochrome camera. The phone comes with up to 12GB of RAM and up to 256GB of storage, and has a 4500mAh battery with support for fast charging and wireless charging.', 969.00, 75, 'https://www.oneplus.com/9-pro', 320, 0.10, NOW(), NOW()),
(2, 'Google Pixel 6 Pro', 'The Google Pixel 6 Pro is the latest flagship phone from Google. It has a 6.7-inch OLED screen with a 120Hz refresh rate and supports 5G connectivity. The phone runs on Android 12 and is powered by a Tensor chipset. It has a triple-camera system with 12MP ultra-wide, wide, and telephoto cameras, along with a 12MP front-facing camera. The phone comes with up to 12GB of RAM and up to 512GB of storage, and has a 5000mAh battery with support for fast charging and wireless charging.', 899.00, 50, 'https://store.google.com/us/product/pixel_6_pro', 245, 0.08, NOW(), NOW());
INSERT INTO product (category_id, name, description, price, product_stock, link_image, view, sale, create_date, update_date)
VALUES
(3, 'Lenovo IdeaPad 3 15.6"', 'AMD Ryzen 5 3500U, 8GB RAM, 256GB SSD, Windows 10 Home', 599.99, 10, null, ROUND(RAND() * (400 - 40) + 40), 0, NOW(), NOW()),
(3, 'ASUS VivoBook 15.6"', 'AMD Ryzen 5 3500U, 8GB RAM, 512GB SSD, Windows 10 Home', 699.99, 15, null, ROUND(RAND() * (400 - 40) + 40), 0, NOW(), NOW()),
(3, 'HP Pavilion x360 14"', 'Intel Core i5-10210U, 8GB RAM, 256GB SSD, Windows 10 Home', 749.99, 5, null, ROUND(RAND() * (400 - 40) + 40), 0, NOW(), NOW()),
(3, 'Dell Inspiron 15.6"', 'Intel Core i7-1165G7, 12GB RAM, 512GB SSD, Windows 10 Home', 899.99, 20, null, ROUND(RAND() * (400 - 40) + 40), 0, NOW(), NOW());

INSERT INTO product (category_id, name, description, price, product_stock, link_image, view, sale, create_date, update_date)
VALUES
(4, 'Canon EOS Rebel T7 DSLR Camera', '24.1MP APS-C CMOS Sensor, DIGIC 4+ Image Processor, Full HD 1080p Video Recording', 499.99, 8, null, ROUND(RAND() * (400 - 40) + 40), 0, NOW(), NOW()),
(4, 'Sony Alpha a6400 Mirrorless Camera', '24.2MP APS-C Exmor CMOS Sensor, BIONZ X Image Processor, 4K UHD Video Recording', 899.99, 12, null, ROUND(RAND() * (400 - 40) + 40), 0, NOW(), NOW()),
(4, 'Nikon D3500 DSLR Camera', '24.2MP DX-Format CMOS Sensor, EXPEED 4 Image Processor, Full HD 1080p Video Recording', 449.99, 15, null, ROUND(RAND() * (400 - 40) + 40), 0, NOW(), NOW()),
(4, 'Fujifilm X-T3 Mirrorless Camera', '26.1MP APS-C X-Trans CMOS 4 Sensor, X-Processor 4 Image Processor, 4K UHD Video Recording', 1399.99, 5, null, ROUND(RAND() * (400 - 40) + 40), 0, NOW(), NOW());
select * from product;
INSERT INTO cart (user_id, product_id, amount)
VALUES 
    (1, 3, 2),
    (2, 8, 1),
    (3, 1, 3),
    (4, 11, 2),
    (5, 4, 1),
    (6, 13, 1),
    (7, 9, 2),
    (8, 7, 1),
    (2, 12, 1),
    (4, 5, 3);
    INSERT INTO `product_review` (`user_id`, `product_id`, `rating`) VALUES 
(1, 1, 4),
(2, 3, 5),
(3, 5, 3),
(4, 6, 4),
(5, 9, 2),
(6, 10, 5),
(7, 12, 4),
(8, 13, 5),
(1, 14, 3),
(4, 15, 5);
INSERT INTO `product_comment` (`user_id`, `product_id`, `comment`, `parent_comment_id`) VALUES 
(1, 1, 'Great product!', NULL),
(3, 3, 'The quality is excellent', NULL),
(2, 5, 'I am disappointed with this product', NULL),
(4, 6, 'Good value for the price', NULL),
(8, 8, 'I have been using this product for a while and it has been great', NULL),
(6, 10, 'This product exceeded my expectations', NULL),
(7, 11, 'The shipping was fast and the product arrived in good condition', NULL),
(1, 13, 'I would highly recommend this product', NULL),
(5, 14, 'The customer service was excellent', NULL),
(2, 15, 'I had an issue with the product but the company quickly resolved it', NULL);

-- Inserting a new order
INSERT INTO `order` (user_id, shipping_address, billing_address, total_price, payment_method, order_status)
VALUES (1, '123 Main St, Anytown, USA', '456 Broad St, Anytown, USA', 125.99, 'Credit Card', 'Processing');

-- Retrieving the ID of the new order
SELECT LAST_INSERT_ID() INTO @order_id;

-- Inserting the order details
INSERT INTO order_detail (order_id, product_id, quantity, price, discount, tax)
VALUES (@order_id, 1, 2, 49.99, 0, 0),
       (@order_id, 2, 1, 26.99, 0, 0),
       (@order_id, 3, 3, 16.99, 0, 0);
       select * from order_detail;
       
       INSERT INTO `order` (user_id, shipping_address, billing_address, total_price, payment_method, order_status, notes)
VALUES (3, '123 Main St, Anytown, USA', '123 Main St, Anytown, USA', 199.99, 'Credit Card', 'Processing', 'Please expedite shipment');

INSERT INTO order_detail (order_id, product_id, quantity, price, discount, tax)
VALUES (@order_id, 3, 1, 149.99, 0, 9.99);
----
INSERT INTO `order` (user_id, shipping_address, billing_address, total_price, payment_method, order_status, notes)
VALUES (5, '456 Elm St, Anytown, USA', '456 Elm St, Anytown, USA', 499.99, 'PayPal', 'Shipped', '');

INSERT INTO order_detail (order_id, product_id, quantity, price, discount, tax)
VALUES (@order_id, 4, 2, 199.99, 0, 19.99),
(@order_id, 5, 1, 99.99, 0, 9.99);
select * from order_detail;
select * from `order`;
select * from user;
select * from product;
select * from cart;
select * from carts;
select * from product_review;
select * from product_comment;
-- update user 2 to use cart.
-- change 10-03-2023
UPDATE `ecommerce`.`user` SET `password` = '$2a$10$8CdSbs.vQ/onS9q3.sY4JO3Sl.swjEOdncxaNq7XZ8/EBC.YN9cWK' WHERE (`id` = '2');
UPDATE `ecommerce`.`user` SET `password` = '$2a$10$tSFn.9dJlMq/RlMj3rwVGeGaF0HCS9bNqNxA.qlff9qzzd/QxyVie' WHERE (`id` = '1');
UPDATE `ecommerce`.`user` SET `user_role` = 'admin' WHERE (`id` = '3');

ALTER TABLE user ADD reset_password_token VARCHAR(255);
UPDATE `ecommerce`.`user` SET `email` = '19130222@st.hcmuaf.edu.vn' WHERE (`id` = '3');

-- end 10-03-2023
select `order`.* from `order` join order_detail on `order`.id=order_detail.id 
						join product on order_detail.product_id=product.id
                        join category on product.category_id = category.id
                        where month(`order`.order_date) = 2 and year(`order`.order_date)=2023 and `order`.payment_method !='not payment yet';
-- update 19-04-2023
ALTER TABLE product ADD is_best_selling BIT NOT NULL DEFAULT 0;
UPDATE `ecommerce`.`product` SET `is_best_selling` = 1 WHERE (`id` = '3');
