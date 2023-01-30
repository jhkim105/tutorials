-- ROLE
INSERT INTO tu_role (id, name) VALUES ('id01', 'ROLE_ADMIN');
INSERT INTO tu_role (id, name) VALUES ('id02', 'ROLE_USER');

-- ROLE_PRIVILEGE
INSERT INTO tu_role_privileges (role_id, privilege) VALUES ('id01', 'READ');
INSERT INTO tu_role_privileges (role_id, privilege) VALUES ('id01', 'WRITE');
INSERT INTO tu_role_privileges (role_id, privilege) VALUES ('id02', 'READ');
