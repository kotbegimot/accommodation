DROP TABLE IF EXISTS HOTELS;

CREATE TABLE hotels (
  hotel_id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) DEFAULT NULL,
  rating INT DEFAULT 0,
  category VARCHAR(20) DEFAULT NULL,
  location_id INT DEFAULT 0,
  image VARCHAR(255) DEFAULT NULL,
  reputation INT DEFAULT 0,
  reputation_badge VARCHAR(10) DEFAULT NULL,
  price INT DEFAULT 0,
  availability INT DEFAULT 0
);