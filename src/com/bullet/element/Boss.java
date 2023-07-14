package com.bullet.element;

import com.bullet.manager.GameLoad;
import com.bullet.manager.Settings;
import com.bullet.view.Animation;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Boss extends ElementObj{
    private boolean pkType=false;//攻击状态 true 攻击  false停止
    private int moveSpeed=1;
    Animation animation;
    ArrayList<String> bossAnimationList = new ArrayList<String>(){
        {
            add("BOSS1");
            add("BOSS2");
            add("BOSS3");
            add("BOSS4");
            add("BOSS5");
            add("BOSS6");
            add("BOSS7");
            add("BOSS8");
            add("BOSS9");
            add("BOSS10");
            add("BOSS11");
            add("BOSS12");
            add("BOSS13");
            add("BOSS14");
            add("BOSS15");
            add("BOSS16");
        }
    };

    @Override
    public ElementObj createElement(String str) {
        this.setX(Settings.GameX);
        this.setY(300);
        ImageIcon icon2 = GameLoad.imgMap.get("BOSS1");

        this.setW(icon2.getIconWidth());
        this.setH(icon2.getIconHeight());
        this.setIcon(icon2);
        animation = new Animation(4);
        return this;
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
        this.setX(this.getX()-moveSpeed);

    }

    @Override
    protected void updateImage(long gameTime) {
        animation.SetAnimation(bossAnimationList);
        this.setIcon(animation.LoadSprite(gameTime));
    }
}
