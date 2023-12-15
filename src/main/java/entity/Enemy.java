package entity;

import org.example.Game;

import java.awt.*;
import java.util.Random;

public class Enemy extends Entity{

    public Enemy(Game game) {
        super(game);
        solidArea = new Rectangle();
        solidArea.x = 6;
        solidArea.y = 6;
        solidArea.width = 32;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        shotInterval = 45;
        name = "enemy";
        setDefaultValues();
        getDefaultImage();
    }
    public void playerDetected() {
        if(Math.abs(worldX - game.player.worldX) <= game.tileSize * 5 && Math.abs(worldY - game.player.worldY) <= game.tileSize * 5) {

            int tileX = (worldX / (game.tileSize));
            int tileY = (worldY / (game.tileSize));

            int playerTileX = (game.player.worldX / (game.tileSize));
            int playerTileY = (game.player.worldY / (game.tileSize));

            shot = false; // Устанавливаем shot в false по умолчанию

            if (Math.abs(worldX - game.player.worldX) <= 8) {
                if (worldY >= game.player.worldY && !upC) {
                    direct = "up";
                    for (int i = tileY; i > playerTileY; i--)
                        if (game.tileM.tile[game.tileM.mapTileNum[tileX][i]].collision) {
                            return; // Если есть столкновение, прекращаем выполнение функции
                        }
                }
                if (worldY <= game.player.worldY && !downC) {
                    direct = "down";
                    for (int i = tileY; i < playerTileY; i++)
                        if (game.tileM.tile[game.tileM.mapTileNum[tileX][i]].collision) {
                            return; // Если есть столкновение, прекращаем выполнение функции
                        }
                }
                shot = true; // Если нет столкновения, устанавливаем shot в true
            }

            else if (Math.abs(worldY - game.player.worldY) <= 8) {
                if (worldX >= game.player.worldX && !leftC) {
                    direct = "left";
                    for (int i = tileX; i > playerTileX; i--)
                        if (game.tileM.tile[game.tileM.mapTileNum[i][tileY]].collision) {
                            return; // Если есть столкновение, прекращаем выполнение функции
                        }
                }
                if (worldX <= game.player.worldX && !rightC) {
                    direct = "right";
                    for (int i = tileX; i < playerTileX; i++)
                        if (game.tileM.tile[game.tileM.mapTileNum[i][tileY]].collision) {
                            return; // Если есть столкновение, прекращаем выполнение функции
                        }
                }
                shot = true; // Если нет столкновения, устанавливаем shot в true
            }
        }
    }

    @Override
    public void update() {
        if(live) {
            playerDetected();
            checkCollision();

            Random random = new Random();
            if (game.iterations % 100 == 0 && !shot) {
                int rand = random.nextInt(100);
                if (rand <= 20 && !upC) direct = "up";
                if (rand > 20 && rand <= 40 && !downC) direct = "down";
                if (rand > 40 && rand <= 60 && !leftC) direct = "left";
                if (rand > 60 && rand <= 80 && !rightC) direct = "right";
            }

            if (!collisionOn && !shot) {
                if (direct.equals("up")) worldY -= speed;
                if (direct.equals("down")) worldY += speed;
                if (direct.equals("left")) worldX -= speed;
                if (direct.equals("right")) worldX += speed;
            }
            else if (shot) {
                shot = false;
                timeInterval++;
                shotInterval++;
                if (timeInterval >= 9) timeInterval = 0;
                if (shotInterval >= 45) {
                    shotInterval = 0;
                    shooting(name);
                }
            }

            for (int i = 0; i < amms.length && amms[i] != null; i++) amms[i].update();

            if(health <= 0) {
                live = false;
                collisionOn = false;
                amms = null;
                game.mapEnemiesCount--;
            }
        }
    }
}
