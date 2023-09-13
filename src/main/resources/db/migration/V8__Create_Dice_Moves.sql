CREATE TABLE dice_moves (
    move INT NOT NULL,
    dice_id UUID NOT NULL,
    FOREIGN KEY (dice_id) REFERENCES dices(id)
);
