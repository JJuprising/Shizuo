package com.bullet.manager;

public class GameManager {

    private static GameManager GM=null; //引用
    //	synchronized线程锁->保证本方法执行中只有一个线程
    public static synchronized GameManager getManager() {
        if(GM == null) {//控制判定
            GM=new GameManager();
        }
        return GM;
    }
    public static long gameTime = 0;
    public static int PlayPositionX;
    public static int PlayPositionY;
    public static int MapPositionX;
    public static int HostagePositionX;
    public static int HostagePositionY;
    public static boolean isMoving;//人物是否移动
    public static String fx;//人物朝向
    public static boolean HostageCrash = false;//人物和人质是否碰撞
    public static boolean canSave = true; //true表示能拯救 false表示不能拯救 给人质用
    public static boolean isGive = false;
}
