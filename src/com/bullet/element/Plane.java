package com.bullet.element;

import com.bullet.manager.GameLoad;
import com.bullet.manager.Settings;

import javax.swing.*;
import java.awt.*;

public class Plane extends ElementObj{
    //	变量专门用来记录飞机面向的方向,默认为是left
    private String fx="LEFT_PLANE";
    private boolean pkType=false;//攻击状态 true 攻击  false停止
    private boolean isLeft = true;//方向
    @Override
    public void showElement(Graphics g) {
        g.drawImage(this.getIcon().getImage(),
                this.getX(), this.getY(),
                this.getW(), this.getH(), null);
    }

    @Override
    public ElementObj createElement(String str) {
        //出现位置应该是屏幕右边界
        int x= Settings.GameX;
        int y= 50;
        this.setX(x);
        this.setY(y);
//        ImageIcon icon=GameLoad.imgMap.get("LEFT_PLANE");
        ImageIcon icon=new ImageIcon("res/images/飞机/plane_fly0.png");
        this.setW(icon.getIconWidth());
        this.setH(icon.getIconHeight());
        this.setIcon(icon);
        System.out.println("飞机生成");
        return this;
    }

    @Override
    protected void updateImage() {
        this.setIcon(GameLoad.imgMap.get(fx));
    }

    @Override
    protected void move() {
        if (this.getX()==0){
            isLeft=!isLeft;
        }
        if(isLeft&& this.getX()>0){
            this.setX(this.getX() - 1);
            fx="LEFT_PLANE";
        }
        if(!isLeft&&this.getX()<800){
            this.setX(this.getX() + 1);
            fx="RIGHT_PLANE";

        }
    }
}

