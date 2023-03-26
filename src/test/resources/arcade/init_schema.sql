-- case 1
create vertex type Person if not exists;
create property Person.first_name if not exists string;
create property Person.email if not exists string;
create property Person.id if not exists integer;
create vertex type Business if not exists;
create property Business.business_name if not exists string;
create property Business.email if not exists string;
create property Business.id if not exists integer;
create edge type worksIn if not exists;

-- case 2

create vertex type Customer if not exists;
create property Customer.first_name if not exists string;
create property Customer.email if not exists string;
create property Customer.id if not exists integer;
create vertex type Service if not exists;
create property Service.service_name if not exists string;
create property Service.price if not exists string;
create property Service.id if not exists integer;
create edge type paysFor if not exists;