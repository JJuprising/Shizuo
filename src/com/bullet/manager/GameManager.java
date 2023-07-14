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


    public int PlayerPos =0;
}