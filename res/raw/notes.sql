CREATE TABLE notes (
id  INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
item_id  INTEGER,
type  TEXT,
time  TEXT,
status  TEXT,
sync  TEXT,
CONSTRAINT notes_items FOREIGN KEY (item_id) REFERENCES items (id)
);