package tile;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Maze {
    private final int[][] map;
    private final int col;
    private final int row;

    public Maze(int col, int row) {
        map = new int[col][row];
        this.col = col;
        this.row = row;
        for (int i = 0; i < col; i++)
            for (int j = 0; j < row; j++) map[i][j] = 1;
    }

    public void setField(int x, int y, int value) {
        if (x < 0 || x >= this.col || y < 0 || y >= this.row) {
            return;
        }
        map[y][x] = value;
    }

    public int getField(int x, int y) {
        if (x < 0 || x >= this.col || y < 0 || y >= this.row) {
            return -1;
        }
        return map[y][x];
    }

    public boolean isMaze () {
        for (int x = 0; x <= this.col; x++) {
            for (int y = 0; y <= this.row; y++) {
                if ((x % 2 == 0) && (y % 2 == 0) && this.getField(x, y) == 1) {
                    return false;
                }
            }
        }

        for (int i = 0; i < this.col; i++) {
            for (int j = 0; j < this.row; j++) {
                if (i == 0 || j == 0 || i == this.col - 1 || j == this.row - 1) {
                    this.setField(i, j, 1);
                }
                if (((i == 1 || i == this.col - 2) && j > 0 && j < this.row - 1) || ((j == 1 || j == this.row - 2) && i > 0 && i < this.col - 1)) {
                    this.setField(i, j, 0);
                }
            }
        }
        return true;
    }

    public void moveTractor (Point tractor) {
        if (!this.isMaze()) {
            Set<String> directs = new HashSet<>();
            if (tractor.x > 0) {
                directs.add("left");
            }

            if (tractor.x < this.col - 2) {
                directs.add("right");
            }

            if (tractor.y > 0) {
                directs.add("up");
            }

            if (tractor.y < this.row - 2) {
                directs.add("down");
            }

            Random random = new Random();

            String direct = directs.toArray()[random.nextInt(directs.size())].toString();

            switch (direct) {
                case "left":
                    if (getField(tractor.x - 2, tractor.y) == 1) {
                        setField(tractor.x - 1, tractor.y, 0);
                        setField(tractor.x - 2, tractor.y, 0);
                    }
                    tractor.x -= 2;
                    break;
                case "right":
                    if (getField(tractor.x + 2, tractor.y) == 1) {
                        setField(tractor.x + 1, tractor.y, 0);
                        setField(tractor.x + 2, tractor.y, 0);
                    }
                    tractor.x += 2;
                    break;
                case "up":
                    if (getField(tractor.x, tractor.y - 2) == 1) {
                        setField(tractor.x, tractor.y - 1, 0);
                        setField(tractor.x, tractor.y - 2, 0);
                    }
                    tractor.y -= 2;
                    break;
                case "down":
                    if (getField(tractor.x, tractor.y + 2) == 1) {
                        setField(tractor.x, tractor.y + 1, 0);
                        setField(tractor.x, tractor.y + 2, 0);
                    }
                    tractor.y += 2;
                    break;
            }
            moveTractor(tractor);
        }
    }

    public ArrayList<Point> freeCells() {
        ArrayList<Point> cells = new ArrayList<Point>();
        for (int i = 0; i <= this.col; i++) {
            for (int j = 0; j <= this.row; j++) {
                if(this.getField(i, j) == 0) cells.add(new Point(i, j));
            }
        }
        return cells;
    }

    public void mazeGenerator() {
        Random random = new Random();
        int startX = random.nextInt(this.col);
        while(startX % 2 != 0) {
            startX = random.nextInt(this.col);
        }

        int startY = random.nextInt(this.row);
        while(startY % 2 != 0) {
            startY = random.nextInt(this.row);
        }

        Point tractor = new Point(startX, startY);
        this.setField(startX, startY, 0);
        this.moveTractor(tractor);
    }
}
