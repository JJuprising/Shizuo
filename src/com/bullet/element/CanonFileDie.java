package com.bullet.element;

import com.bullet.manager.GameLoad;
import com.bullet.view.Animation;

import javax.swing.*;
import java.awt.*;

public class CanonFileDie extends ElementObj {
    private String fx;
    Animation Die;

    public CanonFileDie() {
    }

    @Override
    public void showElement(Graphics g) {
        g.drawImage(this.getIcon().getImage(), this.getX(), this.getY(), this.getW(), this.getH(), null);
    }

    @Override
    public ElementObj createElement(String str) {
        String[] arrs = str.split(",");
        this.fx = arrs[0];
        this.setX(new Integer(arrs[1]));
        this.setY(new Integer(arrs[2]));
        this.setDie(new Animation(5));
        ImageIcon icon;
        if (this.fx.equals("Right")) {
            icon = GameLoad.imgMap.get("CanonFile_Die_Right_000");
            this.setIcon(icon);
            this.setH(icon.getIconHeight());
            this.setW(icon.getIconWidth());
            this.getDie().SetAnimation(GameLoad.aniMap.get("CanonFile_Die_Right"));
        }
        if (this.fx.equals("Left")) {
            icon = GameLoad.imgMap.get("CanonFile_Die_Left_000");
            this.setIcon(icon);
            this.setH(icon.getIconHeight());
            this.setW(icon.getIconWidth());
            this.getDie().SetAnimation(GameLoad.aniMap.get("CanonFile_Die_Left"));
        }
        return this;
    }

    @Override
    protected void updateImage(long gameTime){
        this.setIcon(this.getDie().LoadSprite(gameTime));
        if(this.getDie().LoadSprite(gameTime)==GameLoad.imgMap.get("CanonFile_Die_Right_008") || this.getDie().LoadSprite(gameTime)==GameLoad.imgMap.get("CanonFile_Die_Left_008")){
            this.setLive(false);
        }
    }

    public String getFx() {
        return fx;
    }
    public void setFx(String fx) {
        this.fx = fx;
    }
    public Animation getDie() {
        return Die;
    }
    public void setDie(Animation die) {
        Die = die;
    }
}

