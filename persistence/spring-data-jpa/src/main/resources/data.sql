-- dm_group
INSERT INTO dm_group (id, name) VALUES ('id01', 'group 01');
-- dm_user
INSERT INTO dm_user (id, username) VALUES ('id01', 'user01');
-- dm_user_address
INSERT INTO dm_user_address (user_id, address_city, address_street, address_name) VALUES ('id01', '서울시 송파구', '위례성대로', '직장');
INSERT INTO dm_user_address (user_id, address_city, address_street, address_name) VALUES ('id01', '서울시 성동구', '무팍로', '집');
INSERT INTO dm_user_address (user_id, address_city, address_street, address_name) VALUES ('id01', '전북 정읍시', '내능길', '시골');
-- de_product
INSERT INTO de_product (id, name, price) VALUES ('id01', '상품1', 1000);
INSERT INTO de_product (id, name, price) VALUES ('id02', '상품2', 1500);
INSERT INTO de_product (id, name, price) VALUES ('id03', '상품3', 300);
INSERT INTO de_product (id, name, price) VALUES ('id04', '상품4', 1200);
INSERT INTO de_product (id, name, price) VALUES ('id05', '상품5', 7100);
-- de_order
INSERT INTO de_order (id, name, order_date, user_id) VALUES ('id01', '주문1', '2020-11-26 17:22:07.000000', 'id01');
INSERT INTO de_order (id, name, order_date, user_id) VALUES ('id02', '주문2', '2020-11-27 17:22:18.000000', 'id01');
INSERT INTO de_order (id, name, order_date, user_id) VALUES ('id03', '주문3', '2020-11-28 17:22:29.000000', 'id01');
-- de_order_products
INSERT INTO de_order_products (id, count, delivery_date, price, order_id, product_id) VALUES (-99, 2, null, 2000, 'id01', 'id01');
INSERT INTO de_order_products (id, count, delivery_date, price, order_id, product_id) VALUES (-98, 1, null, 300, 'id01', 'id03');
