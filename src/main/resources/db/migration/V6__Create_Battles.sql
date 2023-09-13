CREATE TABLE battles (
    id UUID PRIMARY KEY,
    created_at TIMESTAMP NOT NULL,
    ended_at TIMESTAMP,
    quantity_turns INT NOT NULL DEFAULT 0,
    is_player_initiative BOOLEAN,
    status VARCHAR(255) NOT NULL DEFAULT 'INITIAL_ROLL',
    player_id UUID NOT NULL,
    enemy_id UUID NOT NULL,
    player_battle_character_id UUID NOT NULL,
    enemy_battle_character_id UUID NOT NULL,
    FOREIGN KEY (player_id) REFERENCES players (id),
    FOREIGN KEY (enemy_id) REFERENCES enemies (id),
    FOREIGN KEY (player_battle_character_id) REFERENCES battle_characters (id),
    FOREIGN KEY (enemy_battle_character_id) REFERENCES battle_characters (id)
);
