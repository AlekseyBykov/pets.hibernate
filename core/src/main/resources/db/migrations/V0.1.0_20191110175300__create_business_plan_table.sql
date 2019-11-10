create table if not exists business_plan (
    id bigserial not null,
    product_description text,
    constraint business_plan_id_pkey primary key (id)
);

comment on table business_plan is 'Business plan';

comment on column business_plan.id is 'Identifier of the business plan';
comment on column business_plan.product_description is 'Description of the product';
