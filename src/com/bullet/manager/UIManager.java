package com.bullet.manager;

import com.bullet.controller.GameListener;
import com.bullet.controller.GameThread;
import com.bullet.view.*;

public class UIManager {
    private static UIManager UM=null; //引用
    //	synchronized线程锁->保证本方法执行中只有一个线程
    public static synchronized UIManager getManager() {
        if(UM == null) {//控制判定
            UM=new UIManager();


        }
        return UM;
    }

    private GameJFrame gameJFrame;
    GameListener listener;
    GameThread th;

    GameStartPanel startPanel = new GameStartPanel();
    GameSelectPanel selectPanel = new GameSelectPanel();
    GameMainJPanel mainPanel =new GameMainJPanel();
    GameWinPanel winPanel = new GameWinPanel();

    private static UIElement currentPage;


    public void init(){
        gameJFrame= new GameJFrame();
        listener=new GameListener();
        th = new GameThread(0);
        gameJFrame.setKeyListener(listener);
        gameJFrame.setThread(th);
//        System.out.println("init finish");

    }
    public void SetPanel(UIElement page){
        if(page==currentPage) return;

        switch (page){
            case Start:
                gameJFrame.setjPanel(startPanel);
                gameJFrame.start();
                break;
            case Select:
                gameJFrame.setjPanel(selectPanel);
                gameJFrame.start();
                break;
            case Level1:
                gameJFrame.setjPanel(mainPanel);
                th.ChangeMap(11);
                gameJFrame.start();
                break;
            case Level2:
                gameJFrame.setjPanel(mainPanel);
                th.ChangeMap(2);
                gameJFrame.start();

                break;
            case End:
                gameJFrame.setjPanel(winPanel);
                gameJFrame.start();
                break;
        }
        currentPage = page;
    }
}
