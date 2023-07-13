package com.bullet.element;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.ImageIcon;

import com.bullet.view.Animation;

public class Hostage extends ElementObj{
	private ImageIcon icon;
	private long Time =0;
	private boolean canSave = true; //true表示能拯救 false表示不能拯救

	@Override
	public ElementObj createElement(String str) {
		icon = new ImageIcon("res/images/人质/ode10.png");
		this.setX(400);
		this.setY(400);
		this.setW(this.icon.getIconWidth());
		this.setH(this.icon.getIconHeight());
		this.setIcon(icon);
		return this;
	}
	
	@Override
	public void showElement(Graphics g) {
		g.drawImage(this.icon.getImage(), this.getX(), this.getY(), this.getW(), this.getH(), null);
	}

	private int i = 10;
	@Override
	protected void updateImage(long gameTime) {
		if(gameTime- Time >20){
			Time = gameTime;
			this.icon = new ImageIcon("res/images/人质/oder"+ i + ".png");
			this.setW(this.icon.getIconWidth());
			this.setH(this.icon.getIconHeight());
			i++;
			if (i == 12) {
				i = 10;
			}
		}
	}
}
