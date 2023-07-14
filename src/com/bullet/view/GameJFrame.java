package com.bullet.view;

import com.bullet.controller.GameThread;
import com.bullet.manager.ElementManager;
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
	private static GameThread thread =null;  //游戏主线程
	private static Label label;
	
	public GameJFrame() {
		init();
	}
	public void init() {
		this.setSize(Settings.GameX, Settings.GameY+39); //设置窗体大小
		this.setTitle("Bullet Fight");
//		addButton();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置退出并且关闭
		this.setLocationRelativeTo(null);//屏幕居中显示
	}
	/*窗体布局: 可以讲 存档，读档。button   给大家扩展的*/
	public void addButton() {
	}	
	/**
	 * 启动方法
	 */
	Thread th;
	private boolean isStart = false;

	public void start() {
		if(!isStart&&this.jPanel instanceof Runnable){//是否开启过线程，只有游戏界面需要键盘监听和游戏线程
			System.out.println(keyListener);
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

	/*set注入：等大家学习ssm 通过set方法注入配置文件中读取的数据;讲配置文件
	 * 中的数据赋值为类的属性
	 * 构造注入：需要配合构造方法
	 * spring 中ioc 进行对象的自动生成，管理。
	 * */
	public void setjPanel(JPanel jPanel) {
		if(this.jPanel!=null){
			this.remove(this.jPanel);

		}
		this.jPanel = jPanel;
		this.add(jPanel);
		jPanel.repaint();
//		System.out.println(123);
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