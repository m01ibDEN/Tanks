package tile;

import org.example.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class TileManager {
    Game game;
    public Tile[] tile;
    public int mapTileNum[][];
    public TileManager(Game game) throws IOException {
        this.game = game;
        tile = new Tile[10];
        mapTileNum = new int[game.maxWorldCol][game.maxWorldRow];
        getTileImage();
        loadMap("C:\\Users\\Danila\\IdeaProjects\\Course4\\src\\main\\resources\\maps\\" + game.mapNum + ".txt");
    }
    public void loadMap(String filePath) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(filePath));
        int col = 0;
        int row = 0;
        String line;
        String numbers[];
        while(col < game.maxWorldCol && row < game.maxWorldRow) {
            line = br.readLine();
            while(col < game.maxWorldCol) {
                numbers = line.split(" ");
                mapTileNum[col][row] = Integer.parseInt(numbers[col]);
                col++;
            }
            if(col == game.maxWorldCol) {
                col = 0;
                row++;
            }
        }
        br.close();
    }
    public void getTileImage() throws IOException {
        tile[0] = new Tile();
        tile[0].img = ImageIO.read(new File("C:\\Users\\Danila\\IdeaProjects\\Course4\\src\\main\\resources\\tiles\\floor.png"));

        tile[1] = new Tile();
        tile[1].img = ImageIO.read(new File("C:\\Users\\Danila\\IdeaProjects\\Course4\\src\\main\\resources\\tiles\\brick.jpg"));
        tile[1].collision = true;
    }
    public void draw(Graphics2D g2) {
        int worldCol = 0;
        int worldRow = 0;

        while(worldCol < game.maxWorldCol && worldRow < game.maxWorldRow) {
            int tileNum = mapTileNum[worldCol][worldRow];
            int worldX = worldCol * game.tileSize;
            int worldY = worldRow * game.tileSize;
            int screenX = worldX - game.player.worldX + game.player.screenX;
            int screenY = worldY - game.player.worldY + game.player.screenY;
            g2.drawImage(tile[tileNum].img, screenX, screenY, game.tileSize, game.tileSize, null);
            worldCol++;
            if(worldCol == game.maxWorldCol) {
                worldCol = 0;
                worldRow++;
            }
        }
    }
}
