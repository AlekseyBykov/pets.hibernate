create table if not exists report (
    id bigserial not null,
    DTYPE text,
    format text,
    typeface text,
    quoted boolean default true,
    constraint report_id_pkey primary key (id)
);

comment on table report is 'Report';

comment on column report.id is 'Identifier of the report';
comment on column report.DTYPE is 'Name of the actual type';
comment on column report.format is 'Report format';
comment on column report.format is 'Report typeface (for PDF)';
comment on column report.quoted is 'Quoted sign (for CSV)';
