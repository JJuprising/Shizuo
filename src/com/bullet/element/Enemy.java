package com.bullet.element;

import com.bullet.manager.*;
import com.bullet.view.Animation;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

//只是创建普通的敌人类
public class Enemy extends ElementObj implements Runnable{

    private ElementManager em=ElementManager.getManager();
    ElementObj Play = em.getElementsByKey(GameElement.PLAY).get(0);
    private String fx;//敌人方向

    private long gameTime=0L;//设置
    Animation animation;

    public Enemy(){
        Thread t = new Thread(this);
        t.start();
    }

    public void Model(){

    }

    @Override
    public void run() {
        while(true){
            move();
            updateImage(this.getGameTime());
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void showElement(Graphics g) {
        g.drawImage(this.getIcon().getImage(), this.getX(), this.getY(), this.getW(), this.getH(), null);
    }

    //创建敌人
    @Override
    public ElementObj createElement(String str){
        System.out.println(str);
        Random random = new Random();
        int LocaY = random.nextInt((Settings.GameY-this.getH()-Settings.playerFootHeight)-(Settings.GameY-Settings.FloorHeight)+1)+(Settings.GameY-Settings.FloorHeight);

        this.setY(LocaY);
        ImageIcon icon;
        if(str.equals("Right")){
            icon = GameLoad.EnemyImgMap.get("Run_Gun_Right_000");
            this.setIcon(icon);
            this.setX(500);
            this.setH(icon.getIconHeight());
            this.setW(icon.getIconWidth());
            animation = new Animation(5);
            animation.SetAnimation(GameLoad.aniMap.get("Enemy_Run_Gun_Right"));
        }
        if (str.equals("Left")){
            icon = GameLoad.EnemyImgMap.get("Run_Gun_Left_000");
            this.setIcon(icon);
            this.setX(0);
            this.setH(icon.getIconHeight());
            this.setW(icon.getIconWidth());
            animation = new Animation(10);
            animation.SetAnimation(GameLoad.aniMap.get("Enemy_Run_Gun_Left"));
        }
        this.setFx(str);
        return this;
    }

    @Override
    protected void move() {
        int playX = Play.getX();
        if(this.fx.equals("Right")){
            if(this.getX()>playX+100){
                this.setX(this.getX()-1);
            }
            if(this.getX()<=playX+100){
                animation.SetAnimation(GameLoad.aniMap.get("Enemy_SA_Gun_Right"));
            }
        }
        if(this.fx.equals("Left")){
            if(this.getX()<playX-100){
                this.setX(this.getX()+1);
            }
            if(this.getX()>=playX-100){
                animation.SetAnimation(GameLoad.aniMap.get("Enemy_SA_Gun_Left"));
            }
        }
    }

    //敌人从地图两边出现，先跑到一定位置，然后站着，随后拿出武器，一定频率射击
    @Override
    protected void updateImage(long gameTime) {
        setGameTime(gameTime);
        this.setIcon(animation.LoadSprite(gameTime));
    }

    @Override
    protected void add(long gameTime) {

    }

    public long getGameTime() {
        return gameTime;
    }
    public void setGameTime(long gameTime) {
        this.gameTime = gameTime;
    }

    public String getFx() {
        return fx;
    }

    public void setFx(String fx) {
        this.fx = fx;
    }

    @Override
    public void die(){
        super.die();
        GameManager.getManager().setScore(200);
    }
}
