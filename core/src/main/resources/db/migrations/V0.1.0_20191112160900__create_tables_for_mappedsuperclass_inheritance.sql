create table if not exists modern (
    id bigserial not null,
    name text,
    vertical_axis_height double precision,
    constraint modern_id_pkey primary key(id)
);

comment on table modern is 'Modern typeface';

comment on column modern.id is 'Identifier of the modern typeface';
comment on column modern.name is 'Name of typeface';
comment on column modern.vertical_axis_height is 'Height of vertical axis of typeface';

create table if not exists glyphic (
    id bigserial not null,
    name text,
    stroke_weight_contrast double precision,
    constraint glyphic_id_pkey primary key(id)
);

comment on table glyphic is 'Glyphic typeface';

comment on column glyphic.id is 'Identifier of the glyphic typeface';
comment on column glyphic.name is 'Name of typeface';
comment on column glyphic.stroke_weight_contrast is 'Weight of stroke contrast';
