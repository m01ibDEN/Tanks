package org.example;

import entity.Enemy;

public class Setter {
    Game game;
    public Setter(Game game) {
        this.game = game;
    }
    public void setEnemies() {
        game.enemies[0] = new Enemy(game);
        game.enemies[0].worldX = game.tileSize * 2;
        game.enemies[0].worldY = game.tileSize * 2;

        game.enemies[1] = new Enemy(game);
        game.enemies[1].worldX = game.tileSize * 12;
        game.enemies[1].worldY = game.tileSize * 5;

        game.enemies[2] = new Enemy(game);
        game.enemies[2].worldX = game.tileSize;
        game.enemies[2].worldY = game.tileSize * 12;
    }
}
