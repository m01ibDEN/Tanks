package entity;

import org.example.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Amm extends  Entity{
    BufferedImage ammImg;
    public int targetWorldX;
    public int targetWorldY;
    public String target;
    public boolean flag;
    public Amm(Game game) {
        super(game);
        solidArea = new Rectangle();
        solidArea.x = 6;
        solidArea.y = 6;
        solidArea.width = 32;
        solidArea.height = 32;
        speed = 4;
        live = true;
        flag = false;
        getDefaultImage();
    }
    public void update() {
        if(live) {
            if (target.equals("player")) {
                if(Math.abs(worldX - game.player.worldX) <= 16 && Math.abs(worldY - game.player.worldY) <= 16) {
                    game.player.health -= damage;
                    live = false;
                }
            }

            if (target.equals("enemies")) {
                for (int i = 0; i < game.enemies.length; i++)
                    if (game.enemies[i] != null && game.enemies[i].live)
                        if (Math.abs(worldX - game.enemies[i].worldX) <= 16 && Math.abs(worldY - game.enemies[i].worldY) <= 16) {
                            game.enemies[i].health -= damage;
                            live = false;
                        }

            }
            collisionOn = false;
            game.collisionChecker.checkTile(this);
            if(!collisionOn) {
                if (direct.equals("up")) {
                    worldY -= speed;
                    targetWorldY -= speed;
                }
                if (direct.equals("down")) {
                    worldY += speed;
                    targetWorldY += speed;
                }
                if (direct.equals("left")) {
                    worldX -= speed;
                    targetWorldX -= speed;
                }
                if (direct.equals("right")) {
                    worldX += speed;
                    targetWorldX += speed;
                }
            }
            else live = false;
        }
    }
    @Override
    public void getDefaultImage() {
        try {
            ammImg = ImageIO.read(new File("C:\\Users\\Danila\\IdeaProjects\\Course4\\src\\main\\resources\\entities\\amm.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void draw(Graphics2D g2) {
        int screenX = worldX - game.player.worldX + game.player.screenX;
        int screenY = worldY - game.player.worldY + game.player.screenY;
        if(!live) ammImg = null;
        if(worldX + game.tileSize > game.player.worldX - game.player.screenX &&
                worldX - game.tileSize < game.player.worldX + game.player.screenX &&
                worldY + game.tileSize > game.player.worldY - game.player.screenY &&
                worldY - game.tileSize < game.player.worldY + game.player.screenY) {
            g2.drawImage(ammImg, screenX + game.tileSize / 2, screenY + game.tileSize / 2, game.tileSize / 4, game.tileSize / 4, null);
        }
    }
}
