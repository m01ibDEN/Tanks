package org.example;

import entity.Enemy;
import entity.Entity;
import entity.Player;
import objects.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Game extends JPanel implements Runnable {
    final public  int originalTileSize = 16;
    final int scale = 3;
    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 5;
    public final int maxScreenRow = 5;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;
    public final int maxWorldCol = 15;
    public final int maxWorldRow = 15;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;
    final int FPS = 60;
    public String mapNum = "1";
    public int mapEnemiesCount = 3;
    public Player player = new Player(this);
    public Entity[] enemies = new Entity[10];
    public Setter setter = new Setter(this);
    public SuperObject[] obj = new SuperObject[10];
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public long iterations = 0;
    Thread gameThread;
    public TileManager tileM = new TileManager(this);

    public Game() throws IOException {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.addKeyListener(player.keyH);
        this.setFocusable(true);
    }

    public void startGameTread() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    public void restart() {
        if(!player.live) {
            for (int i = 0; i < enemies.length && enemies[i] != null; i++)
                enemies[i].setDefaultValues();
            setup();
            player.setDefaultValues();
            player.restart();
        }
        else if(mapEnemiesCount == 0) {
            player.restart();
            mapNum = String.valueOf(Integer.parseInt(mapNum) + 1);
            try {
                tileM = new TileManager(this);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            setup();
        }
    }
    @Override
    public void run() {
        int milliard = 1000000000;
        double drawInterval = (double) milliard /FPS;
        double nextDrawTime = System.nanoTime() + drawInterval;
        double remainingtime;
        while(gameThread != null) {
            iterations++;
            if(iterations == Long.MAX_VALUE) iterations = 0;
            update();
            repaint();
            try {
                remainingtime = (nextDrawTime - System.nanoTime()) / 1000000;
                if(remainingtime < 0) {
                    remainingtime = 0;
                }
                Thread.sleep((long)remainingtime);
                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {e.printStackTrace();}
        }
    }

    public void setup() {
        setter.setEnemies();
    }

    public void update() {
        player.update();
        for(int i = 0; i < enemies.length && enemies[i] != null; i++)
            enemies[i].update();

    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        tileM.draw(g2);
        player.draw(g2);
        for(int i = 0; i < enemies.length && enemies[i] != null; i++)
            enemies[i].draw(g2);
        g2.dispose();
    }
}
