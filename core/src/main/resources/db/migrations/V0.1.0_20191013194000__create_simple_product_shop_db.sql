create table if not exists contractor (
	id bigserial not null,
	name text not null,
	constraint contractor_id_pkey primary key (id)
);

create table if not exists product (
	id bigserial not null,
	name text not null,
	expiration_date date,
	contractor_id bigint not null,
	constraint product_id_pkey primary key (id),
	constraint contractor_id_fkey foreign key (contractor_id) references contractor (id)
);

create table if not exists shop (
	id bigserial not null,
	name text not null,
	address text,
	constraint shop_id_pkey primary key (id)
);

create table if not exists shop_product (
	shop_id bigint not null,
	product_id bigint not null,
	constraint product_id_fkey foreign key (product_id) references product (id),
	constraint shop_id_fkey foreign key (shop_id) references shop (id)
);
