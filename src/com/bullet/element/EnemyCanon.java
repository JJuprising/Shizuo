package com.bullet.element;

import com.bullet.manager.*;
import com.bullet.view.Animation;

import javax.swing.*;
import java.awt.*;

public class EnemyCanon extends ElementObj implements Runnable{
    private ElementManager em=ElementManager.getManager();
    ElementObj Play = em.getElementsByKey(GameElement.PLAY).get(0);//����λ��

    private long gameTime=0L;//�̲߳���
    private long standbyTime = 0L;//վ����ʱ��
    private long Time = 0; //�ϴη����ӵ�ʱ��
    private long lastshootTime = 0L;//�ϴη����ӵ�ʱ��
    private boolean pkType=false;//����״̬ falseΪδ����Attack״̬��trueδ����Attack״̬
    private int addNum = 0;//�����ӵ���
    private String fx;//�ڿڷ���
    private String EnemyState;//����״̬

    private boolean isNear=false; //�жϽ�Զ�����Ƶ��˶���

    Animation Run;//�ܲ�����
    Animation Stand;//վ������
    Animation Attack;//��������

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
        //����λ���Ǵ�Y[400,512]
        this.setEnemyState("Run");//һ��ʼ���ܲ�״̬
        this.setFx(str);
        this.setY(Enemy.LocaY);
        Enemy.setLocaY(Enemy.LocaY+30);
        //��ʼ�����ֶ���
        this.setRun(new Animation(10));
        this.setStand(new Animation(10));
        this.setAttack(new Animation(10));

        if(str.equals("Right")){
            icon = GameLoad.EnemyImgMap.get("Run_Canon_Right_000");
            this.setIcon(icon);
            this.setX(Settings.GameX);
            this.setH(icon.getIconHeight());
            this.setW(icon.getIconWidth());
            //���ݵ��˷������ö���ͼƬ
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
            //���ݵ��˷������ö���ͼƬ
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
        this.setGameTime(gameTime);//������ùܣ�ֻ�Ǵ��ε��ҵ�run�е�

        if(this.getStandbyTime() > 0){//վ����ʱ�仹û�н���
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
        //�����㷢���������Ҷ�������ͼƬ��ʱ��ͷ����ӵ�
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

            this.setAddNum(this.getAddNum()+1);//ÿ�η����ӵ����ͼ�1

            this.setLastshootTime(gameTime);//��¼���ǹʱ��
            this.setTime(this.getLastshootTime());//�����ǹʱ���¼Ϊ��ʼվ����ʱ��
        }
        if(this.isPkType() && (Attack.LoadSprite(gameTime)==GameLoad.imgMap.get("Attack_Canon_Right_004") || Attack.LoadSprite(gameTime)==GameLoad.imgMap.get("Attack_Canon_Left_004"))){
            this.setStandbyTime(50L);//����һǹ֮��ʱ��������Ҫվ����ʱ��

            if(this.getAddNum()%2==0){//�������ǹ�󣬾�ת��Զ��״̬
                this.setNear(!this.isNear());
            }

            this.setPkType(false);
        }
    }

    @Override
    public void die(){
        ElementObj obj = GameLoad.getObj("enemycanondie");//��δ����
        ElementObj element = obj.createElement(this.fx+","+this.getX()+","+this.getY());
        ElementManager.getManager().addElement(element,GameElement.ENEMYCANONDIE);
        GameManager.getManager().setScore(200);
    }

    //����/��ȡgameTime
    public long getGameTime() {
        return gameTime;
    }
    public void setGameTime(long gameTime) {
        this.gameTime = gameTime;
    }

    //����/��ȡ����
    public String getFx() {
        return fx;
    }
    public void setFx(String fx) {
        this.fx = fx;
    }

    //����/��ȡ����״̬
    public String getEnemyState() {
        return EnemyState;
    }
    public void setEnemyState(String enemyState) {
        EnemyState = enemyState;
    }

    //����/��ȡ�ܲ�����
    public Animation getRun() {
        return Run;
    }
    public void setRun(Animation run) {
        Run = run;
    }

    //����/��ȡվ������
    public Animation getStand() {
        return Stand;
    }
    public void setStand(Animation stand) {
        Stand = stand;
    }

    //����/��ȡ��������
    public Animation getAttack() {
        return Attack;
    }
    public void setAttack(Animation attack) {
        Attack = attack;
    }

    //����/��ȡ����Զ��
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

    //���õ�վ��ʱ��
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