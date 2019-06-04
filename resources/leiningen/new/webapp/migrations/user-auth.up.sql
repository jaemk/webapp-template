create table {{name}}.users (
   id bigint primary key default {{name}}.id_gen(),
   name text unique not null,
   created timestamp with time zone not null default now()
);
--;;

create table {{name}}.auth_tokens (
     id bigint primary key default {{name}}.id_gen(),
     user_id bigint not null unique references {{name}}.users ("id") on delete cascade,
     token uuid unique not null,
     created timestamp with time zone not null default now()
);
