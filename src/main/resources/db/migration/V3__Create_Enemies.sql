CREATE TABLE enemies (
    id UUID NOT NULL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255),
    slug VARCHAR(255) NOT NULL UNIQUE,
    character_id UUID NOT NULL,
    FOREIGN KEY (character_id) REFERENCES characters (id)
);
