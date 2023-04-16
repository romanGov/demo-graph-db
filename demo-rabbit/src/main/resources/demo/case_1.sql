--who is working on more than one position
select business_name, person_id, count(wi.business_id) as business_count
from graph.business b
         join graph.works_in wi on b.business_id = wi.business_id
group by b.business_id, person_id
having count(wi.business_id)>1
order by business_count desc;

--MATCH (a:Person)-[c:worksIn]-(b:Business) RETURN a,count(a) order by a.id