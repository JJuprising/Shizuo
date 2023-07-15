package com.bullet.view;

import java.awt.*;
import java.util.List;
import java.util.Map;
import javax.swing.JPanel;
import com.bullet.element.ElementObj;
import com.bullet.manager.ElementManager;
import com.bullet.manager.GameElement;
import com.bullet.manager.GameManager;
import com.bullet.manager.Settings;

/**
 * @说明 游戏的主要面板
 * @author renjj
 * @功能说明 主要进行元素的显示，同时进行界面的刷新(多线程)
 * 
 * @题外话 java开发实现思考的应该是：做继承或者是接口实现
 * 
 * @多线程刷新 1.本类实现线程接口
 *             2.本类中定义一个内部类来实现
 */
public class GameMainJPanel extends JPanel implements Runnable{
//	联动管理器
	private ElementManager em;
	private GameManager gm;
	public GameMainJPanel() {
		init();
		initLabel();
	}

	public void init() {
		em = ElementManager.getManager();//得到元素管理器对象
		gm = GameManager.getManager();
		setLayout(null);
	}
	private void initLabel(){
		Label weaponLabel = new Label();
		Label ammoLabel = new Label();
		Label HpLabel = new Label();
		Label scoreLabel = new Label();
		Label levelLabel = new Label();

		this.add(weaponLabel);
		this.add(ammoLabel);
		this.add(HpLabel);
		this.add(scoreLabel);
		this.add(levelLabel);

		weaponLabel.setBounds(20,Settings.GameY+Settings.GameDeltaY,200,30);
		ammoLabel.setBounds(220,Settings.GameY+Settings.GameDeltaY,200,30);
		HpLabel.setBounds(420,Settings.GameY+Settings.GameDeltaY,200,30);
		scoreLabel.setBounds(620,Settings.GameY+Settings.GameDeltaY,200,30);
		levelLabel.setBounds(820,Settings.GameY+Settings.GameDeltaY,200,30);

		gm.SetLabel(weaponLabel,ammoLabel,HpLabel,scoreLabel,levelLabel);
		gm.UpdateLabel();



	}
	/**
	 * paint方法是进行绘画元素。
	 * 绘画时是有固定的顺序，先绘画的图片会在底层，后绘画的图片会覆盖先绘画的
	 * 约定：本方法只执行一次,想实时刷新需要使用 多线程
	 */
	@Override  //用于绘画的    Graphics 画笔 专门用于绘画的
	public void paint(Graphics g) {
		super.paint(g);  //调用父类的paint方法
//		map  key-value  key是无序不可重复的。
//		set  和map的key一样 无序不可重复的
		Map<GameElement, List<ElementObj>> all = em.getGameElements();
//		GameElement.values();//隐藏方法  返回值是一个数组,数组的顺序就是定义枚举的顺序
		for(GameElement ge:GameElement.values()) {
			List<ElementObj> list = all.get(ge);
			for(int i=0;i<list.size();i++) {
				ElementObj obj=list.get(i);//读取为基类
//				if(ge.equals(GameElement.PLAYFILE)) {
//					System.out.println(":::::::::::"+obj);
//				}
				obj.showElement(g);//调用每个类的自己的show方法完成自己的显示
			}
		}
		
//		Set<GameElement> set = all.keySet(); //得到所有的key集合
//		for(GameElement ge:set) { //迭代器
//			List<ElementObj> list = all.get(ge);
//			for(int i=0;i<list.size();i++) {
//				ElementObj obj=list.get(i);//读取为基类
//				obj.showElement(g);//调用每个类的自己的show方法完成自己的显示
//			}
//		}
		
	}
	@Override
	public void run() {  //接口实现
		while(GameManager.IsGameRunning()) {
//			System.out.println("多线程运动");
			this.repaint();
//			一般情况下，多线程都会使用一个休眠,控制速度
			try {
				Thread.sleep(10); //休眠10毫秒 1秒刷新20次
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		}
	}
}