package com.bullet.element;

import java.awt.*;


//只是创建普通的敌人类
public class Enemy extends ElementObj{

    @Override
    public void showElement(Graphics g) {
        g.drawImage(this.getIcon().getImage(), this.getX(), this.getY(), this.getW(), this.getH(), null);
    }

    @Override
    public ElementObj createElement(String str){
        

        return this;
    }
}
