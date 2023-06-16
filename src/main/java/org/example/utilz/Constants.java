package org.example.utilz;

import org.example.main.Game;

public class Constants {
    public static class EnemyConstants{
        public final static int GOBLIN = 100;
        public final static int ATTACK = 0;
        public final static int IDLE = 1;
        public final static int RUN = 2;
        public final static int  TAKE_HIT= 3;
        public final static int DEATH = 4;
        public final static int GOBLIN_WIDTH_DEFAULT = 100;
        public final static int GOBLIN_HEIGHT_DEFAULT = 120;
        public final static int GOBLIN_WIDTH = (int) (GOBLIN_WIDTH_DEFAULT*Game.SCALE);
        public final static int GOBLIN_HEIGHT = (int)(GOBLIN_HEIGHT_DEFAULT*Game.SCALE);
        public final static int DISTANCE_MOVE_ENEMY = (int) (50*Game.SCALE);
        public final static int GetSpriteAmount(int enemy_type,int enemy_state){
            switch (enemy_type){
                case GOBLIN:
                    switch (enemy_state){
                        case ATTACK, RUN:
                            return 8;
                        case IDLE, TAKE_HIT, DEATH:
                            return 4;
                    }
            }
            return -1;
        }

        public static int GetMaxHeath(int enemyType){
            switch (enemyType){
                case GOBLIN:
                    return 10;
                default:
                    return 1;
            }
        }

        public static int GetEnemyDmg(int enemyType){
            switch (enemyType){
                case GOBLIN:
                    return 15;
                default:
                    return 0;
            }
        }

    }
    public static class UI {
        public static class Button {
            public final static int B_WIDTH_DEFAULT = 128;
            public final static int B_HEIGHT_DEFAULT = 56;
            public final static int B_WIDTH = (int) (B_WIDTH_DEFAULT * Game.SCALE);
            public final static int B_HEIGHT = (int) (B_HEIGHT_DEFAULT * Game.SCALE);

        }

        public static class PauseSoundButton{
            public final static int SOUND_WIDTH_DEFAULT = 28;
            public final static int SOUND_HEIGHT_DEFAULT = 28;
            public final static int SOUND_WIDTH = (int) (SOUND_WIDTH_DEFAULT * Game.SCALE);
            public final static int SOUND_HEIGHT = (int) (SOUND_HEIGHT_DEFAULT * Game.SCALE);
        }

        public static class PauseURMButton{
            public final static int URM_WIDTH_DEFAULT = 56;
            public final static int URM_HEIGHT_DEFAULT = 56;
            public final static int URM_WIDTH = (int) (URM_WIDTH_DEFAULT * Game.SCALE);
            public final static int URM_HEIGHT = (int) (URM_HEIGHT_DEFAULT * Game.SCALE);
        }

        public static class VolumeButton{
            public final static int VOLUME_WIDTH_DEFAULT=40;
            public final static int VOLUME_HEIGHT_DEFAULT=48;
            public final static int VOLUME_WIDTH = (int) (VOLUME_WIDTH_DEFAULT * Game.SCALE);
            public final static int VOLUME_HEIGHT = (int) (VOLUME_HEIGHT_DEFAULT * Game.SCALE);
        }
    }

    public static class Directions {
        public final static int LEFT = 0;
        public final static int RIGHT = 1;
        public final static int UP = 2;
        public final static int DOWN = 3;


    }

    public static class PlayerConstants {
        public static final int ATTACK1 = 0;
        public static final int ATTACK2 = 1;
        public static final int DEATH = 2;
        public static final int FALL = 3;
        public static final int IDLE = 4;
        public static final int JUMP = 5;
        public static final int RUN = 6;
        public static final int TAKE_HIT = 7;
        public static final int ATTACK_NORMAL = 100;

        public static int getFramesAmount(int playerAction) {
            switch (playerAction) {
                case ATTACK1, ATTACK2, IDLE, RUN:
                    return 8;
                case DEATH:
                    return 7;
                case FALL, JUMP:
                    return 2;
                case TAKE_HIT:
                    return 3;
                default:
                    return 1;
            }
        }

        public static int GetPlayerDmg(int effectType){
            switch (effectType){
                case ATTACK_NORMAL:
                    return 5;
                default:
                    return 0;
            }
        }
    }
}
