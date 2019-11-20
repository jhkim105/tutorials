-- User
insert into tu_user (id, username, password) values(1, 'user', '{bcrypt}$2a$10$g/oQzPGUYrvdG34ARatuueoGQSU3Xd8rCHNinuRz99Ve4OlMfz4/O');
insert into tu_user_roles(user_id, role) values (1, 'USER');
