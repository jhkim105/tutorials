insert into pessimistic (id, count) values('id01', 1);
insert into pessimistic (id, count) values('id02', 1);
insert into optimistic (id, name, count, version) values('id01', 'name01', 1, 1);
insert into optimistic (id, name, count, version) values('id02', 'name02', 1, 1);
insert into optimistic_none (id, count, version) values('id01', 1, 1);