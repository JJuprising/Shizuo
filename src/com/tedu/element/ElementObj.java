package com.tedu.element;

import javax.swing.*;
import java.awt.*;

/*
 * 所有元素的基类，
 * x 左上角x坐标
 * y 右上角y坐标
 * w w宽度
 * h h高度
 * icon 图片
 */
public abstract class ElementObj {
    private int x;
    private int y;
    private int w;
    private int h;
    private ImageIcon icon;

    //还有各种必要的状态值
    public ElementObj(){//无参构造无作用，只是为了继承的时候不要报错写的

    }

    public ElementObj(int x, int y, int w, int h, ImageIcon icon) {
        super();
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.icon = icon;
    }

    /**
     * 抽象方法，显示元素
     * g 画笔 用于进行绘画
     */
    public abstract void showElement(Graphics g);

    /**
     * 只要是VO类(值对象,POJO就是object)， 就要为属性生成get和set方法
     */
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }
}
