select
	id,
	item_id as itemId,
	time,
	status
from
	items
where
	sync='0'
order by time asc