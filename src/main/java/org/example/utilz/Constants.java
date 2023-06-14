package org.example.utilz;

public class Constants {
    public static class Directions{
        public final static int LEFT = 0;
        public final static int  RIGHT= 1;
        public final static int UP = 2;
        public final static int DOWN = 3;


    }
    public static class PlayerConstants{
        public static final int ATTACK1 = 0;
        public static final int ATTACK2 = 1;
        public static final int DEATH = 2;
        public static final int  FALL= 3;
        public static final int IDLE = 4;
        public static final int JUMP = 5;
        public static final int RUN = 6;
        public static final int TAKE_HIT = 7;

        public static int getFramesAmount(int playerAction){
            switch (playerAction){
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
    }
}
