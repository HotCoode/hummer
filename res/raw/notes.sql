CREATE TABLE notes (
id  TEXT PRIMARY KEY NOT NULL,
item_id  INTEGER,
time  INTEGER,
status  TEXT DEFAULT 1,
sync  TEXT DEFAULT 0,
CONSTRAINT notes_items FOREIGN KEY (item_id) REFERENCES items (id)
);