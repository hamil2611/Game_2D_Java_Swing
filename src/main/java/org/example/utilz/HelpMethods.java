package org.example.utilz;

import org.example.main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class HelpMethods {
    public static boolean CanMoveHere(float x, float y, float width, float height, int[][] levelMap) {
        if (!IsSolid(x, y, levelMap))
            if (!IsSolid(x + width, y + height, levelMap))
                if (!IsSolid(x + width, y, levelMap))
                    if (!IsSolid(x, y + height, levelMap))
                        return true;
        return false;
    }

    private static boolean IsSolid(float x, float y, int[][] levelMap) {
        int maxWidth = levelMap[0].length * Game.TILES_SIZE;
        if (x < 0 || x >= maxWidth) {
            return true;
        }

        if (y < 0 || y >= Game.GAME_HEIGHT)
            return true;
        float xIndex = x / Game.TILES_SIZE;
        float yIndex = y / Game.TILES_SIZE;
        return IsTileSolid((int) xIndex, (int) yIndex, levelMap);
    }

    public static boolean IsTileSolid(int xTile, int yTile, int[][] levelMap) {
        int value = levelMap[yTile][xTile];
        if (value >= 48 || value < 0 || value == 11) {
            return false;
        }
        return true;
    }

    public static float GetEntityXPosNextToWall(Rectangle2D.Float hitbox, float xSpeed) {
        int currentTile = (int) (hitbox.x / Game.TILES_SIZE);
        if (xSpeed > 0) {
            int tileXPos = currentTile * Game.TILES_SIZE;
            int xOffset = (int) (Game.TILES_SIZE - hitbox.width);
            return tileXPos + xOffset - 1;
        } else {
            return currentTile * Game.TILES_SIZE;
        }
    }

    public static float GetEntityYPosUnderRoofOrAboveFloor(Rectangle2D.Float hitbox, float airSpeed) {
        int currentTile = (int) (hitbox.y / Game.TILES_SIZE);
        if (airSpeed > 0) {
            //Falling
            int tileYPos = currentTile * Game.TILES_SIZE;
            int yOffset = (int) (Game.TILES_SIZE - hitbox.height);
            return tileYPos + yOffset - 1;
        } else {
            return currentTile * Game.TILES_SIZE;
        }
    }

    public static boolean IsEntityOnFloor(Rectangle2D.Float hitbox, int[][] levelMap) {
        if (!IsSolid(hitbox.x + 20 * Game.SCALE, hitbox.y + hitbox.height + 10, levelMap))
            if (!IsSolid(hitbox.x + hitbox.width - 20 * Game.SCALE, hitbox.y + hitbox.height + 10, levelMap))
                return false;
        return true;
    }

    public static boolean IsFloor(Rectangle2D.Float hitbox, float xSpeed, int[][] levelMap) {
        return IsSolid(hitbox.x + xSpeed, hitbox.y + hitbox.height, levelMap);
    }

    public static boolean IsEnemyOnFloor(Rectangle2D.Float hitbox, float xSpeed, int[][] levelMap) {
        if (!IsSolid(hitbox.x + 20 * Game.SCALE + xSpeed - 20, hitbox.y + hitbox.height + 10, levelMap))
            if (!IsSolid(hitbox.x + hitbox.width - 20 * Game.SCALE + xSpeed, hitbox.y + hitbox.height + 10, levelMap))
                return false;
        return true;
    }

    public static boolean IsAllTileWalkable(int xStart, int xEnd, int y, int[][] levelMap) {
        for (int i = 0; i < xEnd - xStart; i++) {
            if (IsTileSolid(xStart + i, y, levelMap)) ;
            return true;
        }
        return false;
    }

    public static boolean isSightClear(int[][] levelMap, Rectangle2D.Float firstHitbox, Rectangle2D.Float secondHitbox, int tileY) {
        int firstXTile = (int) (firstHitbox.x / Game.TILES_SIZE);
        int secondXTile = (int) (secondHitbox.x / Game.TILES_SIZE);
        if (firstXTile > secondXTile)
            return IsAllTileWalkable(secondXTile, firstXTile, tileY, levelMap);
        else
            return IsAllTileWalkable(firstXTile, secondXTile, tileY, levelMap);
    }

}
