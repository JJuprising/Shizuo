package com.bullet.element;

import com.bullet.manager.GameLoad;
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
			case "BACKGROUND":
				icon=GameLoad.imgMap.get("BACKGROUND");
				break;
		}
		int x=Integer.parseInt(arr[1]);
		int y=Integer.parseInt(arr[2]);
		int w=icon.getIconWidth();
		int h=icon.getIconHeight();
		this.setH(h);
		this.setW(w);
		this.setX(x);
		this.setY(y);
		this.setIcon(icon);
		return this;
	}
}




