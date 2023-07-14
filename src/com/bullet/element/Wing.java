package com.bullet.element;

import com.bullet.manager.ElementManager;
import com.bullet.manager.GameElement;
import com.bullet.manager.GameLoad;
import com.bullet.manager.Settings;

import javax.swing.*;
import java.awt.*;

/**
 * 飞机机翼类
 */
public class Wing extends ElementObj{
    //	变量专门用来记录飞机面向的方向,默认为是left
    private String fx="FRONT_WING";
    private boolean pkType=false;//攻击状态 true 攻击  false停止
    private boolean isLeft = true;//方向
    private long fireTime =0;
    private String name;//机翼的type 前翼FRONT_WING，后翼BACK_WING
    private int moveSpeed=3;
    @Override
    public void showElement(Graphics g) {
        g.drawImage(this.getIcon().getImage(),
                this.getX(), this.getY(),
                this.getW(), this.getH(), null);
    }

    /**
     *
     * @param str 也就是name
     * @return
     */
    @Override
    public ElementObj createElement(String str) {
        //出现位置应该是屏幕右边界
        int x= 0;
        int y= 0;

        ImageIcon icon=null; /*GameLoad.imgMap.get("LEFT_PLANE");*/


        name=str;
        switch (name){
            case "FRONT_WING":
                icon=GameLoad.imgMap.get("FRONT_WING");
                x=Settings.GameX-10;
                y=42;
                break;
            case "BACK_WING":
                icon=GameLoad.imgMap.get("BACK_WING");
                x=Settings.GameX+74;
                y=40;
                break;
        }

        this.setX(x);
        this.setY(y);

        this.setW(icon.getIconWidth());
        this.setH(icon.getIconHeight());
        this.setIcon(icon);
        System.out.println("机翼生成");
        return this;
    }

    @Override
    protected void updateImage(long gameTime) {
        this.setIcon(GameLoad.imgMap.get(name));
    }

    @Override
    protected void move() {
        int distance=100;
        switch (name){
            case "FRONT_WING":
                distance=9;
                break;
            case "BACK_WING":
                distance=90;
                break;
        }
        if (this.getX()<=distance){
            isLeft=!isLeft;
            switch (name){
                case "FRONT_WING":
                    this.setX(this.getX()+19);
                    break;
                case "BACK_WING":
                    this.setX(this.getX()+25);
                    break;
            }

        }
        if(isLeft&& this.getX()>0){
            this.setX(this.getX() - moveSpeed);

        }
        if(!isLeft&&this.getX()<600){
            this.setX(this.getX() + moveSpeed);

        }
    }

}
