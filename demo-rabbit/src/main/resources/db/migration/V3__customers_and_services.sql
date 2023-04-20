create sequence customer_sequence;
create sequence service_sequence;

create table customer(
                       customer_id UUID not null default uuid_generate_v4() primary key,
                       first_name VARCHAR(100) not null,
                       email VARCHAR(100) not null
);

create table service(
                         service_id UUID not null default uuid_generate_v4() primary key,
                         service_name VARCHAR(100) not null,
                         price bigint  not null
);

CREATE OR REPLACE FUNCTION generate_customers(int) RETURNS void AS $$
DECLARE
generation_size int:=$1;
BEGIN
FOR counter IN 1..generation_size
LOOP
insert into customer (first_name, email) values ('John'||(select nextval('customer_sequence')),(select currval('customer_sequence'))||'@customer.com');
END LOOP;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION generate_services(int) RETURNS void AS $$
DECLARE
generation_size int:=$1;
BEGIN
FOR counter IN 1..generation_size
LOOP
insert into service (service_name, price) values ('Service'||(select nextval('service_sequence')),(select currval('service_sequence'))+counter);
END LOOP;
END;
$$ LANGUAGE plpgsql;

select generate_customers(10);
select generate_services(10);

create table pays_for(
                         customer_id UUID not null,
                         service_id UUID not null
);

CREATE OR REPLACE FUNCTION generate_pays_for(int) RETURNS void AS $$
DECLARE
generation_size int:=$1;
current_customer UUID;
current_service UUID;
BEGIN
FOR counter IN 1..generation_size
LOOP

current_customer := (select customer_id from customer order by first_name limit 1 offset counter-1);

current_service :=(select service_id from service order by service_name limit 1 offset counter-1);

insert into pays_for (customer_id,service_id) values(current_customer,current_service);

END LOOP;
END;
$$ LANGUAGE plpgsql;


select generate_pays_for(10);

select generate_customers(1000);
select generate_services(1000);
select generate_pays_for(1000);



