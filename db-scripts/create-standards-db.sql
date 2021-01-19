create table sigo_standards(
    id bigserial primary key,
    title varchar(255) not null,
    keywords varchar(255) not null,
    publication_date timestamp not null,
    in_use boolean not null,
    url varchar(50) not null
);

insert into sigo_standards values (default, 'norma 1', 'tecido, cor', TIMESTAMP '2020-06-05 10:00:00', true, 'https://example.com');

insert into sigo_standards values (default, 'norma 2', 'tecido, material', TIMESTAMP '2020-02-05 10:00:00', true, 'https://example.com');

insert into sigo_standards values (default, 'norma 3', 'material', TIMESTAMP '2020-08-05 10:00:00', false, 'https://example.com');

insert into sigo_standards values (default, 'norma 2', 'material', TIMESTAMP '2020-08-05 10:00:00', false, 'https://example.com');



