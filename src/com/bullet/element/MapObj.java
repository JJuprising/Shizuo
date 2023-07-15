package com.bullet.element;

import com.bullet.manager.GameLoad;
import com.bullet.manager.GameManager;

import java.awt.Graphics;
import javax.swing.ImageIcon;

// 地图类
public class MapObj extends ElementObj{
	//重写的绘图函数
	@Override
	public void showElement(Graphics g) {
		g.drawImage(this.getIcon().getImage(), this.getX(), this.getY(), this.getW(),this.getH(),null);
	}
	
	@Override
	public ElementObj createElement(String str) {
		String []arr=str.split(",");
		ImageIcon icon=null;
		switch(arr[0]) { //设置图片信息 图片还未加载到内存中
			case "BACKGROUND1":
				icon=GameLoad.imgMap.get("BACKGROUND1");
				break;
			case "BACKGROUND2":
				icon=GameLoad.imgMap.get("BACKGROUND2");
				break;
		}
		int x=Integer.parseInt(arr[1]);
		int y=Integer.parseInt(arr[2]);
		int w=icon.getIconWidth();
		int h=icon.getIconHeight();
		this.setH(h);
		this.setW(w+1000);
		this.setX(x);
		this.setY(y);
		this.setIcon(icon);
		return this;
	}
	
	@Override
	protected void move() {
		if (GameManager.PlayPositionX == 300 && this.getX() > -1480
				&& GameManager.isMoving && GameManager.fx == "RIGHT_STAND") {
			this.setX(this.getX() - 2);
			GameManager.MapPositionX = this.getX();
		}
		if (GameManager.PlayPositionX == 200 && this.getX() < 0
				&& GameManager.isMoving && GameManager.fx == "LEFT_STAND") {
			this.setX(this.getX() + 2);
			GameManager.MapPositionX = this.getX();
		}
	}
}




