package com.bullet.element;

import com.bullet.manager.GameLoad;
import com.bullet.manager.GameManager;
import com.bullet.manager.Settings;
import com.bullet.view.Animation;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Boss extends ElementObj{
    private boolean pkType=false;//攻击状态 true 攻击  false停止
    private int moveSpeed=0;
    Animation animation;

    @Override
    public ElementObj createElement(String str) {
        this.setX(500);
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
    public void die(){
        super.die();
        GameManager.getManager().setScore(1000);

    }

    @Override
    protected void updateImage(long gameTime) {
        animation.SetAnimation(GameLoad.aniMap.get("Boss"));
        this.setIcon(animation.LoadSprite(gameTime));
    }
}
