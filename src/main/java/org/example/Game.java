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
    public final int maxScreenCol = 10;
    public final int maxScreenRow = 10;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;
    public final int maxWorldCol = 10;
    public final int maxWorldRow = 10;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;
    final int FPS = 60;
    public int mapEnemiesCount = 1;
    public int mapObjectsCount = 0;
    public TileManager tileM = new TileManager(this);
    public Player player = new Player(this);
    public Entity[] enemies = new Entity[mapEnemiesCount];
    public SuperObject[] obj = new SuperObject[mapObjectsCount];
    public Setter setter = new Setter(this);
    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public long iterations = 0;
    Thread gameThread;

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
            player.setDefaultValues();
            for (int i = 0; i < enemies.length && enemies[i] != null; i++)
                enemies[i].setDefaultValues();
            setup();
        }
        else if(mapEnemiesCount == 0) {
            mapEnemiesCount = 10;
            player.setDefaultValues();
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
        setter.setObjects();
    }

    public void update() {
        player.update();

        for(int i = 0; i < enemies.length && enemies[i] != null; i++) {
            enemies[i].update();
        }

    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        tileM.draw(g2);
        player.draw(g2);
        for(int i = 0; i < enemies.length && enemies[i] != null; i++) {
            enemies[i].draw(g2);
        }
        for(int i = 0; i < obj.length && obj[i] != null; i++) {
            obj[i].draw(g2);
        }
        player.draw(g2);
        g2.dispose();
    }
}
