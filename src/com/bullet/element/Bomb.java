package com.bullet.element;

import com.bullet.manager.Settings;

import java.awt.*;

public class Bomb extends ElementObj{
    private int attack;//攻击力

    public Bomb(){}
    private String fx;
    @Override
    public ElementObj createElement(String str) {
        return super.createElement(str);
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
        if(this.getX()<0 || this.getX() > Settings.GameX ||
                this.getY() <0 || this.getY()>Settings.GameY) {
            this.setLive(false);
            return;
        }
        switch(this.fx) {
            case "up": this.setY(this.getY()-this.moveNum);break;
            case "left": this.setX(this.getX()-this.moveNum);break;
            case "right": this.setX(this.getX()+this.moveNum);break;
            case "down": this.setY(this.getY()+this.moveNum);break;
        }

    }
}
