package org.example;

import entity.Entity;


public class CollisionChecker {
    Game game;
    CollisionChecker(Game game) {
        this.game = game;
    }
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
                entity.upC = true;
            }
        }
        if(entity.direct.equals("down")) {
            entityBottomRow = (entityBottomWorldY + entity.speed) / game.tileSize;
            tileNum1 = game.tileM.mapTileNum[entityLeftCol][entityBottomRow];
            tileNum2 = game.tileM.mapTileNum[entityRightCol][entityBottomRow];
            if (game.tileM.tile[tileNum1].collision || game.tileM.tile[tileNum2].collision) {
                entity.collisionOn = true;
                entity.downC = true;
            }
        }
        if(entity.direct.equals("left")) {
            entityLeftCol = (entityLeftWorldX - entity.speed) / game.tileSize;
            tileNum1 = game.tileM.mapTileNum[entityLeftCol][entityTopRow];
            tileNum2 = game.tileM.mapTileNum[entityLeftCol][entityBottomRow];
            if (game.tileM.tile[tileNum1].collision || game.tileM.tile[tileNum2].collision) {
                entity.collisionOn = true;
                entity.leftC = true;
            }
        }
        if(entity.direct.equals("right")) {
            entityRightCol = (entityRightWorldX + entity.speed) / game.tileSize;
            tileNum1 = game.tileM.mapTileNum[entityRightCol][entityTopRow];
            tileNum2 = game.tileM.mapTileNum[entityRightCol][entityBottomRow];
            if (game.tileM.tile[tileNum1].collision || game.tileM.tile[tileNum2].collision) {
                entity.collisionOn = true;
                entity.rightC = true;
            }
        }
    }

    public int checkObject(Entity entity) {
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
                        entity.collisionOn = true;
                        index = i;
                    }
                }
                if(entity.direct.equals("down")) {
                    entity.solidArea.y += entity.speed;
                    if(entity.solidArea.intersects(game.obj[i].solidArea)) {
                        entity.collisionOn = true;
                        index = i;
                    }
                }
                if(entity.direct.equals("left")) {
                    entity.solidArea.x -= entity.speed;
                    if(entity.solidArea.intersects(game.obj[i].solidArea)) {
                        entity.collisionOn = true;
                        index = i;
                    }
                }
                if(entity.direct.equals("right")) {
                    entity.solidArea.x += entity.speed;
                    if(entity.solidArea.intersects(game.obj[i].solidArea)) {
                        entity.collisionOn = true;
                        index = i;
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
        for (Entity value : target) {
            if (value != null && entity != value && value.live) {
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;

                value.solidArea.x = value.worldX + value.solidArea.x;
                value.solidArea.y = value.worldY + value.solidArea.y;

                if (entity.direct.equals("up")) {
                    entity.solidArea.y -= entity.speed;
                    if (entity.solidArea.intersects(value.solidArea)) {
                        entity.collisionOn = true;
                    }
                }
                if (entity.direct.equals("down")) {
                    entity.solidArea.y += entity.speed;
                    if (entity.solidArea.intersects(value.solidArea)) {
                        entity.collisionOn = true;
                    }
                }
                if (entity.direct.equals("left")) {
                    entity.solidArea.x -= entity.speed;
                    if (entity.solidArea.intersects(value.solidArea)) {
                        entity.collisionOn = true;
                    }
                }
                if (entity.direct.equals("right")) {
                    entity.solidArea.x += entity.speed;
                    if (entity.solidArea.intersects(value.solidArea)) {
                        entity.collisionOn = true;
                    }
                }

                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;

                value.solidArea.x = value.solidAreaDefaultX;
                value.solidArea.y = value.solidAreaDefaultY;

            }
        }
    }

    public void checkPlayer(Entity entity) {
        entity.solidArea.x = entity.worldX + entity.solidArea.x;
        entity.solidArea.y = entity.worldY + entity.solidArea.y;

        game.player.solidArea.x = game.player.worldX + game.player.solidArea.x;
        game.player.solidArea.y = game.player.worldY + game.player.solidArea.y;

        if(entity.direct.equals("up")) {
            entity.solidArea.y -= entity.speed;
            if(entity.solidArea.intersects(game.player.solidArea)) {
                entity.collisionOn = true;
            }
        }
        if(entity.direct.equals("down")) {
            entity.solidArea.y += entity.speed;
            if(entity.solidArea.intersects(game.player.solidArea)) {
                entity.collisionOn = true;
            }
        }
        if(entity.direct.equals("left")) {
            entity.solidArea.x -= entity.speed;
            if(entity.solidArea.intersects(game.player.solidArea)) {
                entity.collisionOn = true;
            }
        }
        if(entity.direct.equals("right")) {
            entity.solidArea.x += entity.speed;
            if(entity.solidArea.intersects(game.player.solidArea)) {
                entity.collisionOn = true;
            }
        }

        entity.solidArea.x = entity.solidAreaDefaultX;
        entity.solidArea.y = entity.solidAreaDefaultY;

        game.player.solidArea.x = game.player.solidAreaDefaultX;
        game.player.solidArea.y = game.player.solidAreaDefaultY;
    }
}
