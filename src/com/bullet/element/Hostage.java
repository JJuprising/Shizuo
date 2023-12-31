package com.bullet.element;

import java.awt.Graphics;

import javax.swing.ImageIcon;

import com.bullet.manager.ElementManager;
import com.bullet.manager.GameElement;
import com.bullet.manager.GameLoad;
import com.bullet.manager.GameManager;
import com.bullet.view.Animation;

public class Hostage extends ElementObj{

	private long Time =0;
	Animation animationStay;
	Animation animationSave;
	

	@Override
	public ElementObj createElement(String str) {
		String[] split = str.split(",");
		this.setX(Integer.parseInt(split[0]));
		this.setY(Integer.parseInt(split[1]));
		ImageIcon icon;
		icon = GameLoad.imgMap.get("HOSTAGE_STAY_0");

		this.setW(icon.getIconWidth());
		this.setH(icon.getIconHeight());
		this.setIcon(icon);
		animationStay = new Animation(20);
		animationSave = new Animation(10);
		
		animationStay.SetAnimation(GameLoad.aniMap.get("Hostage"));
		animationSave.SetAnimation(GameLoad.aniMap.get("HostageSave"));

		GameManager.HostagePositionY = this.getY();
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
			GameManager.HostagePositionX = this.getX();
		}
		if (GameManager.PlayPositionX == 200 && !(this.getX() - GameManager.MapPositionX == 400)
				&& GameManager.isMoving && GameManager.fx == "LEFT_STAND") {
			this.setX(this.getX() + 2);
			GameManager.HostagePositionX = this.getX();
		}
	}

	@Override
	protected void add(long gameTime) {
		if (!GameManager.canSave && !GameManager.isGive && gameTime - this.Time > 150) {
			ElementObj obj=GameLoad.getObj("kit");  		
			ElementObj element = obj.createElement(this.toString());
			ElementManager.getManager().addElement(element, GameElement.KIT);
			GameManager.isGive = true;
		}
	}
	
	@Override
	protected void updateImage(long gameTime) {
		if (GameManager.canSave) {
			this.setIcon(animationStay.LoadSprite(gameTime));
			this.Time = gameTime;
		}
		if (!GameManager.canSave) {
			this.setIcon(animationSave.LoadSprite(gameTime));
		}
		if (gameTime - this.Time >200) {
			this.setLive(false);
		}
	}
	@Override
	public void die(){
		super.die();
		GameManager.getManager().setScore(500);
	}
	@Override
	public String toString() {
		//  {X:3,y:5} json格式
		int x=this.getX();
		int y=this.getY()+50;
		return "x:"+x+",y:"+y;
	}
}
