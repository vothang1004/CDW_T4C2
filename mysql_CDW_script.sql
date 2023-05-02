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
-- update 02-05-2023 - data image
ALTER TABLE `ecommerce`.`product` MODIFY link_image  longtext; 
UPDATE `ecommerce`.`product` SET `link_image` = 'https://cdn-amz.woka.io/images/I/81fZmxBbQgL.jpg' WHERE (`id` = '1');
UPDATE `ecommerce`.`product` SET `link_image` = 'https://resource.logitechg.com/w_692,c_limit,q_auto,f_auto,dpr_1.0/d_transparent.gif/content/dam/gaming/en/products/g502x-plus/gallery/g502x-plus-gallery-1-white.png?v=1' WHERE (`id` = '2');
UPDATE `ecommerce`.`product` SET `link_image` = 'data:image/jpeg;base64,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' WHERE (`id` = '3');
UPDATE `ecommerce`.`product` SET `link_image` = 'https://cdn.tgdd.vn/Products/Images/44/240249/lenovo-ideapad-3-15itl6-i5-82h80042vn-2-org.jpg' WHERE (`id` = '8');
UPDATE `ecommerce`.`product` SET `link_image` = 'https://cdn.tgdd.vn/Products/Images/44/134806/asus-x541ua-i3-6100u-xx272t-dai-dien-10-450x300-600x600.jpg' WHERE (`id` = '9');
UPDATE `ecommerce`.`product` SET `link_image` = 'https://cdn.tgdd.vn/Products/Images/44/302212/hp-pavilion-x360-14-ek0134tu-i5-7c0p8pa-050223-090333-600x600.jpg' WHERE (`id` = '10');
UPDATE `ecommerce`.`product` SET `link_image` = 'https://cdn.tgdd.vn/Products/Images/44/115427/dell-inspiron-n3567e-p63f002-core-i5-7200u-8gb-1t-1-450x300.jpg' WHERE (`id` = '11');
UPDATE `ecommerce`.`product` SET `link_image` = 'https://encrypted-tbn1.gstatic.com/shopping?q=tbn:ANd9GcT8jXknzm3icgEvumSu2N69L4bfJvMtdc9cLr9nxSa5i1jHMIxCaUeSNeLaF2GCxuO-Q-UIpBICA09zXvgoaVR61qTtTf13hqn9E4P3oLjyzRFrqgUy-AnL&usqp=CAE' WHERE (`id` = '12');
UPDATE `ecommerce`.`product` SET `link_image` = 'data:image/jpeg;base64,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' WHERE (`id` = '13');
UPDATE `ecommerce`.`product` SET `link_image` = 'data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAoHCBUVFRgWFRYZGRgZGhocHBwaHBwZGBocGR4aGh4eHBwcIS4lHR4sHxocJjgmKy8xNTU1HCQ7QDszPy40NjEBDAwMDw8QGBESGDEkGCE/NTQxPzE4PzQxMT82NDQxMTE1MTU0PzYxMTQ0MTExNDExMTQxNjMxMTQ+MTQ0ND80Mf/AABEIAM8A9AMBIgACEQEDEQH/xAAcAAACAgMBAQAAAAAAAAAAAAAABQQGAgMHAQj/xABIEAACAQIDBAYHBQYEBAYDAAABAgADEQQhMQUSQVEiYXGBkaEGBxMyscHwQlJygtEUNWKys+Ejg5Kic5PC0hUlM1Oj8RYXQ//EABkBAQEBAQEBAAAAAAAAAAAAAAABAwIEBf/EACMRAQEBAQACAQIHAAAAAAAAAAABAhEDEjEEIRMUMkFDYYH/2gAMAwEAAhEDEQA/AOzQhCAQhCAQhCAQhCAQhCAQhCAQhCAQhCAQhCAQhCAQhCAQhCAQhCAQhCAQhCAQhCAQhCAQhCAQhCAQhCAQhCAQhCAQhCAQij0l20mDw713z3RZV0LsclUdp48Bc8JwjH+lWJxbk1qrWOiKStNRyCg27zc9cDv+09pU8OheowAHC+ZyvYDUnLQCI6fp1gyL7/ZwPg1rTkdDArYHIDnkPOTaOBvmpB7CD8DA6Y3p9hRoHPY1H51Jknp5hDqWXtNL5VDOdLhHH3v9JPyM8bBsdd7wI+UDpFX06wYIAqb1yAN0Ftba20lhwuKSooZGVgRcWII8pwyvgwPeZR2sB5EyDi8IFG9l2/3gfRUJ8++jfpziMJUALtUo36VNjvdHjuE+63LgePOd7wmISoi1EIZHUMpGhVhcHwMDfCEIBCEIBCEIBCEIBCEIBCEIBCEIBCEIBCEIBCEIBCEX7b2kuGoVK7+7TUm3M6BR1liB3wOTeuTbu/WXDIejRG83XUcZD8qH/eeUomwcH7V2uSFRd4kanMAAcr315AyNtDFPVd6jm7uzMx5sxJPdc6SdsXalKgj75O+5GQ+6oNvNm8IF99DMF7euad2RFQsSlg17gDpMDzPhJ22thtSpjfqVDUfFeyTpAAoSbMQBrujX+KUzY/pvTw9LEIqE1KybivvWCCzC9rZnpE8Izx/rNp1a2FdqR3aBZmXf992UAEHdyAIvx1gWH/8AHkevikVq+7Qppurv3ZqrLvAXta2gtzJiMejOLNR6ftU/wlBqOajCml891mK5NbMgDLiRI+zfWWlL2zCmS9bEe1Y71rJvA7g6Ou6LX8oYD1h4ZDiVfDsaWIqGpuq1mUkqWUmwupZb95GcCQmwMeuIOGV7OKe/vb53GS9rhrX1uLEDQxX6S4DE4fcGIZG9opZSCHNhYEbxF1OY0NusyRifWMKjYpzTs1akKKWb/wBJLOOK9JiXJ4ZxP6T+lSYuojKvs0p01RF3t6wUkk3sNb+QlQoqU9xu0Ag9v15Tsnqh237Sg+GY9Kkd5OZRjmPyvfuZZx2pVV0Uqb2up7L7w+J8I29EdtHCYqnVz3QbOOaNk+Q1IGYHNRIr6ShNdNwwBBBBAII0IOhE2QCEIQCEIQCEIQCEIQCEIQCErfpN6X4fBFUdt6oxAFNSpdQQx32F7hOja9jmRlEp9Ye6VZ6CpTJz/wAXeqnkEphLOSbfaEC/TS+IQGxZQeRIB85RcV6x1LbuHoM/WzADwW/xlXOz3rM9R1G87u7EkkksSbXysFFlHJVUcIHV8btFUW6lXYkKq7wAJPMgEjIHgZSMb6z/AGbFThr25Vf1pyqphSyUsRQRgadUOQWDBxTY33HVRZWtk1jdWiirs+tiQaqGkF1I3yzrfmoXzgXb/wDbyXscK+oGVRTrl90RhhvWWrozjDsArbpBYfpOYL6NuTdqq5kHooToetxGlLCLSpsly123iclz5cYD/E+uoKxVcGzEG2dUL8EaKPTr0+XHYajTpjcDdOqu9vMrKzKqE2sRlv8AA+6cpRdp0qasSozJOZJP6DykNMoHterYdchEx1isCFIU+8oG91McyO64HaDI/wCxrAWQvGf7GsP2NYCy8Lxl+xrPDg1gLrwjA4JYfsawIlCsVNx3jnGquCAw0Mi/sYkzDUgEZeIO8OzRh8D3GB2/1T7e/aML7BmvUoWXPU0zfcPdmv5Rzl+ny1snalbDVVrUGKuvHUEHVWX7Sm2nUCLEAzsXox6z6FeyYlfYVPva0W/Nqn5sh94wOhwmtKgYAqQQRcEG4I5gjWbIBCEIBCEIBCEIGmtVVFZ3YKqgksxACgC5JJyAA4zjfpf60XqVPZYMFaINme7I9UclIs1NesWY8CsPW96WF3OCpNamhHtSPtvqE/CuRP8AF+GcywwuYDetiDUc1GVFZuCIEXnoMycySzEk8SZDTEkOGORBFr8Dy8fGS6S3jLDYVDbeAPdeVG30ex1OlvisSu61kAzO7u3Fwc7cL8byXX237aph6AD1E9q71NxGZqiKW3ENMCxUggNla9uUi1vR+nVcN0lFrFVyDdsZ7G2bTwzO1FemRunPfYDWwHWR5CRTT0i2tU9gxqIMNQsRu7ytiao+6oU7tMHQm5IF9JznZu0mNX2jFlQuu8ENrKct0chawjnb2AxNd95yLcN9lW35b73lEYo+xV6ZdSWKt0SSBuHesbgcAZz7Z7yX7u74dye1lkWUbXzuAiqfdDB2yGWgJPeT2TVjdoh0NgFaxsV3txrC5FmzU/24G81UsF7VFdM77oNuoAEXHHK/hDauz/ZIis241QgdLULZgWta4GZseJB5STduucLmTPeqm9Xfa8YbHohn3m91BvnkSPdHe1u4GS6+zqS3CNvWGs2UaQSmF4ud9vw5hB4Xb807cMWS5JOZJJJOtzrMRSByAufGe4bCMzC4IW5LMWRUCkGxOW9ckWue69hGbYpE9xbdoudLGynhqbvc5+7KiJR2Q7DesFXK7EgKARcEkm1rcr8OYkujsRDYl2IIGdNGdevp5IPGQMXtmxuWAYaH33HYT7n5d0RVX2wXN7Mx5sc/nAsrbNwwyJc9ftcMvkXJmI2ZhzkrOD1vQf8A2o4JlW/b6h0VfP8AWZDHVOKDzgWLEbHUWIfdBNv8VHpZ9RIIPjIlbZzpmyG2XSHSXPTpDLgctcovw21mTTfS+pUnPttwjLC7VvmLdqWQ63zUDcbPmt+uBG9mJmigG8lOyOPsrpmosAOO+mdu0Erw45QEpsG6YsRy3bNllYjM87nsgaK1PdYjhw7DpN9NeM9xCXUHiuR7D/f4yfsLB+2qLT3gm9cbxBIBAJGnMi3fAm7C9IsRhD/gVCq8UPSpntU5A9YseudM9HvWRQrFUxC+xdiAGvekxP8AFql/4suuUdvQ2oFLmqgC+9k5tbf38wue6UYZanIZxTtbYz4dlV2VrjPduQGARipuBchaiG4y6XbA+jISnerXaxr4Xdc3ai25c6lLAoT3Er+WXGRRCEIBF+3MeMPh61ci/s6bvbmVUkDvNh3xhKd61HK7LxBBtnSHca1MEeBMDgNWhUqMWbpMxLMxIuzMbsTnqSSe+NtlbHDNZnVTa+rHyVGPw0kTBm//AN/XXJjjLtyte3l9d0Ulkv3nT07JoJ79YdyOD4sR8J57TCL9tm7WA/lAPnK+4YcWHVe3kWsfOa23ud+ssLX7Ljq4njzmdxq/u9OfN48/xy/6s67awye4iE/xXc+ZMh4/0vNt0aclFhKvWDfev2XHZYCLa+V5z+DL821r+d5+nEhntDbzvcDKJTUJN7m+vfzmBmQWaTOc/Eeby+bflvdU0wW3Hp5gsDxKsVJ7bSPitpvUffY3PWSSSbXJJzJyHhILjOeCdMj/AGbV32VNAc2PJQLsb9QB8o6SnvE1HFlJuq6g5iwtlfIWA6rnIZrvRnBdB3bRju3JsNxek5vyJA7laSsdihmTkq8Mhy1A4nK/cvAyowx2N4sbKvXex7dWbr8LCI62MZzZeiPM/pMWLVmufd4D64yxbFwlFbtUJuLWFr3ByuMxmDa4zNiSNLgE2B2M9TMDLO7HJRYFjdjkMgTmY8wGxKQBNVgpV3UodTurw698qLXFxexymO0NvAbwpIFByyy+4DnwvuLdVyytzvXMRj6jasR+HLz1844ntPhZlo0RTYBSXJFibjd56GxHDPPPhaS62GwxD2tcBtwDfF7e7vb41sDe2V9wDViKA7E6kntN4U6jL7rEdhI+EKvSejoqIrIykkKCOTHMjL7qsl/zfdiLGbHZLMARfRgQVOujLcHQyFhNsVUIIa9u5v8AUPneWbZu30q7q1CeiFFt0HoKWuhGm4d4X3c7KMtCAr1PFMhs/cw+cmLiLC2qnhwB4EW49UmbWwtLfK0yWUZXOdyLjI2Fxax7SeFjEoUod0+6dDyPKA0WwOt1bK/Ufq/dJWDxJpOrrk6MCOOYPLjFuHqWO63unyPOb6ykWJ7D2j9RbzgWfG+k1ZNx+hvHfYN7NSbkKGN7ZXQBbcRwMXYvbNTEBA+7YFmWyhT07ZHjYAAAHQC0MOQ9Fk4jMd315Rdgj/iID94fGB0D1VYkpjatLg9HeP4qTqB5VGnXJxz1c/vT/Iq/zU52ORRCEIBKb62f3XiO2j/XpS5Sm+tn914j8VH+tTgcQwOgjJKZOn1p4n4RdgOEa0tD5ctOcI07g5Zcba2yOtszkc9B2zUyZadXMDjYcTxF5MIuc/rX5fGaqg7OH99NT+h7wT4terr018hYZRLimztHG0D9cvq0QsbmFeqJmongE2bv1+kCPU1hTQsQALkkADmTkBPGOZjn0Twu/iU/g6XfcKp7mZT3QLPVpilTSkp0Vb8srm/e4dz1IvOVfaFTffcHurr2/XmTHm1sULO40Oa9S2G6O5Ag7zEezqWW8dTme+VEzDUQBJIpFxyXzY8fy/Hs18p094hBlfXqUan4DtIjKsAq2AsALAcABNMZ7968X1f1FxzGfmq7i0AimsY3xZLNZfrrPVFrr93P+I8ewcpxrUafT5vr1EtPJKXBO2dj3zF8IwkepHvM1a2YNiOI1mJHOeSCwbOx2+N1vfH+4c+2bsTSDAgyu03KkMNRmJZKdQMoYaEXlRAp3sVPvL58vHSOtmYc4hCiAtUuu5na4zyN8r2NvyxXVSzqeB6J7G0PcbGSNmVSlUdZ48zp/vH+6BL2ZWKPY87EeREzeluYhQNN8EeM1YxwXDgAb+8WUaK6sykDqyB/NJmJ6Rov1gHuIgWn1cfvP/JqfzJOyzi/q5YDaa9dKoB29A/AGdokUQhCASl+tr911/xUf61OXSUz1tD/AMrr/io/1qcDiOz+EdU7HiL9sUbP4fXjHKaHTPs6v7DwhHgUZG1tfK+WnbNFc2HX9X+X1abqmpHxzz+r+ci4gZcPPLjKEuOiPjHuMXIxGPekVuCzMKL9x/T5z1R9fQmXH8rf9MBfxlr9DkYJiHH3QoPG5VwLfmKnulUlt9GGtQqZ/aXK/wDHQ4eOfX1wI23HuoUaM3kSSP8AaF8p7h1sBNWPzNPtH8gj3ZNQLSe4P2h7hIF0yuwGYJAupsNDwMqIWFqAMx5WX/qPxXwmrH4zKL6+I3WYdfyEg1695p7czI8GvB7eW6qfTG9Zfv5sf4QbAd5+Esuz9ihwDbsGQGXEk5AAZ3OQAJlPpYizdiqPIH4ky4rtcJRI5rbuupJHcLdhM8flmta7PiPp+GZznlJttVwhsu8R94CynsvnbrPgNIkGLbXUdYse60sHpBtqhWp7qoQ4AC2067/KQB6P1VQObbjA5g3GQvfx+E08er6y6nEs7bwrxChhcSJJSDI9pkcjWaOXgj/0dXf6GfvZWAJsQToWA1B1IiECONgISxF925QbxyCm5AN+Fr37oTpjtrBFGZCCOiDZipOnHdJAzByvy11kSu5DK4yuA/eVWp8VMn7SohGI3kNxchDcKTqpzOY7T2xfihZKPXTX41E+AgM8Mg9s6oEYm7MagDoqmwULkW3tATleFWoUdRugC9wqlihI5b2atIL1yta41NFL+Uzq1bqb8ie8ZjzgXT1eVL7TpEcUqfyE/KdvnC/Vy19p0TzRye00rnzv4zukiiEIQCUz1tD/AMrr/io/1qcucpnraa2y6/W1Ef8AzUz8oHEtnmOKZ6u8Xv5RNgPr604xwjHv7rjUZ311hGb5j4DO30ReRMQLfXVc38pLc8frnlzkXEDL+/Vz+coTY3SIx73fHmOPnES+9IqWomZ17iPnl4TxRMjqOYPxy+cBYdZY/RqqPZ1VOoKsOy4Y9nuCV+stmMY7BrbrlTo6kG/mf9O94wJmOGSHkyjyK/ERrssndcb5S4AIDIoYG4sS7qePAHU881mJQsjLx17xn8QZO2JjFVgzMyqy2Yp71sjYd4A5Sor+1AVqN12Plb4gyExli9KsKd72m6wubneG6bMbm44Wa+WdriVuOnJ8si2nhG6Vd+mBxH1Y9RiWbqNYoeqB61Kxte3br/eNae13FP2Qa9xbqA42kE4lTr5zWa4GnlFkqy2JW4LWkJhx5nLsHHx+Ez9qSM8hxPE9QgvSN/AchynGZe9qa1JBSp3jzYNIhwRe++pFr36F2NrK3VwPYZCpU462ZXWkwLAmw+ybG5tfMEEZdG4PjodLORhjXtr+o823VJdgWuEG4umQX7IsBkCSBkMuAkDawt7FOIpU787shqZ/8wTY1NqrqgPSqOFuOBc5m3AC9+yYbQritiWZfc3jbqQHo+CKonLdoxwK1A1sggXgDlxsezzkcVy7BQNSLKMyTwvbK3VNmMVXck8NIwwFBEKEKLlhnx1EqrV6uKZXaWHU67tS/b7Nz8Z3ecO9Af3pR/DV/kedxkBCEIBKV63P3ZV/HR/qpLrKX62/3ZW/FR/q04HEMFHFM5a/WnHI6xRhNIzpsQOY7NOrlCNjNny4cj2ZDlNVY34eHz5CZObfDXXu+Zymio2X138h1ShVjjrEq+9HGNPwidPeMipazMnIzBfq02L1fL6+jAhYxcw3P4zVSqlWDDUEHwk2vTuCO8fP664vgWMVQSGByP1n16HvM1USUcqMs95D52HYZC2dX+we0ch9fMye6b67ujrmp+vDwlQ6H+OjXAuoUZnM7xtZEXdVQTlYAneZRmTeU7G4Y02sdOB5/wBxHuzcaVYNmCMmUHdJHEE8j3jtjfGYZMTkubsFYm9lBO6qpc574OW9x45XsFDtPJOxWzmTMdJeY1HaJChXk9ELQgZLnJuHWRKYubAXJ4DMmPNm7Md2C2F/u3A426RJyzIFtc7axGWs2/aN2ColyLDsvplmSf4Ra57OOcn4yoEQIj7ym/RYdJCNSQy2VyTquYC2udYVQtNVem4LEWyJVkYAb1lsMrErx0PMWgVnas5G90muzsdEXixt2iwGtwNSJbeusYmZyMsM+4r1jwDU0/Ews7D8KHd7XXlIGGbdRnOrZDv+WVpsxtUVGWmmSILDqAN87ZFiSWPWZExde53R7q5DlfjI7Z08zaNMObug5MvxizBrxjHB++n4h8YRdPV2L7UTqp1P5bfOdunEfVx+9E/4dT+UTt0iiEIQCU/1qC+y8T/leVamZcJUfWn+68R/lf1qcDhOF0+j5RkuY08dNMs4rwpjJCLXz8QLed78YRnfLyysAMuPLhqbzTUfw0GdhfLr5TPf6zfhmL3v4fpnI9Q8s+HPt4aXlC3F6RSvvRziInbJjIqZfj9ecyAv/eah+k2r9XgDDq/t237ZCxNP7Qk8ia3TwOo+cBaDG2GxG8NbOPP6/tFlVN09XCYq1tID16Rcb69FxkeRtzmeFxzLdblGIswva4PxGvnIWGxt9TY+R7pMZ0cAOoP19lh9c5UOMPXoutqilW131sL9W6ARpwsLkai5MV1cMje8oJ56HxE1rh2HuOG/hfonub3T32mTs6C703Ufe3SU7mGR7oGltlU/4h2EfMT1NmUxwY9p/S0yXHIdG8jA41NN74wHOC2bSWmKhbdUllIUANkrkWJPSNwvRJBN+GRJ/wCKlECIbFD0XQlR719+xz3jmL5dFiCIvTCV3G8tJwv33tTS343IU+M2DC0k/wDVf2p+5TuqfmqMLnsUHqYQI6K9UtuWsM3c5IgPEnmeAFyeAMwxThE9nSv0jcscmcj7TcgLmy8L8SSZtxW0C4CjdVFvuqo3aaniQPtNzJJJ4mJ8RjNQuvFv0hWVaqEXcX3j7x49Ykaku8ZqUEmNMLQsJBvpJYSVg/fXtE0Cb8J769sIunq2/ei/8Op8BO3TifquN9pm3/sVP5qc7ZCiEIQCVT1mrfZmJ/Ch8KlM/KWuJPS/ZzYjBV6KZu6HdGl2BDAX4XIt3wPnPDD6+tYwTtPhew8e+2UT4xqmHcpVRkYaq6lW8GzmxNsJbO9/w3Hm0BoW4fEnz6+PGaahGp88xllr9ayA2114eYtMf/FVPEd94RsrRRX96TnxSn7a+c0Ngqz2ZabkHMFVZrjnkIVimk3r9cPoT1dn1rZ0K3/Lb/tmX7FVH/8ACt/ob/tgYwMy9g41pVR+Rv0gaT6+yqd6t+kCO9C48x1SC6EGxjN3I1Rx2qZprOpHSDDrsRAhAzdTxTLlqORml7A5G4hcQGNPGrzI7cx5/rJuHxzLmjWPNHKH5fGIDbnBbX1gWtds1vvue3df43gdt1+Duv4WFP8AlIlV3zzPjDfPEmA8r4wk7zsC3NmLN9d8hVsavW3bkPD9bxcSJ6pHG8DdVxDNrpy4TBKZM206iDUN5frNv7YnBT5QM6FALrrJatIH7aOXnPRjhy84DINBqoXOQaWLZjZULNyFyfACONmeh+0cSw3cNVAy6TqaaW6i9ge6Bc/UuhfG1KnBaDL3u9MjyUztsqPoD6JjZ9EhmDVahBcr7oC33UW+ZAuxvxLHqlugEIQgEIQgaMRhkcWdFYcmAYecinYmGOuGo/8ALT9IxhAVN6O4M64XDntpUz/0zW3otgTrgsKe2hT/AO2OYQFVD0cwSG6YTDoea0aanyWNYQgEIQgEIQgEIQgE8nsIHloWnsIHloWnsIHloWnsIHloWnsIHlp7CEAhCEAhCEAhCED/2Q==' WHERE (`id` = '14');
UPDATE `ecommerce`.`product` SET `link_image` = 'data:image/jpeg;base64,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' WHERE (`id` = '15');
