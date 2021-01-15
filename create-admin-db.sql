create table sigo_user(
    id bigserial primary key,
    password varchar(255) not null,
    username varchar(45) unique not null,
    role varchar(15) not null
);

