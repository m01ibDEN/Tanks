package entity;

import org.example.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.TreeMap;

public class Entity {
    public int health;
    public int worldX, worldY;
    public int speed;
    public int damage;
    public TreeMap<String, Integer> buffs = new TreeMap<String, Integer>();
    public int timeInterval = 9;
    public int shotInterval = 0;
    Game game;
    Amm[] amms;
    public BufferedImage left, right, up, down;
    public boolean leftC, rightC, upC, downC;
    public String direct;
    public String name;
    public boolean shot;
    public boolean collisionOn = false;
    public boolean live;
    public Rectangle solidArea;
    public int solidAreaDefaultX, solidAreaDefaultY;
    public Entity(Game game) {
        this.game = game;
    }

    public void getDefaultImage() {
        try {
            up = ImageIO.read(new File(("C:\\Users\\Danila\\IdeaProjects\\Course4\\src\\main\\resources\\entities\\"+name+"\\up.png")));
            down = ImageIO.read(new File(("C:\\Users\\Danila\\IdeaProjects\\Course4\\src\\main\\resources\\entities\\"+name+"\\down.png")));
            left = ImageIO.read(new File(("C:\\Users\\Danila\\IdeaProjects\\Course4\\src\\main\\resources\\entities\\"+name+"\\left.png")));
            right = ImageIO.read(new File(("C:\\Users\\Danila\\IdeaProjects\\Course4\\src\\main\\resources\\entities\\"+name+"\\right.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setDefaultValues() {
        ArrayList<Point> points = game.tileM.maze.freeCells();
        Random random = new Random();
        int rand = random.nextInt(points.size());
        worldX = game.tileSize * points.get(rand).x;
        worldY = game.tileSize * points.get(rand).y;
        points.remove(rand);
        buffs = new TreeMap<>();
        health = 100;
        damage = 10;
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

        for(int i = 0; amms != null && i < amms.length && amms[i] != null; i++)
            amms[i].draw(g2);
    }

    public void update() {}

    public void checkCollision() {
        collisionOn = false;
        if(name.equals("enemy"))  {
            upC = false;
            downC = false;
            leftC = false;
            rightC = false;
            game.collisionChecker.checkPlayer(this);
        }

        game.collisionChecker.checkEntity(this, game.enemies);

        game.collisionChecker.checkTile(this);
        int index = game.collisionChecker.checkObject(this);

        if(index != 999 && name.equals("main")) {
            buffReaction(index);
        }
    }

    public void buffReaction(int i) {
        if(game.obj[i].name.equals("power")) {
            damage = 100;
            buffs.put("power", 500);
        }
        if(game.obj[i].name.equals("lightning")) {
            speed = 2;
            buffs.put("lightning", 500);
        }
        if(game.obj[i].name.equals("heart")) {
            health += (100 - health) / 2;
        }
        game.obj[i].removeObject();
    }

    public void shooting(String name) {
        amms[timeInterval] = new Amm(game);
        amms[timeInterval].damage = damage;
        amms[timeInterval].direct = direct;
        amms[timeInterval].worldX = worldX;
        amms[timeInterval].worldY = worldY;

        int targetX = 0;
        int targetY = 0;
        if(name.equals("enemy")) {
            amms[timeInterval].target = "player";
            targetX = game.player.worldX;
            targetY = game.player.worldY;
        }
        else if(name.equals("main")) {
            amms[timeInterval].target = "enemies";
            targetX = worldX - game.player.screenX;
            targetY = worldY - game.player.screenY;
        }
        amms[timeInterval].targetWorldX = targetX;
        amms[timeInterval].targetWorldY = targetY;
    }
}
