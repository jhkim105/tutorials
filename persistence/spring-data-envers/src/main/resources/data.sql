-- dm_user
INSERT INTO dm_user (id, username, name) VALUES ('id01', 'user01', 'User 01');
INSERT INTO dm_user (id, username, name) VALUES ('id02', 'user02', 'User 02');
INSERT INTO dm_user (id, username, name) VALUES ('id03', 'user03', 'User 03');
INSERT INTO dm_user (id, username, name) VALUES ('id04', 'user04', 'User 04');
INSERT INTO dm_user (id, username, name) VALUES ('id05', 'user05', 'User 05');
-- dm_user_address
INSERT INTO dm_user_address (id, city, street, name, user_id) VALUES ('id01', '서울시 송파구', '위례성대로', '직장', 'id01');
INSERT INTO dm_user_address (id, city, street, name, user_id) VALUES ('id02', '서울시 성동구', '무팍로', '집', 'id01');
INSERT INTO dm_user_address (id, city, street, name, user_id) VALUES ('id03', '전북 정읍시', '내능길', '시골', 'id01');
-- de_product
INSERT INTO de_product (id, name, price) VALUES ('id01', '상품1', 1000);
INSERT INTO de_product (id, name, price) VALUES ('id02', '상품2', 1500);
INSERT INTO de_product (id, name, price) VALUES ('id03', '상품3', 300);
INSERT INTO de_product (id, name, price) VALUES ('id04', '상품4', 1200);
INSERT INTO de_product (id, name, price) VALUES ('id05', '상품5', 7100);
-- de_order
INSERT INTO de_order (id, order_date, user_id) VALUES ('id01', '2020-11-26 17:22:07.000000', 'id01');
INSERT INTO de_order (id, order_date, user_id) VALUES ('id02', '2020-11-27 17:22:18.000000', 'id01');
INSERT INTO de_order (id, order_date, user_id) VALUES ('id03', '2020-11-28 17:22:29.000000', 'id01');
-- de_order_products
INSERT INTO de_order_products (id, count, price, order_id, product_id) VALUES ('id01', 2, 2000, 'id01', 'id01');
INSERT INTO de_order_products (id, count, price, order_id, product_id) VALUES ('id02', 1, 300, 'id01', 'id03');