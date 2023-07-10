--who pays for more than one position
select c.first_name, c.customer_id, count(pf.customer_id) as customer_count
from demo_graph.customer c
         join demo_graph.pays_for pf on c.customer_id = pf.customer_id
group by c.customer_id, pf.service_id
having count(pf.customer_id)>1
order by c.first_name;


--MATCH (a:Customer)-[c:paysFor]-(b:Service) RETURN a,count(a) order by a.id