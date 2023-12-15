package entity;

import org.example.Game;
import org.example.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TreeMap;

public class Player extends Entity{
    public final int screenX;
    public final int screenY;
    public BufferedImage healthImg, power, lightning;
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
        getHealthImg();
        getBuffsImg();
        screenX = game.screenWidth / 2 - (game.tileSize / 2);
        screenY = game.screenHeight / 2;
        setDefaultValues();
        getDefaultImage();
    }

    @Override
    public void update() {
        if(live) {
            System.out.println(buffs);
            if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
                if (keyH.upPressed) direct = "up";
                if (keyH.downPressed) direct = "down";
                if (keyH.leftPressed) direct = "left";
                if (keyH.rightPressed) direct = "right";

                checkCollision();

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
                if (timeInterval >= 9) timeInterval = 0;
                shooting(name);
            }

            for (int i = 0; i < amms.length && amms[i] != null; i++)
                amms[i].update();
            if(game.mapEnemiesCount == 0) game.restart();
            if(health <= 0) {
                live = false;
                collisionOn = false;
            }

            if(buffs.containsKey("power")) {
                buffs.put("power", buffs.get("power") - 1);
                if(buffs.get("power") <= 0) {
                    buffs.remove("power");
                    damage = 10;
                }
            }

            if(buffs.containsKey("lightning")) {
                buffs.put("lightning", buffs.get("lightning") - 1);
                if(buffs.get("lightning") <= 0) {
                    buffs.remove("lightning");
                    speed = 1;
                }

            }

        }
        else game.restart();
    }

    public void getHealthImg() {
        try {
            healthImg = ImageIO.read(new File(("C:\\Users\\Danila\\IdeaProjects\\Course4\\src\\main\\resources\\entities\\"+name+"\\heart.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void getBuffsImg() {
        try {
            power = ImageIO.read(new File(("C:\\Users\\Danila\\IdeaProjects\\Course4\\src\\main\\resources\\objects\\power.png")));
            lightning = ImageIO.read(new File(("C:\\Users\\Danila\\IdeaProjects\\Course4\\src\\main\\resources\\objects\\lightning.png")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
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

        int x = game.originalTileSize;
        int y = game.tileSize / 2;

        if(health <= 100 && health >= 80) {
            g2.drawImage(healthImg, x, y, game.tileSize, game.tileSize, null);
            g2.drawImage(healthImg, 4 * x, y, game.tileSize, game.tileSize, null);
            g2.drawImage(healthImg, 7 * x, y, game.tileSize, game.tileSize, null);

        }
        else if(health >= 30) {
            g2.drawImage(healthImg, x, y, game.tileSize, game.tileSize, null);
            g2.drawImage(healthImg, 4 * x, y, game.tileSize, game.tileSize, null);
        }
        else {
            g2.drawImage(healthImg, x, y, game.tileSize, game.tileSize, null);
        }

        x = game.originalTileSize * game.maxWorldCol - game.originalTileSize;

        if(buffs.containsKey("power") && buffs.containsKey("lightning")) {
            g2.drawImage(power, x, y, game.tileSize, game.tileSize, null);
            g2.drawImage(lightning, x + game.tileSize, y, game.tileSize, game.tileSize, null);
        }
        else if(buffs.containsKey("power")) {
            g2.drawImage(power, x, y, game.tileSize, game.tileSize, null);
        }
        else if(buffs.containsKey("lightning")) {
            g2.drawImage(lightning, x, y, game.tileSize, game.tileSize, null);
        }
    }
}
