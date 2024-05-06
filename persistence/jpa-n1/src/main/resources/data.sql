-- t_group
insert into t_group (id, name) values ( 'id01', 'group 01');
insert into t_group (id, name) values ( 'id02', 'group 02');

-- t_setting
-- insert into t_setting (id, name) values ( 'id01', 'setting 01');
insert into t_setting (id, name, group_id) values ( 'id02', 'setting 01', 'id01');

-- t_user
insert into t_user (id, name) values ( 'id01', 'user 01');
insert into t_user (id, name) values ( 'id02', 'user 02');
insert into t_user (id, name) values ( 'id03', 'user 03');

-- t_group_users
insert into t_group_users (group_id, users_id) values ( 'id01', 'id01');
insert into t_group_users (group_id, users_id) values ( 'id01', 'id02');

-- t_coupon
insert into t_coupon (id, name, user_id) values ( -99, 'coupon 01', 'id01');
insert into t_coupon (id, name, user_id) values ( -98, 'coupon 02', 'id01');
insert into t_coupon (id, name, user_id) values ( -97, 'coupon 03', 'id01');

-- t_order
insert into t_order (id, name, user_id) values ( -99, 'order 01', 'id01');
insert into t_order (id, name, user_id) values ( -98, 'order 02', 'id01');
insert into t_order (id, name, user_id) values ( -97, 'order 03', 'id02');
insert into t_order (id, name, user_id) values ( -89, 'order 04', 'id02');
