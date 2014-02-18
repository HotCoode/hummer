#根據status查找item
select
	id,
	content,
	status
from
	items
where
	status = ?
order by time desc