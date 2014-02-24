select
	count(*)
from
	notes
WHERE
	type=?
	and
	time BETWEEN ? and ?
	and
	status=?