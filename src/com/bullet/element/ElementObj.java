package com.bullet.element;

import java.awt.Graphics;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public abstract class ElementObj {

	private int x;
	private int y;
	private int w;
	private int h;
	private ImageIcon icon;
	private boolean live=true; //生存状态 true 代表存在，false代表死亡
	public ElementObj() {}
	/**
	 * @说明 带参数的构造方法; 可以由子类传输数据到父类
	 * @param x    左上角X坐标
	 * @param y    左上角y坐标
	 * @param w    w宽度
	 * @param h    h高度
	 * @param icon  图片
	 */
	public ElementObj(int x, int y, int w, int h, ImageIcon icon) {
		super();
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.icon = icon;
	}

	public abstract void showElement(Graphics g);
	
	/**
	 * @说明 使用父类定义接收键盘事件的方法
	 * 	         只有需要实现键盘监听的子类，重写这个方法(约定)
	 * @说明 方式2 使用接口的方式;使用接口方式需要在监听类进行类型转换
	 * @题外话 约定  配置  现在大部分的java框架都是需要进行配置的.
	 *         约定优于配置
	 * @param bl   点击的类型  true代表按下，false代表松开
	 * @param key  代表触发的键盘的code值  
	 * @扩展 本方法是否可以分为2个方法？1个接收按下，1个接收松开(给同学扩展使用)
	 */
	public void keyClick(boolean bl,int key) {  //这个方法不是强制必须重写的。
		System.out.println("测试使用");
	}

	protected void updateImage(){};

	/**
	 * @说明 移动方法; 需要移动的子类，请 重写这个方法
	 */
	protected void move() {	
	}

	//更新图片、移动、发射子弹连贯动作模板
	public final void model(long gameTime) {
		updateImage(gameTime);
		move();
		add(gameTime);
	}

	protected void updateImage(long gameTime) {}
	protected void add(long gameTime){}
	
//	死亡方法，死亡也作为一个对象
	public void die() {}

	//创建对象
	public  ElementObj createElement(String str) {
		return null;
	}

	//返回对象的碰撞矩阵
	public Rectangle getRectangle() {
		return new Rectangle(x,y,w,h);
	}

	/**
	 * @说明 碰撞方法
	 * 一个是 this对象 一个是传入值 obj
	 * @param obj
	 * @return boolean 返回true 说明有碰撞，返回false说明没有碰撞
	 */
	public boolean pk(ElementObj obj) {	
		return this.getRectangle().intersects(obj.getRectangle());
	}

	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getW() {
		return w;
	}
	public void setW(int w) {
		this.w = w;
	}
	public int getH() {
		return h;
	}
	public void setH(int h) {
		this.h = h;
	}
	public ImageIcon getIcon() {
		return icon;
	}
	public void setIcon(ImageIcon icon) {
		this.icon = icon;
	}
	public boolean isLive() {
		return live;
	}
	public void setLive(boolean live) {
		this.live = live;
	}
}










