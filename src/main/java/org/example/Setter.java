package org.example;

import entity.Enemy;
import objects.SuperObject;

import java.util.Random;

public class Setter {
    Game game;
    public Setter(Game game) {
        this.game = game;
    }
    public void setEnemies() {
        for(int i = 0; i < game.enemies.length; i++) {
            game.enemies[i] = new Enemy(game);
        }
    }

    public void setObjects() {
        String[] names = {"power", "lightning", "heart"};
        Random random = new Random();
        for(int i = 0; i < game.obj.length; i++) {
            game.obj[i] = new SuperObject(game, names[random.nextInt(3)]);
        }
    }
}
