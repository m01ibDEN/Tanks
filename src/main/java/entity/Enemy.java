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
        name = "main";
        setDefaultValues();
        getDefaultImage();
    }
    public void playerDetected() {
        if(Math.abs(worldX - game.player.worldX) <= game.tileSize * 5 && Math.abs(worldY - game.player.worldY) <= game.tileSize * 5) {
            detectPlayer = true;
            int tileX = worldX / game.tileSize;
            int tileY = worldY / game.tileSize;

            if (Math.abs(worldX - game.player.worldX) <= 8) {
                shot = true;
                if (worldY >= game.player.worldY) {
                    direct = "up";
                    if(tileY + 1 <= 15) {
                        for (int i = tileY + 1; i > game.player.worldY / game.tileSize; i--)
                            if (game.tileM.tile[game.tileM.mapTileNum[tileX][i]].collision) {
                                shot = false;
                                detectPlayer = false;
                                break;
                            }
                    }
                }
                if (worldY <= game.player.worldY) {
                    direct = "down";
                    if(tileY - 1 >= 0) {
                        for (int i = tileY - 1; i < game.player.worldY / game.tileSize; i++)
                            if (game.tileM.tile[game.tileM.mapTileNum[tileX][i]].collision) {
                                shot = false;
                                detectPlayer = false;
                                break;
                            }
                    }
                }
            }

            else if (Math.abs(worldY - game.player.worldY) <= 8) {
                shot = true;
                if (worldX >= game.player.worldX) {
                    direct = "left";
                    if(tileX + 1 <= 15){
                        for (int i = tileX + 1; i > game.player.worldX / game.tileSize; i--)
                            if (game.tileM.tile[game.tileM.mapTileNum[i][tileY]].collision) {
                                shot = false;
                                detectPlayer = false;
                                break;
                            }
                    }
                }
                if (worldX <= game.player.worldX) {
                    direct = "right";
                    if(tileX - 1 >= 0){
                        for (int i = tileX - 1; i < game.player.worldX / game.tileSize; i++)
                            if (game.tileM.tile[game.tileM.mapTileNum[i][tileY]].collision) {
                                shot = false;
                                detectPlayer = false;
                                break;
                            }
                    }
                }
            }
            else shot = false;
        }
        else {
            shot = false;
            detectPlayer = false;
        }
    }
    @Override
    public void update() {
        if(live) {
            playerDetected();
            collisionOn = false;
            game.collisionChecker.up = false;
            game.collisionChecker.down = false;
            game.collisionChecker.left = false;
            game.collisionChecker.right = false;
            game.collisionChecker.checkTile(this);
            game.collisionChecker.checkPlayer(this);
            game.collisionChecker.checkEntity(this, game.enemies);
            game.collisionChecker.checkObject(this, false);

            Random random = new Random();
            if (game.iterations % 100 == 0 && !shot) {
                int rand = random.nextInt(100);
                if (rand <= 20 && !game.collisionChecker.up) direct = "up";
                if (rand > 20 && rand <= 40 && !game.collisionChecker.down) direct = "down";
                if (rand > 40 && rand <= 60 && !game.collisionChecker.left) direct = "left";
                if (rand > 60 && rand <= 80 && !game.collisionChecker.right) direct = "right";
            }

            if (!collisionOn && !shot) {
                if (direct.equals("up")) worldY -= speed;
                if (direct.equals("down")) worldY += speed;
                if (direct.equals("left")) worldX -= speed;
                if (direct.equals("right")) worldX += speed;
            }

            for (int i = 0; i < amms.length && amms[i] != null; i++)
                amms[i].update();

            if (shot) {
                shot = false;
                timeInterval++;
                shotInterval++;
                if (timeInterval == 9) timeInterval = 0;
                if (shotInterval == 50) {
                    shotInterval = 0;
                    amms[timeInterval] = new Amm(game);
                    amms[timeInterval].target = "player";
                    amms[timeInterval].direct = direct;

                    amms[timeInterval].worldX = worldX;
                    amms[timeInterval].worldY = worldY;

                    int targetX = game.player.worldX;
                    int targetY = game.player.worldY;

                    amms[timeInterval].targetWorldX = targetX;
                    amms[timeInterval].targetWorldY = targetY;
                }
            }
            if(health == 0) {
                live = false;
                collisionOn = false;
                amms = null;
                game.mapEnemiesCount--;
            }
        }
    }
}
