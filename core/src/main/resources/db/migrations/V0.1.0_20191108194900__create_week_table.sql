create table if not exists week (
    id bigserial not null,
    day_in_string text,
    day_in_ordinal int,
    constraint week_id_pkey primary key (id)
);

comment on table week is 'Week';

comment on column week.id is 'Identifier of the week';
comment on column week.day_in_string is 'String representation of the day';
comment on column week.day_in_ordinal is 'Numerical representation of the day';
