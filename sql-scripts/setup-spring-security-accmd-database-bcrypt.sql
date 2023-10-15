USE hotels_db;

DROP TABLE IF EXISTS authorities;
DROP TABLE IF EXISTS users;

--
-- Table structure for table 'users'
--

CREATE TABLE users (
	username varchar(50) PRIMARY KEY NOT NULL,
	password varchar(68) NOT NULL,
	enabled tinyint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Inserting data for table 'users'
--
-- NOTE: The passwords are encrypted using BCrypt
-- A generation tool is available at: https://www.bcryptcalculator.com/encode
-- Default passwords here are: test123
--

INSERT INTO users
VALUES
('devon', '{bcrypt}$2a$10$goLOWsblvk3LycfYKnwQYejFliUNQkf9KNG8K0mAer0M2VNL3FxHm', 1),
('kirk', '{bcrypt}$2a$10$BfxYPKRUgVgkKWOyNNJfk.IJvIq7xnWWUmdYot1fcCHl8Jt65QYP2', 1),
('nagibator', '{bcrypt}$2a$10$wQ.T2H4YpGCOT4tX8eR0n.x2SNZycFD.tu33Fxwf.3Mhid.WQ2EJO', 1);

--
-- Table structure for table 'authorities'
--

CREATE TABLE authorities (
	username varchar (50) NOT NULL,
	authority varchar (50) NOT NULL,
	UNIQUE KEY authorities_idx_1 (username, authority),
	CONSTRAINT authorities_ibfk_1
	FOREIGN KEY (username)
	REFERENCES users (username)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Inserting data for table 'authorities'
--

INSERT INTO authorities
VALUES
('devon', 'ROLE_CUSTOMER'),
('kirk', 'ROLE_CUSTOMER'),
('kirk', 'ROLE_MANAGER'),
('nagibator', 'ROLE_CUSTOMER'),
('nagibator', 'ROLE_MANAGER'),
('nagibator', 'ROLE_ADMIN');