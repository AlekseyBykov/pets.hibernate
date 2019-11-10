create table if not exists employment_agreement (
    id bigserial not null,
    salary double precision,
    constraint employment_agreement_id_pkey primary key (id)
);

comment on table employment_agreement is 'Employment agreement';

comment on column employment_agreement.id is 'Identifier of the employment agreement';
comment on column employment_agreement.salary is 'Salary';
