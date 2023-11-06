package entity;

import org.example.Game;
import org.example.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player extends Entity{
    public final int screenX;
    public final int screenY;
    public KeyHandler keyH = new KeyHandler();

    public Player(Game game) {
        super(game);
        solidArea = new Rectangle();
        solidArea.x = 6;
        solidArea.y = 6;
        solidArea.width = 32;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        name = "main";
        screenX = game.screenWidth / 2 - (game.tileSize / 2);
        screenY = game.screenHeight / 2;
        shotInterval = 4;
        setDefaultValues();
        getDefaultImage();
    }

    @Override
    public void update() {
        if(live) {
            if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
                if (keyH.upPressed) direct = "up";
                if (keyH.downPressed) direct = "down";
                if (keyH.leftPressed) direct = "left";
                if (keyH.rightPressed) direct = "right";

                collisionOn = false;
                game.collisionChecker.checkTile(this);
                game.collisionChecker.checkEntity(this, game.enemies);
                game.collisionChecker.checkObject(this, true);

                if (!collisionOn) {
                    if (direct.equals("up")) worldY -= speed;
                    if (direct.equals("down")) worldY += speed;
                    if (direct.equals("left")) worldX -= speed;
                    if (direct.equals("right")) worldX += speed;
                }
            }

            if (keyH.spacePressed) {
                keyH.spacePressed = false;
                timeInterval++;
                shotInterval++;
                if (timeInterval == 9) timeInterval = 0;
                if (shotInterval == 5) {
                    shotInterval = 0;
                    amms[timeInterval] = new Amm(game);
                    amms[timeInterval].target = "enemies";
                    amms[timeInterval].direct = direct;

                    amms[timeInterval].worldX = worldX;
                    amms[timeInterval].worldY = worldY;

                    int targetX = worldX - screenX;
                    int targetY = worldY - screenY;

                    amms[timeInterval].targetWorldX = targetX;
                    amms[timeInterval].targetWorldY = targetY;
                }
            }

            for (int i = 0; i < amms.length && amms[i] != null; i++)
                amms[i].update();
            if(game.mapEnemiesCount == 0) game.restart();
            if(health == 0) {
                live = false;
                collisionOn = false;
            }
        }
        else game.restart();
    }
    public void draw(Graphics2D g2) {
        BufferedImage img = switch (direct) {
            case "up" -> up;
            case "down" -> down;
            case "left" -> left;
            case "right" -> right;
            default -> null;
        };
        if(!live) img = null;
        for(int i = 0; i < amms.length && amms[i] != null; i++)
            amms[i].draw(g2);

        g2.drawImage(img, screenX, screenY, game.tileSize, game.tileSize, null);
    }
}
