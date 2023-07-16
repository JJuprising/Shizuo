package com.bullet.element;

import com.bullet.manager.*;
import com.bullet.view.Animation;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Random;

//敌人类
public class Enemy extends ElementObj implements Runnable{
    public static int LocaY=400;
    private ElementManager em=ElementManager.getManager();
    ElementObj Play = em.getElementsByKey(GameElement.PLAY).get(0);//主角位置
    List<ElementObj> Enemy = em.getElementsByKey(GameElement.ENEMY);
    
    private String fx;//敌人方向
    private boolean pkType=false;//攻击状态 false为未处于Attack状态而true未处于Attack状态
    private String EnemyState;//敌人状态

    private long standbyTime = 0L;//站立的时间
    private long gameTime=0L;//为了在本线程中使用而添加的游戏时间
    private long Time = 0; //上次发射子弹时间
    private long lastshootTime = 0L;//上次发射子弹时间

    private boolean isNear=false; //判断近远，控制敌人动作

    private int addNum = 0;

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

        String[] split = str.split(",");
        this.setX(Integer.parseInt(split[0]));
        this.setY(Integer.parseInt(split[1]));
        GameManager.enemyPositionX.add(Integer.parseInt(split[0]));
//        System.out.println(GameManager.enemyPositionX);
        this.setFx(split[2]);
        ImageIcon icon;
        this.setEnemyState("Run");//一开始是跑步状态

        this.setRun(new Animation(10));
        this.setStand(new Animation(10));
        this.setAttack(new Animation(15));

        if(split[2].equals("Right")){
            icon = GameLoad.EnemyImgMap.get("Run_Gun_Right_000");
            this.setIcon(icon);
            this.setH(icon.getIconHeight());
            this.setW(icon.getIconWidth());
            //根据敌人方向设置动画图片
            this.getRun().SetAnimation(GameLoad.aniMap.get("Enemy_Run_Gun_Right"));
            this.getStand().SetAnimation(GameLoad.aniMap.get("Enemy_Stand_Gun_Right"));
            this.getAttack().SetAnimation(GameLoad.aniMap.get("Enemy_Attack_Gun_Right"));
        }
        if (split[2].equals("Left")){
            icon = GameLoad.EnemyImgMap.get("Run_Gun_Left_000");
            this.setIcon(icon);
            this.setH(icon.getIconHeight());
            this.setW(icon.getIconWidth());
            //根据敌人方向设置动画图片
            this.getRun().SetAnimation(GameLoad.aniMap.get("Enemy_Run_Gun_Left"));
            this.getStand().SetAnimation(GameLoad.aniMap.get("Enemy_Stand_Gun_Left"));
            this.getAttack().SetAnimation(GameLoad.aniMap.get("Enemy_Attack_Gun_Left"));
        }

//        Random random = new Random();
//        ImageIcon icon;
//        int LocaY = random.nextInt((Settings.GameY-this.getH()-Settings.playerFootHeight)-(Settings.GameY-Settings.FloorHeight)+1)+(Settings.GameY-Settings.FloorHeight);
//
//        this.setEnemyState("Run");//一开始是跑步状态
//        this.setFx(split[2]);
//        this.setY(LocaY);
//
//        this.setRun(new Animation(10));
//        this.setStand(new Animation(10));
//        this.setAttack(new Animation(15));
//
//        if(str.equals("Right")){
//            icon = GameLoad.EnemyImgMap.get("Run_Gun_Right_000");
//            this.setIcon(icon);
//            this.setX(500);
//            this.setH(icon.getIconHeight());
//            this.setW(icon.getIconWidth());
//
//            this.getRun().SetAnimation(GameLoad.aniMap.get("Enemy_Run_Gun_Right"));
//            this.getStand().SetAnimation(GameLoad.aniMap.get("Enemy_Stand_Gun_Right"));
//            this.getAttack().SetAnimation(GameLoad.aniMap.get("Enemy_Attack_Gun_Right"));
//        }
//        if (str.equals("Left")){
//            icon = GameLoad.EnemyImgMap.get("Run_Gun_Left_000");
//            this.setIcon(icon);
//            this.setX(0);
//            this.setH(icon.getIconHeight());
//            this.setW(icon.getIconWidth());
//
//            this.getRun().SetAnimation(GameLoad.aniMap.get("Enemy_Run_Gun_Left"));
//            this.getStand().SetAnimation(GameLoad.aniMap.get("Enemy_Stand_Gun_Left"));
//            this.getAttack().SetAnimation(GameLoad.aniMap.get("Enemy_Attack_Gun_Left"));
//        }
        return this;
    }

