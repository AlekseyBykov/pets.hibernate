create index if not exists /*cuncurrently*/ contractor_name_idx on contractor /*using gin*/(name);
create index if not exists /*cuncurrently*/ product_name_idx on product /*using gin*/(name);
create index if not exists /*cuncurrently*/ shop_name_idx on shop /*using gin*/(name);
