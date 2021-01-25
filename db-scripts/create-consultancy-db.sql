create table sigo_requirement(
    id bigserial primary key,
    title varchar(255) not null,
    description varchar(255) not null,
    status varchar(20) not null,
    standard_id bigserial not null,
    process_id bigserial not null
);

insert into sigo_requirement values (default, 'Pendência 1', 'A atividade 1 não atendeu aos requisitos mínimos de segurança', 'OPEN', 1, 1);

insert into sigo_requirement values (default, 'Pendência 2', 'A atividade 2 não atendeu aos requisitos mínimos de qualidade', 'OPEN', 1, 1);
