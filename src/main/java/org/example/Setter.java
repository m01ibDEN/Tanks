package org.example;

import entity.Enemy;

public class Setter {
    Game game;
    public Setter(Game game) {
        this.game = game;
    }
    public void setEnemies() {
        game.enemies[0] = new Enemy(game);

        game.enemies[1] = new Enemy(game);

        game.enemies[2] = new Enemy(game);

        game.enemies[3] = new Enemy(game);

        game.enemies[4] = new Enemy(game);

        game.enemies[5] = new Enemy(game);

        game.enemies[6] = new Enemy(game);

        game.enemies[7] = new Enemy(game);

        game.enemies[8] = new Enemy(game);

        game.enemies[9] = new Enemy(game);
    }
}
