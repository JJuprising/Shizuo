package com.bullet.element;

import com.bullet.manager.*;

import javax.swing.*;
import java.awt.*;

public class CanonFile extends ElementObj{
    private int moveNum = 1;
    private String fx;

    public CanonFile(){}

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
            icon = GameLoad.imgMap.get("Canon_Right");
            this.setIcon(icon);
            this.setH(icon.getIconHeight());
            this.setW(icon.getIconWidth());
        }
        if (this.fx.equals("Left")){
            icon = GameLoad.imgMap.get("Canon_Left");
            this.setIcon(icon);
            this.setH(icon.getIconHeight());
            this.setW(icon.getIconWidth());
        }
        System.out.println(this.getH());
        System.out.println(this.getW());
        return this;
    }

    @Override
    protected void move(){
        if(this.getX()<0||this.getX()> Settings.GameX){
            this.setLive(false);
            return;
        }
        //和主角相撞就setLive(false)
        switch (this.fx){
            case "Right": this.setX(this.getX()-this.moveNum);break;
            case "Left": this.setX(this.getX()+this.moveNum);break;
        }
    }

    @Override
    public void die(){
        ElementObj obj = GameLoad.getObj("canonfiledie");
        SoundManager.getManager().PlaySound("res/music/fire.wav");
        int locaX = 0;
        int locaY = this.getY()-50;
        if(this.fx.equals("Left")){
            locaX = this.getX()+30;
        }
        if(this.fx.equals("Right")){
            locaX = this.getX()-250;
        }

        ElementObj element = obj.createElement(this.fx+","+locaX+","+locaY);
        ElementManager.getManager().addElement(element, GameElement.CANONFILEDIE);
        GameManager.getManager().setScore(200);
    }
}
