CREATE SCHEMA if not exists graph;

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

create sequence person_sequence;
create sequence business_sequence;

create table person(
    person_id UUID not null default uuid_generate_v4() primary key,
    first_name VARCHAR(100) not null,
    email VARCHAR(100) not null
);

create table business(
                       business_id UUID not null default uuid_generate_v4() primary key,
                       business_name VARCHAR(100) not null,
                       email VARCHAR(100) not null
);

CREATE OR REPLACE FUNCTION generate_persons(int) RETURNS void AS $$
DECLARE
generation_size int:=$1;
BEGIN
FOR counter IN 1..generation_size
LOOP
insert into person (first_name, email) values ('Ivan'||(select nextval('person_sequence')),(select currval('person_sequence'))||'@mail.com');
END LOOP;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION generate_businesses(int) RETURNS void AS $$
DECLARE
generation_size int:=$1;
BEGIN
FOR counter IN 1..generation_size
LOOP
insert into business (business_name, email) values ('University'||(select nextval('business_sequence')),(select currval('business_sequence'))||'@uni.com');
END LOOP;
END;
$$ LANGUAGE plpgsql;

select generate_persons(10);
select generate_businesses(10);





