package objects;

import org.example.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class SuperObject {
    Game game;
    public String name;
    public BufferedImage img;
    public int worldX;
    public int worldY;
    public int solidAreaDefaultX;
    public int solidAreaDefaultY;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);

    public SuperObject(Game game, String name) {
        this.game = game;
        this.name = name;
        getDefaultImage();
        setDefaultValues();
    }

    public void setDefaultValues() {
        ArrayList<Point> points = game.tileM.maze.freeCells();
        Random random = new Random();
        int rand = random.nextInt(points.size());
        worldX = game.tileSize * points.get(rand).x;
        worldY = game.tileSize * points.get(rand).y;
        points.remove(rand);
    }

    public void removeObject() {
        solidArea.height = 0;
        solidArea.width = 0;
        img = null;
    }

    public void getDefaultImage() {
        try {
            img = ImageIO.read(new File("C:\\Users\\Danila\\IdeaProjects\\Course4\\src\\main\\resources\\objects\\"+name+".png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void draw(Graphics2D g2) {
        int screenX = worldX - game.player.worldX + game.player.screenX;
        int screenY = worldY - game.player.worldY + game.player.screenY;

        if (worldX + game.tileSize > game.player.worldX - game.player.screenX &&
                worldX - game.tileSize < game.player.worldX + game.player.screenX &&
                worldY + game.tileSize > game.player.worldY - game.player.screenY &&
                worldY - game.tileSize < game.player.worldY + game.player.screenY) {
            g2.drawImage(img, screenX, screenY, game.tileSize, game.tileSize, null);

        }
    }
}
