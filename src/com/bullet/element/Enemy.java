package com.bullet.element;

import com.bullet.manager.ElementManager;
import com.bullet.manager.GameElement;
import com.bullet.manager.GameLoad;
import com.bullet.manager.Settings;
import com.bullet.view.Animation;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

//ֻ�Ǵ�����ͨ�ĵ�����
public class Enemy extends ElementObj implements Runnable{

    private ElementManager em=ElementManager.getManager();
    ElementObj Play = em.getElementsByKey(GameElement.PLAY).get(0);//�õ�����

    private String fx;//���˷���
    private boolean pkType=false;//��ǹ״̬
    private String EnemyState;//����״̬
    private long gameTime=0L;//����
    private long Time = 0;
    Animation Run;//���ܵĶ���
    Animation Stand;//վ���Ķ���
    Animation Attack;//��������

    public Enemy(){
        Thread t = new Thread(this);
        t.start();
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

    //��������
    @Override
    public ElementObj createElement(String str){
        this.setTime(0);
        Random random = new Random();
        ImageIcon icon;
        int LocaY = random.nextInt((Settings.GameY-this.getH()-Settings.playerFootHeight)-(Settings.GameY-Settings.FloorHeight)+1)+(Settings.GameY-Settings.FloorHeight);

        this.setEnemyState("Run");//һ��ʼ����������
        this.setFx(str);
        this.setY(LocaY);
        this.setRun(new Animation(5));

        if(str.equals("Right")){
            icon = GameLoad.EnemyImgMap.get("Run_Gun_Right_000");
            this.setIcon(icon);
            this.setX(500);
            this.setH(icon.getIconHeight());
            this.setW(icon.getIconWidth());
            this.getRun().SetAnimation(GameLoad.aniMap.get("Enemy_Run_Gun_Right"));
            Stand = new Animation(100);
            Stand.SetAnimation(GameLoad.aniMap.get("Enemy_Stand_Gun_Right"));
            Attack = new Animation(10);
            Attack.SetAnimation(GameLoad.aniMap.get("Enemy_Attack_Gun_Right"));
        }
        if (str.equals("Left")){
            icon = GameLoad.EnemyImgMap.get("Run_Gun_Left_000");
            this.setIcon(icon);
            this.setX(0);
            this.setH(icon.getIconHeight());
            this.setW(icon.getIconWidth());
            this.getRun().SetAnimation(GameLoad.aniMap.get("Enemy_Run_Gun_Left"));
            Stand = new Animation(100);
            Stand.SetAnimation(GameLoad.aniMap.get("Enemy_Stand_Gun_Left"));
            Attack = new Animation(10);
            Attack.SetAnimation(GameLoad.aniMap.get("Enemy_Attack_Gun_Left"));
        }
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
                this.setEnemyState("Stand");
            }
            if(this.getX()<=playX+100 && this.getEnemyState().equals("Stand")){
                this.setEnemyState("Attack");
            }
        }
        if(this.fx.equals("Left")){
            if(this.getX()<playX-100){
                this.setX(this.getX()+1);
            }
            if(this.getX()>=playX-100){
                this.setEnemyState("Stand");
            }
            if(this.getX()>=playX-100 && this.getEnemyState().equals("Stand")){
                this.setEnemyState("Attack");
            }
        }
    }

    //���˴ӵ�ͼ���߳��֣����ܵ�һ��λ�ã�Ȼ��վ�ţ�����ó�������һ��Ƶ�����
    @Override
    protected void updateImage(long gameTime) {
        this.setGameTime(gameTime);
        if(this.getEnemyState().equals("Run")){
            this.setIcon(Run.LoadSprite(gameTime));
        } else if (this.getEnemyState().equals("Stand")) {
            this.setIcon(Stand.LoadSprite(gameTime));
            System.out.println(this.getIcon());
        } else if (this.getEnemyState().equals("Attack")) {
            this.setIcon(Attack.LoadSprite(gameTime));
        }
    }

    @Override
    protected void add(long gameTime) {
//        if(gameTime-this.getTime()>100 && (Attack.LoadSprite(gameTime)==GameLoad.imgMap.get("Attack_Gun_Right_002") || Attack.LoadSprite(gameTime)==GameLoad.imgMap.get("Attack_Gun_Left_002"))){
            if(gameTime-this.getTime()>100){
                ElementObj obj = GameLoad.getObj("enemyfile");
                ElementObj element = obj.createElement(this.fx+","+this.getX()+","+this.getY());
                ElementManager.getManager().addElement(element,GameElement.ENEMYFILE);
                this.setTime(gameTime);
            }
//        }
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
    public long getTime() {
        return Time;
    }
    public void setTime(long time) {
        Time = time;
    }
    public String getEnemyState() {
        return EnemyState;
    }
    public void setEnemyState(String enemyState) {
        EnemyState = enemyState;
    }
    public Animation getRun() {
        return Run;
    }
    public void setRun(Animation run) {
        Run = run;
    }
    public Animation getStand() {
        return Stand;
    }
    public void setStand(Animation stand) {
        Stand = stand;
    }
    public Animation getAttack() {
        return Attack;
    }
    public void setAttack(Animation attack) {
        Attack = attack;
    }
}