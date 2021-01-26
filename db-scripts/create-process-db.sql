create table sigo_standard_update(
    id bigserial primary key,
    standard_id bigserial not null,
    title varchar(255) not null,
    publication_date timestamp not null,
    update_date timestamp not null,
    in_use boolean not null
);
