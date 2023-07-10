package com.bullet.element;

import com.bullet.manager.GameLoad;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class EnemyB extends ElementObj{

	private int spawnPos;
	private boolean isDown = true;
	private String fx="up";
	@Override
	public void showElement(Graphics g) {
		g.drawImage(this.getIcon().getImage(), 
				this.getX(), this.getY(), 
				this.getW(), this.getH(), null);
	}
	@Override
	public ElementObj createElement(String str) {
		Random ran=new Random();
		int x=ran.nextInt(800);
		int y=ran.nextInt(500);
		spawnPos = y;
		this.setX(x);
		this.setY(y);
		this.setW(30);
		this.setH(30);
		this.setIcon(new ImageIcon("image/tank/bot/bot_up.png"));
		return this;
	}
	@Override
	public void move(){
		if(!isDown && this.getY()>0){
			this.setY(this.getY() + 1);
			fx="down";
		}
		if(isDown &&this.getY()<500-this.getH()){
			this.setY(this.getY() - 1);
			fx="up";
		}
		if(Math.abs(this.getY()-spawnPos)>100||this.getY()<2||this.getY()>495-this.getH()){
			isDown = !isDown;

		}
	}

	@Override
	protected void updateImage() {
//		ImageIcon icon=GameLoad.imgMap.get(fx);
//		System.out.println(icon.getIconHeight());//得到图片的高度
//		如果高度是小于等于0 就说明你的这个图片路径有问题
		this.setIcon(GameLoad.imgMap.get(fx));
	}
	
	
	
	
}
