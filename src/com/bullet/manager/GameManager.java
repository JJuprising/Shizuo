package com.bullet.manager;

import com.bullet.element.AttackType;

import java.awt.*;

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


    private static int score=0;
    private static int Hp=100;
    private static AttackType attackType = AttackType.Gun;
    private static int mapID = 1;
    private static int[] ammo = new int[3];

    public static int getMapID() {
        return mapID;
    }

    public void setMapID(int mapID) {
        GameManager.mapID = mapID;
        UpdateLabel();
    }



    public int getScore() {
        return score;
    }
    public void setScore(int delta) {
        score += delta;
        UpdateLabel();
    }

    public int getHp() {
        return Hp;
    }

    public void setHp(int delta) {
        Hp += delta;
        UpdateLabel();
        if(Hp<=0){
            Hp=0;
            StopGame();
        }
    }

    public AttackType getAttackType() {
        return attackType;
    }

    public void setAttackType(AttackType attackType) {
        GameManager.attackType = attackType;
        UpdateLabel();
    }

    public boolean ShootAmmo(){
        boolean isShoot = false;
        if(ammo[attackType.ordinal()]>0){
            ammo[attackType.ordinal()]--;
            UpdateLabel();
            isShoot = true;
        }
        if (ammo[attackType.ordinal()]<=0){
            ReloadAmmo(attackType);
            UpdateLabel();

        }
        return isShoot;
    }
    private boolean isReloading = false;
    public void ReloadAmmo(AttackType type){
        if(!isReloading){

            isReloading = true;
            new Reload(type).start();
        }

    }
    class Reload extends Thread{

        AttackType type;

        public Reload(AttackType type){
            this.type = type;
        }
        @Override
        public void run() {
            long startTime = gameTime;
            while (gameTime<startTime+Settings.ReloadTime){
                try {
                        sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
            }
            System.out.println("reload");
            switch (type){
                case Gun:
                    ammo[type.ordinal()] =Settings.maxGunAmmo;
                    break;
                case Grenade:
                    ammo[type.ordinal()] =Settings.maxGrenadeAmmo;
                    break;
                case Rpg:
                    ammo[type.ordinal()] =Settings.maxRPGAmmo;
                    break;
            }
            isReloading = false;
            UpdateLabel();
        }
    }



    private static boolean isGameRunning = false;

    public Label weaponLabel;
    public Label ammoLabel;
    public Label HpLabel;
    public Label scoreLabel;
    public Label levelLabel;

    public void SetLabel(Label weaponLabel,Label ammoLabel, Label HpLabel, Label scoreLabel, Label levelLabel){
        this.weaponLabel = weaponLabel;
        this.ammoLabel = ammoLabel;
        this.HpLabel = HpLabel;
        this.scoreLabel = scoreLabel;
        this.levelLabel = levelLabel;
    }
    public void UpdateLabel(){

        levelLabel.setText("Level:"+mapID);
        weaponLabel.setText("Weapon:"+attackType);
        ammoLabel.setText("Ammo:"+ammo[attackType.ordinal()]);
        HpLabel.setText("Hp:"+Hp+"/"+Settings.playerMaxHP);
        scoreLabel.setText("Score:"+score);
    }


    public void StartGame(){

        isGameRunning = true;
        ResetGame();


    }
    public void StopGame(){
        isGameRunning = false;
        if(Hp>0){

        }else{

        }

    }
    public void ResetGame(){

        score =0;
        Hp=Settings.playerMaxHP;
        ammo[0] =Settings.maxGunAmmo;
        ammo[1] =Settings.maxGrenadeAmmo;
        ammo[2] =Settings.maxRPGAmmo;
        UpdateLabel();

    }
    public static boolean IsGameRunning(){
        return isGameRunning;
    }

}
