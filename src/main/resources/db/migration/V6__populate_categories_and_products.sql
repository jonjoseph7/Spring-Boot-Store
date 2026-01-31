-- =====================================
-- Insert 10 product categories
-- =====================================
INSERT INTO categories (name)
VALUES ('Electronics'),
       ('Books'),
       ('Clothing'),
       ('Home & Kitchen'),
       ('Toys & Games'),
       ('Sports & Outdoors'),
       ('Beauty & Personal Care'),
       ('Automotive'),
       ('Music'),
       ('Garden & Outdoor');

-- =====================================
-- Insert products for each category
-- =====================================

-- Electronics (id=1)
INSERT INTO products (name, price, description, category_id)
VALUES ('Smartphone X', 799.99, 'Latest smartphone with high-resolution camera and OLED display.', 1),
       ('Laptop Pro', 1299.99, 'Powerful laptop for work and gaming.', 1),
       ('Wireless Earbuds', 199.99, 'Noise-cancelling wireless earbuds.', 1),
       ('Smartwatch 5', 349.99, 'Smartwatch with fitness tracking and notifications.', 1);

-- Books (id=2)
INSERT INTO products (name, price, description, category_id)
VALUES ('The Great Adventure', 14.99, 'An exciting adventure novel.', 2),
       ('Learn SQL', 39.99, 'Comprehensive guide to SQL for beginners.', 2),
       ('Mystery of the Night', 19.99, 'A gripping mystery novel.', 2);

-- Clothing (id=3)
INSERT INTO products (name, price, description, category_id)
VALUES ('Men T-Shirt', 24.99, 'Cotton t-shirt with comfortable fit.', 3),
       ('Women Jacket', 89.99, 'Stylish and warm jacket for women.', 3),
       ('Running Shoes', 59.99, 'Lightweight running shoes for everyday use.', 3),
       ('Baseball Cap', 19.99, 'Classic baseball cap with adjustable strap.', 3);

-- Home & Kitchen (id=4)
INSERT INTO products (name, price, description, category_id)
VALUES ('Blender', 49.99, 'High-speed blender for smoothies and soups.', 4),
       ('Non-stick Pan', 34.99, 'Durable non-stick pan for easy cooking.', 4),
       ('Coffee Maker', 79.99, 'Automatic coffee maker with timer.', 4);

-- Toys & Games (id=5)
INSERT INTO products (name, price, description, category_id)
VALUES ('Building Blocks', 29.99, 'Colorful blocks for creative play.', 5),
       ('Board Game Deluxe', 49.99, 'Fun board game for family and friends.', 5),
       ('Puzzle 1000 Pieces', 19.99, 'Challenging puzzle for all ages.', 5),
       ('Remote Control Car', 39.99, 'Fast RC car with rechargeable battery.', 5);

-- Sports & Outdoors (id=6)
INSERT INTO products (name, price, description, category_id)
VALUES ('Yoga Mat', 29.99, 'Non-slip yoga mat for home workouts.', 6),
       ('Tennis Racket', 89.99, 'Lightweight racket for beginner and pro players.', 6),
       ('Camping Tent', 149.99, 'Durable tent for 2–3 people.', 6);

-- Beauty & Personal Care (id=7)
INSERT INTO products (name, price, description, category_id)
VALUES ('Shampoo', 12.99, 'Nourishing shampoo for all hair types.', 7),
       ('Face Cream', 24.99, 'Hydrating cream for daily use.', 7),
       ('Makeup Kit', 49.99, 'Complete kit for makeup enthusiasts.', 7),
       ('Electric Toothbrush', 79.99, 'Rechargeable toothbrush with multiple modes.', 7);

-- Automotive (id=8)
INSERT INTO products (name, price, description, category_id)
VALUES ('Car Vacuum', 59.99, 'Portable vacuum for your car interior.', 8),
       ('Dash Cam', 99.99, 'High-definition dash camera with night vision.', 8),
       ('Tire Inflator', 39.99, 'Compact tire inflator for emergencies.', 8);

-- Music (id=9)
INSERT INTO products (name, price, description, category_id)
VALUES ('Acoustic Guitar', 199.99, 'Beginner-friendly acoustic guitar.', 9),
       ('Bluetooth Speaker', 49.99, 'Portable speaker with deep bass.', 9),
       ('Drum Set', 399.99, 'Complete drum set for practice and performance.', 9);

-- Garden & Outdoor (id=10)
INSERT INTO products (name, price, description, category_id)
VALUES ('Garden Hose', 29.99, 'Durable hose for watering plants.', 10),
       ('Lawn Mower', 299.99, 'Electric lawn mower for small to medium yards.', 10),
       ('Patio Chair', 49.99, 'Comfortable chair for outdoor use.', 10),
       ('BBQ Grill', 199.99, 'Gas grill for outdoor cooking.', 10);
