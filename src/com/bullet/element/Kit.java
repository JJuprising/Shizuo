package com.bullet.element;

import java.awt.Graphics;

import javax.swing.ImageIcon;

import com.bullet.manager.GameLoad;
import com.bullet.manager.GameManager;

public class Kit extends ElementObj{
	
	@Override
	public ElementObj createElement(String str) {
		String[] split = str.split(",");
		for(String str1 : split) {//X:3
			String[] split2 = str1.split(":");// 0下标 是 x,y,f   1下标是值
			switch(split2[0]) {
			case "x": this.setX(Integer.parseInt(split2[1]));break;
			case "y":this.setY(Integer.parseInt(split2[1]));break;
			}
		}
		ImageIcon icon;
		icon = GameLoad.imgMap.get("KIT0");
		this.setW(icon.getIconWidth());
		this.setH(icon.getIconHeight());
		this.setIcon(icon);
		return this;	
	}
	@Override
	public void showElement(Graphics g) {
		g.drawImage(this.getIcon().getImage(), this.getX(), this.getY(), this.getW(), this.getH(), null);
	}
	
	@Override
	protected void move() {
		if (GameManager.PlayPositionX == 300 && !(this.getX() - GameManager.MapPositionX == 400)
				&& GameManager.isMoving && GameManager.fx == "RIGHT_STAND") {
			this.setX(this.getX() - 2);
		}
		if (GameManager.PlayPositionX == 200 && !(this.getX() - GameManager.MapPositionX == 400)
				&& GameManager.isMoving && GameManager.fx == "LEFT_STAND") {
			this.setX(this.getX() + 2);
		}
	}
}
