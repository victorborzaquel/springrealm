CREATE TABLE turns (
    id UUID PRIMARY KEY,
    created_at TIMESTAMP NOT NULL,
    number INT NOT NULL,
    is_player_turn BOOLEAN NOT NULL,
    player_pv INT NOT NULL,
    enemy_pv INT NOT NULL,
    attack_power INT NOT NULL,
    defense_power INT NOT NULL,
    damage INT NOT NULL,
    attack_dice_id UUID NOT NULL,
    defense_dice_id UUID NOT NULL,
    damage_dice_id UUID,
    battle_id UUID NOT NULL,
    FOREIGN KEY (attack_dice_id) REFERENCES dices (id),
    FOREIGN KEY (defense_dice_id) REFERENCES dices (id),
    FOREIGN KEY (damage_dice_id) REFERENCES dices (id),
    FOREIGN KEY (battle_id) REFERENCES battles (id)
);
