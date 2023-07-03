package com.tedu.element;

import javax.swing.*;
import java.awt.*;

public class Play extends ElementObj{
    public Play(int x, int y, int w, int h, ImageIcon icon){
        super(x,y,w,h,icon);
    }

    /**
     * 面向对象第一个思想：对象自己的事情自己做
     * */
    @Override
    public void showElement(Graphics g) {
        g.drawImage(this.getIcon().getImage(),this.getX(),this.getY(),this.getW(),this.getH(),null);
    }
}
