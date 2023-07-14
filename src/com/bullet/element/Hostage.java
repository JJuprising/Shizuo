package com.bullet.element;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.ImageIcon;

import com.bullet.manager.GameLoad;
import com.bullet.view.Animation;

public class Hostage extends ElementObj{

	private long Time =0;
	private boolean canSave = true; //true表示能拯救 false表示不能拯救
	Animation animation;

	@Override
	public ElementObj createElement(String str) {
		ImageIcon icon;
		icon = GameLoad.imgMap.get("HOSTAGE_STAY_0");
		this.setX(400);
		this.setY(400);
		this.setW(icon.getIconWidth());
		this.setH(icon.getIconHeight());
		this.setIcon(icon);
		animation = new Animation(20);
		animation.SetAnimation(GameLoad.aniMap.get("Hostage"));
//		animation.SetAnimation(GameLoad.aniMap.get("LeftGun"));

//		System.out.println(GameLoad.aniMap.get("Hostage"));
		return this;
	}
	
	@Override
	public void showElement(Graphics g) {
		g.drawImage(this.getIcon().getImage(), this.getX(), this.getY(), this.getW(), this.getH(), null);
	}

	@Override
	protected void updateImage(long gameTime) {

		this.setIcon(animation.LoadSprite(gameTime));

//		System.out.println(this.icon);



	}
}
