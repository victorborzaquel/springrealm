CREATE TABLE battle_characters (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    slug VARCHAR(255) NOT NULL,
    life INT NOT NULL,
    pv INT NOT NULL,
    strength INT NOT NULL,
    agility INT NOT NULL,
    defense INT NOT NULL,
    quantity_dices INT NOT NULL,
    quantity_faces INT NOT NULL,
    type VARCHAR(255) NOT NULL
);
