-- schema
create table company
(
    id   varchar(50)  not null,
    name varchar(255) null,
    primary key (id)
);

create table user
(
    id         varchar(50)  not null,
    name       varchar(255) not null,
    company_id varchar(50)  null,
    primary key (id),
    constraint FK2yuxsfrkkrnkn5emoobcnnc3r
        foreign key (company_id) references company (id)
);

-- data
INSERT INTO company (id, name) VALUES ('id01', 'Company 01');
INSERT INTO user (id, name, company_id) VALUES ('id01', 'User 01', 'id01');