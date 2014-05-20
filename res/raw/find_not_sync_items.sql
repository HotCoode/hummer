select
	id,
	content,
	time,
	status
from
	items
where
	sync='0'
order by time asc