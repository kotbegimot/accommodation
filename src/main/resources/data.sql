INSERT INTO locations(city, state, country, zip_code, address)
VALUES
('Santa Monica', 'California', 'USA', 90401, '1670 Ocean Ave Santa Monica'),
('Budapest', 'Budapest', 'Hungary', 10260, 'Szilagyi Erzsebet fasor 47'),
('Dusseldorf', 'Nordrhein westphalia', 'Germany', 40221, 'Volmerswerther Deich 658'),
('Luton', 'Luton', 'United Kingdom', 29600, '100 Oving Cl'),
('Omsk', 'Omsk district', 'Russia', 64402, 'Slesarnaya str. 666');
INSERT INTO hotels (name, rating, category, location_id, image, reputation, reputation_badge, price, availability) VALUES
  ('California hotel', 5, 'hotel', 1, 'http://california/img.com', 999, 'green', 150, 30),
  ('Budapest hotel', 4, 'hotel', 2, 'http://budapest/budapest_logo.com', 850, 'green', 78, 10),
  ('California hostel', 3, 'hostel', 1, 'http://california/california_hostel.com', 650, 'yellow', 45, 6),
  ('Near the Rhein', 4, 'guest-house', 3, 'http://river_houes/river_img.com', 800, 'green', 90, 2),
  ('Cool house bro', 2, 'alternative', 4, 'http://coolh/image.com', 450, 'red', 38, 15),
  ('Chernihovskaya hata', 1, 'resort', 5, 'http://nobody_can_leave_Omsk/image.com', 300, 'red', 15, 0);