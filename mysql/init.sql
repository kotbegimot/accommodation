CREATE DATABASE hotels_db;
USE hotels_db;
CREATE TABLE IF NOT EXISTS locations (
  location_id INT PRIMARY KEY AUTO_INCREMENT,
  city VARCHAR(255) DEFAULT NULL,
  state VARCHAR(255) DEFAULT NULL,
  country VARCHAR(255) DEFAULT NULL,
  zip_code VARCHAR(255) DEFAULT NULL,
  address VARCHAR(255) DEFAULT NULL
) ENGINE=InnoDB;
INSERT INTO locations(city, state, country, zip_code, address)
VALUES
("Santa Monica", "California", "USA", "90401", "1670 Ocean Ave Santa Monica"),
("Budapest", "Budapest", "Hungary", "1026" , "Szilagyi Erzsebet fasor 47"),
("Dusseldorf", "Nordrhein westphalia", "Germany", "40221" , "Volmerswerther Deich 658"),
("Luton", "Luton", "United Kingdom", "LU2 9RN" , "100 Oving Cl");
CREATE TABLE IF NOT EXISTS hotels (
  hotel_id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255) DEFAULT NULL,
  rating INT(1) DEFAULT 0,
  location_id INT,
  category VARCHAR(20) DEFAULT NULL,
  image VARCHAR(255) DEFAULT NULL,
  reputation INT DEFAULT 0,
  reputationBadge VARCHAR(10) DEFAULT NULL,
  price INT DEFAULT 0,
  avaliability INT DEFAULT 0,
  FOREIGN KEY (location_id) REFERENCES locations(location_id) ON DELETE SET NULL
) ENGINE=InnoDB;
INSERT INTO hotels(name, rating, category, location_id, image, reputation, reputationBadge, price, avaliability) VALUES ("California", 5, "hotel", 1, "img.com", 999, "green", 150, 30);
INSERT INTO hotels(name, rating, category, location_id, image, reputation, reputationBadge, price, avaliability) VALUES ("Budapest", 4, "hotel", 2, "budapest_logo.com", 850, "green", 78, 10);
INSERT INTO hotels(name, rating, category, location_id, image, reputation, reputationBadge, price, avaliability) VALUES ("California hostel", 3, "hostel", 1, "california_hostel.com", 650, "yellow", 45, 6);
INSERT INTO hotels(name, rating, category, location_id, image, reputation, reputationBadge, price, avaliability) VALUES ("Near the Rhein", 4, "guest-house", 3, "river_img.com", 800, "green", 90, 2);
INSERT INTO hotels(name, rating, category, location_id, image, reputation, reputationBadge, price, avaliability) VALUES ("Cool house", 2, "alternative", 3, "image.com", 450, "red", 38, 15);