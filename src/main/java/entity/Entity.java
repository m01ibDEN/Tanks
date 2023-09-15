package entity;

import org.example.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Entity {
    public int health;
    public int worldX, worldY;
    public int speed;
    public int timeInterval = 0;
    public int shotInterval = 0;
    Game game;
    Amm[] amms;
    public BufferedImage left, right, up, down;
    public String direct;
    public String name;
    public boolean shot;
    public boolean collisionOn = false;
    public boolean live;
    public boolean detectPlayer = false;
    public Rectangle solidArea;
    public int solidAreaDefaultX, solidAreaDefaultY;
    public Entity(Game game) {
        this.game = game;
    }

    public void getDefaultImage() {
        if(name.equals("main")) {
            try {
                up = ImageIO.read(new File(("C:\\Users\\Danila\\IdeaProjects\\Course4\\src\\main\\resources\\entities\\main_person\\up.png")));
                down = ImageIO.read(new File(("C:\\Users\\Danila\\IdeaProjects\\Course4\\src\\main\\resources\\entities\\main_person\\down.png")));
                left = ImageIO.read(new File(("C:\\Users\\Danila\\IdeaProjects\\Course4\\src\\main\\resources\\entities\\main_person\\left.png")));
                right = ImageIO.read(new File(("C:\\Users\\Danila\\IdeaProjects\\Course4\\src\\main\\resources\\entities\\main_person\\right.png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void setDefaultValues() {
        health = 100;
        live = true;
        speed = 1;
        direct = "down";
        amms = new Amm[10];
    }

    public void draw(Graphics2D g2) {
        int screenX = worldX - game.player.worldX + game.player.screenX;
        int screenY = worldY - game.player.worldY + game.player.screenY;

        BufferedImage img = switch (direct) {
            case "up" -> up;
            case "down" -> down;
            case "left" -> left;
            case "right" -> right;
            default -> null;
        };
        if(!live) img = null;
        if(worldX + game.tileSize > game.player.worldX - game.player.screenX &&
                worldX - game.tileSize < game.player.worldX + game.player.screenX &&
                worldY + game.tileSize > game.player.worldY - game.player.screenY &&
                worldY - game.tileSize < game.player.worldY + game.player.screenY) {
            g2.drawImage(img, screenX, screenY, game.tileSize, game.tileSize, null);
        }

        for(int i = 0; amms != null && i < amms.length; i++)
            if(amms[i] != null) {
                amms[i].draw(g2);
            }
    }

    public void update() {}
}
