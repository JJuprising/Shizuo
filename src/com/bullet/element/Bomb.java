package com.bullet.element;

import com.bullet.manager.*;
import com.bullet.view.Animation;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Bomb extends ElementObj{
    private int attack;//攻击力

    public Bomb(){}
    private int moveNum=3;//移动速度值
    private long initTime=0;
    private int lastTime=100;
    private boolean markTime=false;//时间初始化标记
    private boolean overKey=false;//炮弹消失，在该位置触发动画
    Animation animation;

    //炮弹旋转动画
    ArrayList<String> bombAnimationList = new ArrayList<String>(){
        {
            add("PLANE_BOMB0");
            add("PLANE_BOMB1");
            add("PLANE_BOMB2");
            add("PLANE_BOMB3");
        }
    };
    //爆炸动画
    ArrayList<String> overAnimationList = new ArrayList<String>(){
        {
            add("BOMB_OVER0");
            add("BOMB_OVER1");
            add("BOMB_OVER2");
            add("BOMB_OVER3");
            add("BOMB_OVER4");
            add("BOMB_OVER5");
            add("BOMB_OVER6");
            add("BOMB_OVER7");
            add("BOMB_OVER8");
            add("BOMB_OVER9");
            add("BOMB_OVER10");
            add("BOMB_OVER11");
            add("BOMB_OVER12");
            add("BOMB_OVER13");
            add("BOMB_OVER14");
            add("BOMB_OVER15");
            add("BOMB_OVER16");
        }
    };
    @Override
    public ElementObj createElement(String str) {
        String[] split = str.split(",");
        for(String str1 : split) {//X:3
            String[] split2 = str1.split(":");// 0下标 是 x,y,f   1下标是值
            switch(split2[0]) {
                case "x": this.setX(Integer.parseInt(split2[1])+85);break;
                case "y":this.setY(Integer.parseInt(split2[1])+70);break;
            }
        }
        ImageIcon icon= GameLoad.imgMap.get("PLANE_BOMB0");
//        this.setW(icon.getIconWidth());
//        this.setH(icon.getIconHeight());
        this.setW(45);
        this.setH(45);
        this.setIcon(icon);
        animation = new Animation(4);
        return this;
    }

    @Override
    public void showElement(Graphics g) {
//		绘画图片
        g.drawImage(this.getIcon().getImage(),
                this.getX(), this.getY(),
                this.getW(), this.getH(), null);
    }
    @Override
    protected void move() {
        //炮弹应该是在飞机下方出现后开始下坠，到地面消失，然后触发死亡效果

        //消失
        if (this.getY()>=Settings.GameY-150){
            overKey=true;//炮弹消失
//            this.setLive(false);
//            return;
            this.setW(130);
            this.setH(130);
            //由于炮弹和爆炸效果图片大小不同，为了不穿帮，需要把爆炸效果坐标往左上调整一下
            this.setX(this.getX()-50);
            this.setY(this.getY()-65);
        }
        //炮弹没有到地面 向下坠
        if (!overKey){
            this.setY(this.getY()+this.moveNum);
        }
    }

    @Override
    protected void updateImage(long gameTime) {
        if (overKey&&!markTime){
            initTime=gameTime;//炮弹落地后记录初始时间
            markTime=true;//标记已经记录了初始时间
        }
        if (overKey){
            //触发死亡动画
            animation.SetAnimation(overAnimationList);
            this.setIcon(animation.LoadSprite(gameTime));
        }else {
            animation.SetAnimation(bombAnimationList);
            this.setIcon(animation.LoadSprite(gameTime));
        }
        //结束死亡动画
        if (overKey&&markTime&&gameTime-initTime>=100){
            SoundManager.getManager().PlaySound("res/music/music (16).wav");
//            ElementObj obj = GameLoad.getObj("enemyfile");
//            ElementObj element = obj.createElement("Left"+","+ this.getX() +","+this.getY()+20);
//            ElementManager.getManager().addElement(element, GameElement.ENEMYFILE);
            this.setLive(false);
        }

    }


}
