package com.bullet.view;

import com.bullet.manager.GameLoad;

import javax.swing.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Animation {
    public Animation(int animationSpeed){

        this.animationSpeed = animationSpeed;//间隔，越小越快
    }
    private ArrayList<String> sprites;
    public int animationSpeed;//动画时间间隔
    private long startTime =-1;
    private int idx = 0;

    public ImageIcon LoadSprite(long currentTime) {

        if(startTime ==-1){
            startTime = currentTime;
        }
        if (currentTime > startTime + animationSpeed) {
            startTime = currentTime+ animationSpeed;
            idx = (++idx)%sprites.size();
        }
        return GameLoad.imgMap.get(sprites.get(idx));
    }
    public void ResetAnimation() {
        idx = 0;
        startTime = -1;
    }
    public void SetAnimation(ArrayList<String> sprites) {
        this.sprites = sprites;
    }
}