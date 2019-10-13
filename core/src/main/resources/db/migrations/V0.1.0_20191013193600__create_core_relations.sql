create table if not exists university (
    university_id bigserial not null,
    university_name text   not null,
    constraint university_id_pkey primary key (university_id)
);

create table if not exists student (
    student_id bigserial not null,
    student_first_name text,
    university_id bigint not null,
    constraint student_id_pkey primary key (student_id),
    constraint university_id_fkey foreign key (university_id) references university (university_id)
);

create table if not exists secondentity (
    id bigint not null,
    name text,
    constraint secondentity_id_pkey primary key (id)
);

create table if not exists firstentity (
    id bigserial not null,
    name text,
    constraint firstentity_id_pkey primary key (id)
);

create table if not exists book (
    book_id bigserial not null,
    book_title text,
    constraint book_id_pkey primary key (book_id)
);

create table if not exists publisher (
    publisher_id bigserial not null,
    publisher_name text,
    book_id bigint not null,
    constraint publisher_id_pkey primary key (publisher_id),
    constraint book_id_fkey foreign key (book_id) references book (book_id)
);

create table if not exists passport (
    passport_id bigserial not null,
    passport_no text,
    constraint passport_id_pkey primary key (passport_id)
);

create table if not exists person (
    person_id bigserial not null,
    person_name text,
    passport_id bigint not null,
    constraint person_id_pkey primary key (person_id),
    constraint passport_id_fkey foreign key (passport_id) references passport (passport_id)
);

create table if not exists department (
    id bigserial not null,
    deptname text,
    constraint department_id_pkey primary key (id)
);

create table if not exists employee (
    id bigserial not null,
    firstname text,
    salary double precision,
    department bigint not null,
    constraint employee_id primary key (id),
    constraint department_id_fkey foreign key (department) references department (id)
);
