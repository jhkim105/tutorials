-- User
insert into tu_user (id, username, password) values(1, 'user', '{noop}111111');
insert into tu_user_roles(user_id, role) values (1, 'USER');
