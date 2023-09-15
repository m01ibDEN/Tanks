package objects;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SuperObject {
    public String name;
    public BufferedImage img;
    public int worldX;
    public int worldY;
    public int solidAreaDefaultX;
    public int solidAreaDefaultY;
    public boolean collision = false;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
}
