package com.bullet.element;

import com.bullet.manager.*;
import com.bullet.view.Animation;

import javax.swing.*;
import java.awt.*;

public class EnemyCanon extends ElementObj implements Runnable{
    private ElementManager em=ElementManager.getManager();
    ElementObj Play = em.getElementsByKey(GameElement.PLAY).get(0);//主角位置

    private long gameTime=0L;//线程参数
    private long standbyTime = 0L;//站立的时间
    private long Time = 0; //上次发射子弹时间
    private long lastshootTime = 0L;//上次发射子弹时间
    private boolean pkType=false;//攻击状态 false为未处于Attack状态而true未处于Attack状态
    private int addNum = 0;//发射子弹数
    private String fx;//炮口方向
    private String EnemyState;//敌人状态

    private boolean isNear=false; //判断近远，控制敌人动作

    Animation Run;//跑步动画
    Animation Stand;//站立动画
    Animation Attack;//攻击动画

    public EnemyCanon(){
        Thread t = new Thread(this);
        t.start();
    }
    @Override
    public void showElement(Graphics g) {
        g.drawImage(this.getIcon().getImage(), this.getX(), this.getY(), this.getW(), this.getH(), null);
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
    public ElementObj createElement(String str){
        ImageIcon icon;
        //敌人位置是从Y[400,512]
        this.setEnemyState("Run");//一开始是跑步状态
        this.setFx(str);
        this.setY(Enemy.LocaY);
        Enemy.setLocaY(Enemy.LocaY+30);
        //初始化各种动画
        this.setRun(new Animation(10));
        this.setStand(new Animation(10));
        this.setAttack(new Animation(10));

        if(str.equals("Right")){
            icon = GameLoad.EnemyImgMap.get("Run_Canon_Right_000");
            this.setIcon(icon);
            this.setX(Settings.GameX);
            this.setH(icon.getIconHeight());
            this.setW(icon.getIconWidth());
            //根据敌人方向设置动画图片
            this.getRun().SetAnimation(GameLoad.aniMap.get("Enemy_Run_Canon_Right"));
            this.getStand().SetAnimation(GameLoad.aniMap.get("Enemy_Stand_Canon_Right"));
            this.getAttack().SetAnimation(GameLoad.aniMap.get("Enemy_Attack_Canon_Right"));
        }
        if (str.equals("Left")){
            icon = GameLoad.EnemyImgMap.get("Run_Canon_Left_000");
            this.setIcon(icon);
            this.setX(0);
            this.setH(icon.getIconHeight());
            this.setW(icon.getIconWidth());
            //根据敌人方向设置动画图片
            this.getRun().SetAnimation(GameLoad.aniMap.get("Enemy_Run_Canon_Left"));
            this.getStand().SetAnimation(GameLoad.aniMap.get("Enemy_Stand_Canon_Left"));
            this.getAttack().SetAnimation(GameLoad.aniMap.get("Enemy_Attack_Canon_Left"));
        }
        return this;
    }
    @Override
    protected void move() {
        int playX = Play.getX();
        int distance = Math.abs(this.getX()-playX);

        if(!this.isNear()){
            if(distance>=175){
                this.setEnemyState("Run");
                if(this.fx.equals("Right")){
                    this.setX(this.getX()-1);
                }
                if(this.fx.equals("Left")){
                    this.setX(this.getX()+1);
                }
                if(distance==175){
                    this.setEnemyState("Stand");
                }
            } else if (this.getEnemyState().equals("Stand")) {
                this.setEnemyState("Attack");
            }
        }else{
            if(distance<=400 && this.getX()>0 && this.getX()<=Settings.GameX){
                this.setEnemyState("Run");
                if(this.fx.equals("Right")){
                    this.setX(this.getX()+1);
                }
                if(this.fx.equals("Left")){
                    this.setX(this.getX()-1);
                }
                if(distance==400 || this.getX()==0){
                    this.setEnemyState("Stand");
                }
            } else if ((distance>=400 || this.getX()==0) && this.getEnemyState().equals("Stand")) {
                this.setEnemyState("Attack");
            }
        }
    }

    @Override
    protected void updateImage(long gameTime) {
        this.setGameTime(gameTime);//这个不用管，只是传参到我的run中的

        if(this.getStandbyTime() > 0){//站立的时间还没有结束
            this.setEnemyState("Stand");
            this.setStandbyTime(this.getStandbyTime()-(gameTime-this.getTime()));
            this.setTime(gameTime);
        }

        if(this.getEnemyState().equals("Run")){
            this.setIcon(Run.LoadSprite(gameTime));
        } else if (this.getEnemyState().equals("Stand")) {
            this.setIcon(Stand.LoadSprite(gameTime));
        } else if (this.getEnemyState().equals("Attack")) {
            this.setIcon(Attack.LoadSprite(gameTime));
        }
    }

    @Override
    protected void add(long gameTime) {
        //当满足发射间隔，并且动作符合图片的时候就发射子弹
        if(gameTime-this.getLastshootTime()>50 && this.getEnemyState().equals("Attack") && (Attack.LoadSprite(gameTime)==GameLoad.imgMap.get("Attack_Canon_Right_001") || Attack.LoadSprite(gameTime)==GameLoad.imgMap.get("Attack_Canon_Left_001"))){
            this.setPkType(true);
            ElementObj obj = GameLoad.getObj("canonfile");

            int fileX = 0,fileY =0;
            fileY = this.getY()+15;
            if(this.fx.equals("Left")){
                fileX = this.getX()+100;
            }
            if(this.fx.equals("Right")){
                fileX = this.getX()-30;
            }

            ElementObj element = obj.createElement(this.fx+","+ fileX +","+fileY);
            ElementManager.getManager().addElement(element,GameElement.CANONFILE);

            this.setAddNum(this.getAddNum()+1);//每次发射子弹数就加1

            this.setLastshootTime(gameTime);//记录最后开枪时间
            this.setTime(this.getLastshootTime());//把最后开枪时间记录为开始站立的时间
        }
        if(this.isPkType() && (Attack.LoadSprite(gameTime)==GameLoad.imgMap.get("Attack_Canon_Right_004") || Attack.LoadSprite(gameTime)==GameLoad.imgMap.get("Attack_Canon_Left_004"))){
            this.setStandbyTime(50L);//打完一枪之后时间间隔，需要站立的时间

            if(this.getAddNum()%2==0){//当射击两枪后，就转变远近状态
                this.setNear(!this.isNear());
            }

            this.setPkType(false);
        }
    }

    @Override
    public void die(){
        ElementObj obj = GameLoad.getObj("enemycanondie");//仍未创建
        ElementObj element = obj.createElement(this.fx+","+this.getX()+","+this.getY());
        ElementManager.getManager().addElement(element,GameElement.ENEMYCANONDIE);
        GameManager.getManager().setScore(200);
    }

    //设置/获取gameTime
    public long getGameTime() {
        return gameTime;
    }
    public void setGameTime(long gameTime) {
        this.gameTime = gameTime;
    }

    //设置/获取方向
    public String getFx() {
        return fx;
    }
    public void setFx(String fx) {
        this.fx = fx;
    }

    //设置/获取敌人状态
    public String getEnemyState() {
        return EnemyState;
    }
    public void setEnemyState(String enemyState) {
        EnemyState = enemyState;
    }

    //设置/获取跑步动画
    public Animation getRun() {
        return Run;
    }
    public void setRun(Animation run) {
        Run = run;
    }

    //设置/获取站立动画
    public Animation getStand() {
        return Stand;
    }
    public void setStand(Animation stand) {
        Stand = stand;
    }

    //设置/获取攻击动画
    public Animation getAttack() {
        return Attack;
    }
    public void setAttack(Animation attack) {
        Attack = attack;
    }

    //设置/获取敌人远近
    public boolean isNear() {
        return isNear;
    }
    public void setNear(boolean near) {
        isNear = near;
    }

    public long getTime() {
        return Time;
    }
    public void setTime(long time) {
        Time = time;
    }

    //设置的站立时间
    public long getStandbyTime() {
        return standbyTime;
    }
    public void setStandbyTime(long standbyTime) {
        this.standbyTime = standbyTime;
    }

    public long getLastshootTime() {
        return lastshootTime;
    }
    public void setLastshootTime(long lastshootTime) {
        this.lastshootTime = lastshootTime;
    }

    public boolean isPkType() {
        return pkType;
    }
    public void setPkType(boolean pkType) {
        this.pkType = pkType;
    }

    public int getAddNum() {
        return addNum;
    }
    public void setAddNum(int addNum) {
        this.addNum = addNum;
    }
}