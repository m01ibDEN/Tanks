package org.example;

import entity.Entity;


public class CollisionChecker {
    Game game;
    CollisionChecker(Game game) {
        this.game = game;
    }
    public boolean left, right, up, down;
    public void checkTile(Entity entity) {
        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entityLeftWorldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entityTopWorldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX / game.tileSize;
        int entityRightCol = entityRightWorldX / game.tileSize;
        int entityTopRow = entityTopWorldY / game.tileSize;
        int entityBottomRow = entityBottomWorldY / game.tileSize;

        int tileNum1, tileNum2;

        if(entity.direct.equals("up")) {
            entityTopRow = (entityTopWorldY - entity.speed) / game.tileSize;
            tileNum1 = game.tileM.mapTileNum[entityLeftCol][entityTopRow];
            tileNum2 = game.tileM.mapTileNum[entityRightCol][entityTopRow];
            if (game.tileM.tile[tileNum1].collision || game.tileM.tile[tileNum2].collision) {
                entity.collisionOn = true;
                up = true;

            }
        }
        if(entity.direct.equals("down")) {
            entityBottomRow = (entityBottomWorldY + entity.speed) / game.tileSize;
            tileNum1 = game.tileM.mapTileNum[entityLeftCol][entityBottomRow];
            tileNum2 = game.tileM.mapTileNum[entityRightCol][entityBottomRow];
            if (game.tileM.tile[tileNum1].collision || game.tileM.tile[tileNum2].collision) {
                entity.collisionOn = true;
                down = true;
            }
        }
        if(entity.direct.equals("left")) {
            entityLeftCol = (entityLeftWorldX - entity.speed) / game.tileSize;
            tileNum1 = game.tileM.mapTileNum[entityLeftCol][entityTopRow];
            tileNum2 = game.tileM.mapTileNum[entityLeftCol][entityBottomRow];
            if (game.tileM.tile[tileNum1].collision || game.tileM.tile[tileNum2].collision) {
                entity.collisionOn = true;
                left = true;
            }
        }
        if(entity.direct.equals("right")) {
            entityRightCol = (entityRightWorldX + entity.speed) / game.tileSize;
            tileNum1 = game.tileM.mapTileNum[entityRightCol][entityTopRow];
            tileNum2 = game.tileM.mapTileNum[entityRightCol][entityBottomRow];
            if (game.tileM.tile[tileNum1].collision || game.tileM.tile[tileNum2].collision) {
                entity.collisionOn = true;
                right = true;
            }
        }
    }

    public int checkObject(Entity entity, boolean player) {
        int index = 999;

        for(int i = 0; i < game.obj.length; i++) {
            if(game.obj[i] != null) {
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                game.obj[i].solidArea.x = game.obj[i].worldX + game.obj[i].solidArea.x;
                game.obj[i].solidArea.y = game.obj[i].worldY + game.obj[i].solidArea.y;

                if(entity.direct.equals("up")) {
                    entity.solidArea.y -= entity.speed;
                    if(entity.solidArea.intersects(game.obj[i].solidArea)) {
                        if(game.obj[i].collision) {
                            entity.collisionOn = true;
                        }
                        if(player) {
                            index = i;
                        }
                    }
                }
                if(entity.direct.equals("down")) {
                    entity.solidArea.y += entity.speed;
                    if(entity.solidArea.intersects(game.obj[i].solidArea)) {
                        if(game.obj[i].collision) {
                            entity.collisionOn = true;
                        }
                        if(player) {
                            index = i;
                        }
                    }
                }
                if(entity.direct.equals("left")) {
                    entity.solidArea.x -= entity.speed;
                    if(entity.solidArea.intersects(game.obj[i].solidArea)) {
                        if(game.obj[i].collision) {
                            entity.collisionOn = true;
                        }
                        if(player) {
                            index = i;
                        }
                    }
                }
                if(entity.direct.equals("right")) {
                    entity.solidArea.x += entity.speed;
                    if(entity.solidArea.intersects(game.obj[i].solidArea)) {
                        if(game.obj[i].collision) {
                            entity.collisionOn = true;
                        }
                        if(player) {
                            index = i;
                        }
                    }
                }

                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;

                game.obj[i].solidArea.x = game.obj[i].solidAreaDefaultX;
                game.obj[i].solidArea.y = game.obj[i].solidAreaDefaultY;

            }
        }

        return index;
    }

    public void checkEntity(Entity entity, Entity[] target) {
        for(int i = 0; i < target.length; i++) {
            if(target[i] != null && target[i].live) {
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                target[i].solidArea.x = target[i].worldX + target[i].solidArea.x;
                target[i].solidArea.y = target[i].worldY + target[i].solidArea.y;

                if(entity.direct.equals("up")) {
                    entity.solidArea.y -= entity.speed;
                    if(entity.solidArea.intersects(target[i].solidArea)) {
                        entity.collisionOn = true;
                    }
                }
                if(entity.direct.equals("down")) {
                    entity.solidArea.y += entity.speed;
                    if(entity.solidArea.intersects(target[i].solidArea)) {
                        entity.collisionOn = true;
                    }
                }
                if(entity.direct.equals("left")) {
                    entity.solidArea.x -= entity.speed;
                    if(entity.solidArea.intersects(target[i].solidArea)) {
                        entity.collisionOn = true;
                    }
                }
                if(entity.direct.equals("right")) {
                    entity.solidArea.x += entity.speed;
                    if(entity.solidArea.intersects(target[i].solidArea)) {
                        entity.collisionOn = true;
                    }
                }

                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;

                target[i].solidArea.x = target[i].solidAreaDefaultX;
                target[i].solidArea.y = target[i].solidAreaDefaultY;

            }
        }
    }

}
