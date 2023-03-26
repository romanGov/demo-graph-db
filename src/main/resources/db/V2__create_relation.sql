create table works_in(
                       person_id UUID not null,
                       business_id UUID not null
);

CREATE OR REPLACE FUNCTION generate_works_in(int) RETURNS void AS $$
DECLARE
generation_size int:=$1;
current_person UUID;
current_business UUID;
BEGIN
FOR counter IN 1..generation_size
LOOP

current_person := (select person_id from person order by first_name limit 1 offset counter-1);

current_business :=(select b.business_id from business b order by b.business_name limit 1 offset counter-1);

insert into works_in (person_id,business_id) values(current_person,current_business);

END LOOP;
END;
$$ LANGUAGE plpgsql;


select generate_works_in(10);

select generate_persons(100);
select generate_businesses(100);
select generate_works_in(100);



