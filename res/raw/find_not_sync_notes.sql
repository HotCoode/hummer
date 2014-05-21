select
	id,
	item_id as itemId,
	type,
	time,
	status
from
	notes
where
	sync='0'
order by time asc