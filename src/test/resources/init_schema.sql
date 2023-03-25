create vertex type Person if not exists;
create property Person.first_name if not exists string;
create property Person.email if not exists string;
create vertex type Business if not exists;
create property Business.business_name if not exists string;
create property Business.email if not exists string;
create edge type worksIn if not exists;