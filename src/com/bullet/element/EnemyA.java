package com.bullet.element;

import com.bullet.manager.GameLoad;

import java.awt.Graphics;
import java.util.Random;

import javax.swing.ImageIcon;

public class EnemyA extends ElementObj{

	private int spawnPos;
	private boolean isLeft = true;
	private String fx="left";
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
		spawnPos = x;
		int y=ran.nextInt(500);
		this.setX(x);
		this.setY(y);
		this.setW(30);
		this.setH(30);
		this.setIcon(new ImageIcon("image/tank/bot/bot_up.png"));
		return this;
	}
	@Override
	public void move(){
		if(isLeft&& this.getX()>0){
			this.setX(this.getX() - 1);
			fx="left";
		}
		if(!isLeft&&this.getX()<800-this.getW()){
			this.setX(this.getX() + 1);
			fx="right";
		}
		if(Math.abs(this.getX()-spawnPos)>100||this.getX()<2||this.getX()>795-this.getW()){
			isLeft = !isLeft;
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
