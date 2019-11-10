create table if not exists document (
    id bigserial not null,
    state text,
    constraint document_id_pkey primary key (id)
);

comment on table document is 'Document';

comment on column document.id is 'Identifier of the document';
comment on column document.state is 'State of the document';
