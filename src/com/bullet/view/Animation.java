package com.bullet.view;

import com.bullet.manager.GameLoad;

import javax.swing.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Animation {
    public Animation(ImageIcon target, ArrayList<String> sprites, int animationSpeed){
        this.sprites = sprites;
        this.target = target;
        this.animationSpeed = animationSpeed;//间隔，越小越快
    }
    private ImageIcon target;
    private ArrayList<String> sprites;
    public int animationSpeed;//动画时间间隔
    private long startTime =-1;
    private long currentTime;
    private int idx = 0;

    public ImageIcon LoadSprite(long currentTime) {

        if(startTime ==-1){
            startTime = currentTime;

        }

//        if(currentTime-startTime>animationSpeed*sprites.size()){//播完了
//            ResetAnimation();
//        }
//        else{
            if (currentTime > startTime + animationSpeed) {
                startTime = currentTime+ animationSpeed;
                System.out.println("123");
//                if (idx < sprites.size()-1)
//                    idx++;
//                else
//                    ResetAnimation();
                idx = (++idx)%sprites.size();
            }
//        }
        return GameLoad.imgMap.get(sprites.get(idx));
    }
    public void ResetAnimation() {
        idx = 0;
        startTime = -1;
    }


}
