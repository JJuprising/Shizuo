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
	//联动管理器
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

		weaponLabel.setBounds(20,Settings.GameY + 20,200,30);
		weaponLabel.setFont(new Font("宋体",Font.BOLD,20));
		ammoLabel.setBounds(220,Settings.GameY+ 20,200,30);
		ammoLabel.setFont(new Font("宋体",Font.BOLD,20));
		HpLabel.setBounds(420,Settings.GameY+ 20,200,30);
		HpLabel.setFont(new Font("宋体",Font.BOLD,20));
		scoreLabel.setBounds(620,Settings.GameY+ 20,200,30);
		scoreLabel.setFont(new Font("宋体",Font.BOLD,20));
		levelLabel.setBounds(820,Settings.GameY+ 20,200,30);
		levelLabel.setFont(new Font("宋体",Font.BOLD,20));

		gm.SetLabel(weaponLabel,ammoLabel,HpLabel,scoreLabel,levelLabel);
		gm.UpdateLabel();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);  //调用父类的paint方法
		Map<GameElement, List<ElementObj>> all = em.getGameElements();
		for(GameElement ge:GameElement.values()) {
			List<ElementObj> list = all.get(ge);
			for(int i=0;i<list.size();i++) {
				ElementObj obj=list.get(i);//读取为基类
				obj.showElement(g);//调用每个类的show方法完成自己的显示
			}
		}
		
	}

	@Override
	public void run() {//接口实现
		while(true) {
			this.repaint();
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
		}
	}
}