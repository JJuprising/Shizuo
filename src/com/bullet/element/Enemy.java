package com.bullet.element;

import java.awt.*;


//ֻ�Ǵ�����ͨ�ĵ�����
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
