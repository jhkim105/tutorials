-- schema
CREATE TABLE IF NOT EXISTS company
(
    id   varchar(50)  not null,
    name varchar(255) null,
    primary key (id)
);

CREATE TABLE IF NOT EXISTS user
(
    id         varchar(50)  not null,
    name       varchar(255) not null,
    company_id varchar(50)  null,
    primary key (id),
    constraint FK2yuxsfrkkrnkn5emoobcnnc3r
        foreign key (company_id) references company (id)
);