//    @Override
//    protected void move() {
//		if (GameManager.PlayPositionX == 300
//				&& !(Enemy.get(0).getX() - GameManager.MapPositionX == GameManager.enemyPositionX.get(0))
//				&& GameManager.fx == "RIGHT_STAND") {
//			Enemy.get(0).setX(Enemy.get(0).getX() - 2);
//		}
//		if (GameManager.PlayPositionX == 200
//				&& !(Enemy.get(0).getX() - GameManager.MapPositionX == GameManager.enemyPositionX.get(0))
//				&& GameManager.fx == "LEFT_STAND") {
//			Enemy.get(0).setX(Enemy.get(0).getX() + 2);
//		}
//		if (GameManager.PlayPositionX == 300
//				&& !(Enemy.get(1).getX() - GameManager.MapPositionX == GameManager.enemyPositionX.get(1))
//				&& GameManager.fx == "RIGHT_STAND") {
//			Enemy.get(1).setX(Enemy.get(1).getX() - 2);
//		}
//		if (GameManager.PlayPositionX == 200
//				&& !(Enemy.get(1).getX() - GameManager.MapPositionX == GameManager.enemyPositionX.get(1))
//				&& GameManager.fx == "LEFT_STAND") {
//			Enemy.get(1).setX(Enemy.get(1).getX() + 2);
//		}
//		if (GameManager.PlayPositionX == 300
//				&& !(Enemy.get(2).getX() - GameManager.MapPositionX == GameManager.enemyPositionX.get(2))
//				&& GameManager.fx == "RIGHT_STAND") {
//			Enemy.get(2).setX(Enemy.get(2).getX() - 2);
//		}
//		if (GameManager.PlayPositionX == 200
//				&& !(Enemy.get(2).getX() - GameManager.MapPositionX == GameManager.enemyPositionX.get(2))
//				&& GameManager.fx == "LEFT_STAND") {
//			Enemy.get(2).setX(Enemy.get(2).getX() + 2);
//		}
//		if (GameManager.PlayPositionX == 300
//				&& !(Enemy.get(3).getX() - GameManager.MapPositionX == GameManager.enemyPositionX.get(3))
//				&& GameManager.fx == "RIGHT_STAND") {
//			Enemy.get(3).setX(Enemy.get(3).getX() - 2);
//		}
//		if (GameManager.PlayPositionX == 200
//				&& !(Enemy.get(3).getX() - GameManager.MapPositionX == GameManager.enemyPositionX.get(3))
//				&& GameManager.fx == "LEFT_STAND") {
//			Enemy.get(3).setX(Enemy.get(3).getX() + 2);
//		}
//		if (GameManager.PlayPositionX == 300
//				&& !(Enemy.get(4).getX() - GameManager.MapPositionX == GameManager.enemyPositionX.get(4))
//				&& GameManager.fx == "RIGHT_STAND") {
//			Enemy.get(4).setX(Enemy.get(4).getX() - 2);
//		}
//		if (GameManager.PlayPositionX == 200
//				&& !(Enemy.get(4).getX() - GameManager.MapPositionX == GameManager.enemyPositionX.get(4))
//				&& GameManager.fx == "LEFT_STAND") {
//			Enemy.get(4).setX(Enemy.get(4).getX() + 2);
//		}
//        if(!this.isNear()){
//            if(distance>=150){
//                this.setEnemyState("Run");
//                if(this.fx.equals("Right")){
//                    this.setX(this.getX()-1);
//                }
//                if(this.fx.equals("Left")){
//                    this.setX(this.getX()+1);
//                }
//                if(distance==150){
//                    this.setEnemyState("Stand");
//                }
//            } else if (distance<=150 && this.getEnemyState().equals("Stand")) {
//                this.setEnemyState("Attack");
//            }
//        }else{
//            if(distance<=300 && this.getX()>0 && this.getX()<=Settings.GameX){
//                this.setEnemyState("Run");
//                if(this.fx.equals("Right")){
//                    this.setX(this.getX()+1);
//                }
//                if(this.fx.equals("Left")){
//                    this.setX(this.getX()-1);
//                }
//                if(distance==300 || this.getX()==0){
//                    this.setEnemyState("Stand");
//                }
//            } else if ((distance>=300 || this.getX()==0) && this.getEnemyState().equals("Stand")) {
//                this.setEnemyState("Attack");
//            }
//        }
//    }

    protected void move() {
        int playX = Play.getX();
        int distance = Math.abs(this.getX() - playX);
        if (!this.isNear()) {
            if (distance >= 150) {
                this.setEnemyState("Run");
                if (this.fx.equals("Right")) {
                    this.setX(this.getX() - 1);
                }
                if (this.fx.equals("Left")) {
                    this.setX(this.getX() + 1);
                }
                if (distance == 150) {
                    this.setEnemyState("Stand");
                }
            } else if (distance <= 150 && this.getEnemyState().equals("Stand")) {
                this.setEnemyState("Attack");
            }
        } else {
            if (distance <= 300 && this.getX() > 0 && this.getX() <= Settings.GameX) {
                this.setEnemyState("Run");
                if (this.fx.equals("Right")) {
                    this.setX(this.getX() + 1);
                }
                if (this.fx.equals("Left")) {
                    this.setX(this.getX() - 1);
                }
                if (distance == 300 || this.getX() == 0) {
                    this.setEnemyState("Stand");
                }
            } else if ((distance >= 300 || this.getX() == 0) && this.getEnemyState().equals("Stand")) {
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
        if(gameTime-this.getLastshootTime()>100 && this.getEnemyState().equals("Attack") && (Attack.LoadSprite(gameTime)==GameLoad.imgMap.get("Attack_Gun_Right_002") || Attack.LoadSprite(gameTime)==GameLoad.imgMap.get("Attack_Gun_Left_002"))){
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
            SoundManager.getManager().PlaySound("res/music/bullet.wav");
            ElementManager.getManager().addElement(element,GameElement.ENEMYFILE);

            this.setAddNum(this.getAddNum()+1);//每次发射子弹数就加1

            this.setLastshootTime(gameTime);//记录最后开枪时间
            this.setTime(this.getLastshootTime());//把最后开枪时间记录为开始站立的时间
        }
        if(this.isPkType() && (Attack.LoadSprite(gameTime)==GameLoad.imgMap.get("Attack_Gun_Right_004") || Attack.LoadSprite(gameTime)==GameLoad.imgMap.get("Attack_Gun_Left_004"))){
            this.setStandbyTime(100L);//打完一枪之后时间间隔，需要站立的时间

            if(this.getAddNum()%2==0){//当射击两枪后，就转变远近状态
                this.setNear(!this.isNear());
            }

            this.setPkType(false);
        }
    }

    @Override
    public void die(){
        ElementObj obj = GameLoad.getObj("enemydie");
        ElementObj element = obj.createElement(this.fx+","+this.getX()+","+this.getY());
        SoundManager.getManager().PlaySound("res/music/music (11).wav");
        ElementManager.getManager().addElement(element,GameElement.ENEMYDIE);
        GameManager.getManager().setScore(200);
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
    public boolean isNear() {
        return isNear;
    }
    public void setNear(boolean near) {
        isNear = near;
    }
    public int getAddNum() {
        return addNum;
    }
    public void setAddNum(int addNum) {
        this.addNum = addNum;
    }
    public static int getLocaY() {
        return LocaY;
    }
    public static void setLocaY(int locaY) {
        LocaY = locaY;
    }
}