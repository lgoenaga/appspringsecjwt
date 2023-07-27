INSERT INTO roles(rol) VALUES('ROLE_USER');
INSERT INTO roles(rol) VALUES('ROLE_ADMIN');
INSERT INTO users(created_at, date_birth, email, enabled, first_name, image, last_name, password, updated_at, username) VALUES(CURDATE(), CURDATE(), 'admin@gmail.com', 1, '', '', '', '$2a$12$dtk.BVqx.08zWNRaKTTOVO9nli8yC67/k2ZYI2gADJ9zS078NeWre', CURDATE(), 'Admin');
INSERT INTO users_roles(user_id, role_id) VALUES(1, 1);
INSERT INTO users_roles(user_id, role_id) VALUES(1, 2);