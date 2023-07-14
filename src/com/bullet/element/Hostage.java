package com.bullet.element;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.ImageIcon;

import com.bullet.manager.GameLoad;
import com.bullet.manager.GameManager;
import com.bullet.view.Animation;

public class Hostage extends ElementObj{

	private long Time =0;
	Animation animationStay;
	Animation animationSave;
	

	@Override
	public ElementObj createElement(String str) {
		ImageIcon icon;
		icon = GameLoad.imgMap.get("HOSTAGE_STAY_0");
		this.setX(400);
		this.setY(400);
		this.setW(icon.getIconWidth());
		this.setH(icon.getIconHeight());
		this.setIcon(icon);
		animationStay = new Animation(20);
		animationSave = new Animation(10);
		
		animationStay.SetAnimation(GameLoad.aniMap.get("Hostage"));
		animationSave.SetAnimation(GameLoad.aniMap.get("HostageSave"));
//		animation.SetAnimation(GameLoad.aniMap.get("LeftGun"));

//		System.out.println(GameLoad.aniMap.get("Hostage"));
		return this;
	}
	
	@Override
	public void showElement(Graphics g) {
		g.drawImage(this.getIcon().getImage(), this.getX(), this.getY(), this.getW(), this.getH(), null);
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
	
	@Override
	protected void updateImage(long gameTime) {
		if (GameManager.canSave) {
			this.setIcon(animationStay.LoadSprite(gameTime));
		}
		if (!GameManager.canSave) {
			this.setIcon(animationSave.LoadSprite(gameTime));


		}
	}
}
