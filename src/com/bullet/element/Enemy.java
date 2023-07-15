package com.bullet.element;

import com.bullet.manager.*;
import com.bullet.view.Animation;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

//敌人类
public class Enemy extends ElementObj implements Runnable{

    private ElementManager em=ElementManager.getManager();
    ElementObj Play = em.getElementsByKey(GameElement.PLAY).get(0);//主角位置
    private int playX = Play.getX();

    private String fx;//敌人方向
    private boolean pkType=false;//攻击状态 false为未处于Attack状态而true未处于Attack状态
    private String EnemyState;//敌人状态

    private long standbyTime = 0L;//站立的时间
    private long gameTime=0L;//为了在本线程中使用而添加的游戏时间
    private long Time = 0; //上次发射子弹时间
    private long lastshootTime = 0L;//上次发射子弹时间


    Animation Run;//跑步动画
    Animation Stand;//站立动画
    Animation Attack;//攻击动画

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

    //创建敌人
    @Override
    public ElementObj createElement(String str){
        Random random = new Random();
        ImageIcon icon;
        int LocaY = random.nextInt((Settings.GameY-this.getH()-Settings.playerFootHeight)-(Settings.GameY-Settings.FloorHeight)+1)+(Settings.GameY-Settings.FloorHeight);

        this.setEnemyState("Run");//一开始是跑步状态
        this.setFx(str);
        this.setY(LocaY);
        //初始化各种动画
        this.setRun(new Animation(10));
        this.setStand(new Animation(10));
        this.setAttack(new Animation(15));

        if(str.equals("Right")){
            icon = GameLoad.EnemyImgMap.get("Run_Gun_Right_000");
            this.setIcon(icon);
            this.setX(500);
            this.setH(icon.getIconHeight());
            this.setW(icon.getIconWidth());
            //根据敌人方向设置动画图片
            this.getRun().SetAnimation(GameLoad.aniMap.get("Enemy_Run_Gun_Right"));
            this.getStand().SetAnimation(GameLoad.aniMap.get("Enemy_Stand_Gun_Right"));
            this.getAttack().SetAnimation(GameLoad.aniMap.get("Enemy_Attack_Gun_Right"));
        }
        if (str.equals("Left")){
            icon = GameLoad.EnemyImgMap.get("Run_Gun_Left_000");
            this.setIcon(icon);
            this.setX(0);
            this.setH(icon.getIconHeight());
            this.setW(icon.getIconWidth());
            //根据敌人方向设置动画图片
            this.getRun().SetAnimation(GameLoad.aniMap.get("Enemy_Run_Gun_Left"));
            this.getStand().SetAnimation(GameLoad.aniMap.get("Enemy_Stand_Gun_Left"));
            this.getAttack().SetAnimation(GameLoad.aniMap.get("Enemy_Attack_Gun_Left"));
        }
        return this;
    }

    @Override
    protected void move() {
        if(this.fx.equals("Right")){
            if(this.getX()>this.getPlayX()+200){
                this.setX(this.getX()-1);
            }
            if(this.getX()<=this.getPlayX()+200){
                this.setEnemyState("Stand");
            }
            if(this.getX()<=this.getPlayX()+200 && this.getEnemyState().equals("Stand")){
                this.setEnemyState("Attack");
            }
        }
        if(this.fx.equals("Left")){
            if(this.getX()<this.getPlayX()-200){
                this.setX(this.getX()+1);
            }
            if(this.getX()>=this.getPlayX()-200){
                this.setEnemyState("Stand");
            }
            if(this.getX()>=this.getPlayX()-200 && this.getEnemyState().equals("Stand")){
                this.setEnemyState("Attack");
            }
        }
    }

    //根据不同的状态给予不同的动画
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
        if(gameTime-this.getLastshootTime()>200 && this.getEnemyState().equals("Attack") && (Attack.LoadSprite(gameTime)==GameLoad.imgMap.get("Attack_Gun_Right_002") || Attack.LoadSprite(gameTime)==GameLoad.imgMap.get("Attack_Gun_Left_002"))){
            this.setPkType(true);
            ElementObj obj = GameLoad.getObj("enemyfile");

            int fileX = 0,fileY =0;
            fileY = this.getY()+15;
            if(this.fx.equals("Left")){
                fileX = this.getX()+100;
            }
            if(this.fx.equals("Right")){
                fileX = this.getX()-30;
            }
            ElementObj element = obj.createElement(this.fx+","+ fileX +","+fileY);
            ElementManager.getManager().addElement(element,GameElement.ENEMYFILE);
            this.setLastshootTime(gameTime);//记录最后开枪时间
            this.setTime(this.getLastshootTime());//把最后开枪时间记录为开始站立的时间
        }
        if(this.isPkType() && (Attack.LoadSprite(gameTime)==GameLoad.imgMap.get("Attack_Gun_Right_004") || Attack.LoadSprite(gameTime)==GameLoad.imgMap.get("Attack_Gun_Left_004"))){
            this.setStandbyTime(200L);//打完一枪之后时间间隔，需要站立的时间
            this.setPkType(false);
        }
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
    @Override
    public void die(){
        super.die();
        GameManager.getManager().setScore(200);
    }
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
    public int getPlayX() {
        return playX;
    }
    public void setPlayX(int playX) {
        this.playX = playX;
    }
}