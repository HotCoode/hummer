SELECT
	notes.id as id,
	notes.time as time,
	items.content as content
FROM
	notes,items
where
	notes.item_id = items.id
	and
	notes.type = ?
	and
	notes.status = ?
ORDER BY time DESC