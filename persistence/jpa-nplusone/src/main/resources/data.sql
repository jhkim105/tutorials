-- t_user
insert into t_user (id, name) values ( -99, "user 01");
insert into t_user (id, name) values ( -98, "user 02");
insert into t_user (id, name) values ( -97, "user 03");
-- t_order
insert into t_order (id, name, user_id) values ( -99, "order 01", -99);
insert into t_order (id, name, user_id) values ( -98, "order 02", -99);
insert into t_order (id, name, user_id) values ( -97, "order 03", -99);
insert into t_order (id, name, user_id) values ( -89, "order 04", -98);
insert into t_order (id, name, user_id) values ( -88, "order 05", -98);
insert into t_order (id, name, user_id) values ( -87, "order 06", -98);
-- t_coupon
insert into t_coupon (id, name, user_id) values ( -99, "coupon 01", -99);
insert into t_coupon (id, name, user_id) values ( -98, "coupon 02", -99);
insert into t_coupon (id, name, user_id) values ( -97, "coupon 03", -99);