package com.bullet.element;

import com.bullet.manager.ElementManager;
import com.bullet.manager.GameElement;
import com.bullet.manager.GameLoad;
import com.bullet.manager.Settings;

import javax.swing.*;
import java.awt.*;

public class Plane extends ElementObj{
    //	变量专门用来记录飞机面向的方向,默认为是left
    private String fx="LEFT_PLANE";
    private boolean pkType=false;//攻击状态 true 攻击  false停止
    private boolean isLeft = true;//方向
    private long fireTime =0;
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

        String[] split = str.split(",");
        this.setX(Integer.parseInt(split[0]));
        this.setY(Integer.parseInt(split[1]));

        ImageIcon icon=null; /*GameLoad.imgMap.get("LEFT_PLANE");*/
        //机身
        icon=GameLoad.imgMap.get("LEFT_PLANE");

        this.setW(icon.getIconWidth());
        this.setH(icon.getIconHeight());
        this.setIcon(icon);
//        System.out.println("飞机生成");
        return this;
    }

    @Override
    protected void updateImage(long gameTime) {
        this.setIcon(GameLoad.imgMap.get(fx));
    }

    @Override
    protected void move() {
        if (this.getX()<=10){
            isLeft=false;
            fx="RIGHT_PLANE";
        }
        if(this.getX()>Settings.GameX-10){
            isLeft=true;
        }
        if(isLeft&& this.getX()>0){
            this.setX(this.getX() - moveSpeed);
            fx="LEFT_PLANE";
        }
        if(!isLeft&& this.getX()<Settings.GameX){
            this.setX(this.getX() + moveSpeed);
            fx="RIGHT_PLANE";
        }
    }

    @Override   //添加炮弹
    public void add(long gameTime) {//有啦时间就可以进行控制
//        if(!this.pkType) {//如果是不发射状态 就直接return
//            return;
//        }
        if(gameTime- fireTime >200){

            fireTime = gameTime;
//		new PlayFile(); // 构造一个类 需要做比较多的工作  可以选择一种方式，使用小工厂
//		将构造对象的多个步骤进行封装成为一个方法，返回值直接是这个对象
//		传递一个固定格式   {X:3,y:5,f:up} json格式
            ElementObj obj=GameLoad.getObj("bomb");
            ElementObj element = obj.createElement(this.toString());
//		System.out.println("子弹是否为空"+element);
//		装入到集合中
            ElementManager.getManager().addElement(element, GameElement.BOMB);
//		如果要控制子弹速度等等。。。。还需要代码编写
        }
    }
    @Override
    public String toString() {// 这里是偷懒，直接使用toString；建议自己定义一个方法
        //  {X:3,y:5,f:up,t:A} json格式
        int x=this.getX();
        int y=this.getY();
        return "x:"+x+",y:"+y;
    }
}

