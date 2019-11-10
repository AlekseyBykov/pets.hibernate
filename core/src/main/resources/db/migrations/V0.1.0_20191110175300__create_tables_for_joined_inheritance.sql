create table if not exists document (
    id bigserial not null,
    state text,
    constraint document_id_pkey primary key (id)
);

comment on table document is 'Document';

comment on column document.id is 'Identifier of the document';
comment on column document.state is 'State of the document';

create table if not exists business_plan (
    id bigserial not null,
    product_description text,
    constraint business_plan_id_pkey primary key (id),
    constraint business_plan_id_fkey foreign key (id) references document (id)
);

comment on table business_plan is 'Business plan';

comment on column business_plan.id is 'Identifier of the business plan';
comment on column business_plan.product_description is 'Description of the product';

create table if not exists employment_agreement (
    id bigserial not null,
    salary double precision,
    constraint employment_agreement_id_pkey primary key (id),
    constraint employment_agreement_id_fkey foreign key (id) references document (id)
);

comment on table employment_agreement is 'Employment agreement';

comment on column employment_agreement.id is 'Identifier of the employment agreement';
comment on column employment_agreement.salary is 'Salary';
