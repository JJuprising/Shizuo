package com.bullet.view;

import com.bullet.controller.GameThread;
import com.bullet.manager.ElementManager;
import com.bullet.manager.GameManager;
import com.bullet.manager.Settings;

import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * @说明 游戏窗体 主要实现的功能：关闭，显示，最大最小化
 * @author renjj
 * @功能说明   需要嵌入面板,启动主线程等等
 * @窗体说明  swing awt  窗体大小（记录用户上次使用软件的窗体样式）
 * 
 * @分析 1.面板绑定到窗体
 *       2.监听绑定
 *       3.游戏主线程启动
 *       4.显示窗体
 */
public class GameJFrame extends JFrame{

	private JPanel jPanel =null; //正在显示的面板
	private KeyListener  keyListener=null;//键盘监听
	private MouseMotionListener mouseMotionListener=null; //鼠标监听
	private MouseListener mouseListener=null;
	private static GameThread thread =null; //游戏主线程
	Thread th;
	private static Label label;
	private boolean isStart = false;
	
	public GameJFrame() {
		init();
	}
	public void init() {
		this.setSize(Settings.GameX, Settings.GameY+Settings.GameDeltaY+Settings.GameInfoY); //窗体大小
		this.setTitle("Bullet Fight");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);//屏幕居中显示
	}
	public void start() {
		if(!isStart&&this.jPanel instanceof Runnable){//判断是否开启过线程
			if(keyListener !=null) {
				this.addKeyListener(keyListener);
			}
			if(thread !=null) {
				thread.start();//启动线程
			}
			if(this.jPanel instanceof Runnable) {//带多线程接口的就运行
				Runnable run=(Runnable)this.jPanel;
				th=new Thread(run);
				th.start();//
			}
			isStart = true;
		}
		this.setVisible(true);//显示界面
		setFocusable(true);
		requestFocus();//聚焦当前面板，使得keylistener能工作
	}

	public void setjPanel(JPanel jPanel) {
		if(this.jPanel!=null){
			this.remove(this.jPanel);
		}
		this.jPanel = jPanel;
		this.add(jPanel);
		jPanel.repaint();
	}
	public void setKeyListener(KeyListener keyListener) {
		this.keyListener = keyListener;
	}
	public void setMouseMotionListener(MouseMotionListener mouseMotionListener) {
		this.mouseMotionListener = mouseMotionListener;
	}
	public void setMouseListener(MouseListener mouseListener) {
		this.mouseListener = mouseListener;
	}
	public void setThread(GameThread thread) {
		this.thread = thread;
	}
	public static void ChangeMap(int mapID){


	}
	public static void SetLabel(String content){
		if(label!=null){
			label.setText(content);
		}
	}
}