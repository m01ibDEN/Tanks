package tile;

import org.example.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class TileManager {
    Game game;
    public Tile[] tile;
    public int[][] mapTileNum;
    public Maze maze;
    public TileManager(Game game) throws IOException {
        this.game = game;
        tile = new Tile[10];
        mapTileNum = new int[game.maxWorldCol][game.maxWorldRow];
        getTileImage();
        maze = new Maze(game.maxWorldCol, game.maxWorldRow);
        loadMap();
    }
    public void loadMap() throws IOException {
        maze.mazeGenerator();
        for(int i = 0; i < game.maxWorldCol; i++) {
            for(int j = 0; j < game.maxWorldRow; j++) {
                mapTileNum[i][j] = maze.getField(i, j);
            }
        }
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
