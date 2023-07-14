package com.bullet.view;

import com.bullet.manager.GameLoad;
import javax.swing.*;
import java.util.ArrayList;


public class Animation {
    public Animation(int animationSpeed){

        this.animationSpeed = animationSpeed;//间隔，越小越快
    }
    private ArrayList<String> sprites;
    public int animationSpeed;//动画时间间隔
    private long startTime =-1;
    private int idx = 0;

    public ImageIcon LoadSprite(long currentTime) {//返回当前帧的图图

        if(startTime ==-1){
            startTime = currentTime;
        }
        if (currentTime > startTime + animationSpeed) {
            startTime = currentTime+ animationSpeed;
            idx = (++idx)%sprites.size();
        }
        return GameLoad.imgMap.get(sprites.get(idx));
    }
    public void ResetAnimation() {//重置动画
        idx = 0;
        startTime = -1;
    }
    public void SetAnimation(ArrayList<String> sprites) {
        this.sprites = sprites;
    }//设置动画序列，列表的字符串是GameData里的键

}
