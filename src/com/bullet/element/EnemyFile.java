package com.bullet.element;

import com.bullet.manager.GameLoad;
import com.bullet.manager.Settings;

import javax.swing.*;
import java.awt.*;

public class EnemyFile extends ElementObj{
    private int moveNum = 1;
    private String fx;

    public EnemyFile(){}

    @Override
    public void showElement(Graphics g) {
        g.drawImage(this.getIcon().getImage(), this.getX(), this.getY(), this.getW(), this.getH(), null);
    }

    @Override
    public ElementObj createElement(String str){
        String[] arrs = str.split(",");
        this.fx = arrs[0];
        this.setX(new Integer(arrs[1]));
        this.setY(new Integer(arrs[2]));
        ImageIcon icon;
        if(this.fx.equals("Right")){
            icon = GameLoad.imgMap.get("Bullet_Right");
            this.setIcon(icon);
            this.setH(icon.getIconHeight());
            this.setW(icon.getIconWidth());
        }
        if (this.fx.equals("Left")){
            icon = GameLoad.imgMap.get("Bullet_Left");
            this.setIcon(icon);
            this.setH(icon.getIconHeight());
            this.setW(icon.getIconWidth());
        }
        return this;
    }

    @Override
    protected void move(){
        if(this.getX()<0||this.getX()> Settings.GameX){
            this.setLive(false);
            return;
        }
        switch (this.fx){
            case "Right": this.setX(this.getX()-this.moveNum);break;
            case "Left": this.setX(this.getX()+this.moveNum);break;
        }
    }
}