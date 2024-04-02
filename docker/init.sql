USE chess;

CREATE TABLE pieces
(
    position    VARCHAR(5) NOT NULL,
    team        VARCHAR(10) NOT NULL,
    type        VARCHAR(10) NOT NULL,
    PRIMARY KEY (position)
);

CREATE TABLE game_infos
(
    game_id INT NOT NULL,
    turn    VARCHAR(5) NOT NULL,
    PRIMARY KEY (game_id)
);