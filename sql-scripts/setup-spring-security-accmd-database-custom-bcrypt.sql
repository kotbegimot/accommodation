USE hotels_db;

DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS members;

--
-- Table structure for table 'members'
--

CREATE TABLE members (
	user_id varchar(50) PRIMARY KEY NOT NULL,
	pwd varchar(68) NOT NULL,
	active tinyint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Inserting data for table 'members'
--
-- NOTE: The passwords are encrypted using BCrypt
-- A generation tool is available at: https://www.bcryptcalculator.com/encode
-- Default passwords here are: test123
--

INSERT INTO members
VALUES
('devon', '{bcrypt}$2a$10$goLOWsblvk3LycfYKnwQYejFliUNQkf9KNG8K0mAer0M2VNL3FxHm', 1),
('kirk', '{bcrypt}$2a$10$BfxYPKRUgVgkKWOyNNJfk.IJvIq7xnWWUmdYot1fcCHl8Jt65QYP2', 1),
('nagibator', '{bcrypt}$2a$10$wQ.T2H4YpGCOT4tX8eR0n.x2SNZycFD.tu33Fxwf.3Mhid.WQ2EJO', 1);

--
-- Table structure for table 'roles'
--

CREATE TABLE roles (
	user_id varchar (50) NOT NULL,
	role varchar (50) NOT NULL,
	UNIQUE KEY authorities5_idx_1 (user_id, role),
	CONSTRAINT authorities5_ibfk_1
	FOREIGN KEY (user_id)
	REFERENCES members (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Inserting data for table 'roles'
--

INSERT INTO roles
VALUES
('devon', 'ROLE_CUSTOMER'),
('kirk', 'ROLE_CUSTOMER'),
('kirk', 'ROLE_MANAGER'),
('nagibator', 'ROLE_CUSTOMER'),
('nagibator', 'ROLE_MANAGER'),
('nagibator', 'ROLE_ADMIN');