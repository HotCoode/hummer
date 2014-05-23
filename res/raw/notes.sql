CREATE TABLE notes (
id TEXT PRIMARY KEY NOT NULL,
item_id  TEXT,
type  TEXT,
time  TEXT,
status  TEXT DEFAULT 1,
sync  TEXT DEFAULT 0,
CONSTRAINT notes_items FOREIGN KEY (item_id) REFERENCES items (id)
);