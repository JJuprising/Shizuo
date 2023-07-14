package com.bullet.controller;

import java.util.List;
import java.util.Map;
import com.bullet.element.ElementObj;
import com.bullet.element.Hostage;
import com.bullet.manager.ElementManager;
import com.bullet.manager.GameElement;
import com.bullet.manager.GameLoad;
import com.bullet.manager.GameManager;


//主线程
public class GameThread extends Thread{
	private ElementManager em;
	private GameManager gm;

	public boolean isRunning = true;
	private int mapID;
	
	public GameThread(int mapID) {
		em=ElementManager.getManager();
		gm = GameManager.getManager();
		this.mapID = mapID;
	}
	@Override
	public void run() {
		while(true) {
//		游戏开始前   读进度条，加载游戏资源(场景资源)
			isRunning = true;
			gameLoad(mapID);
//		游戏进行时   游戏过程中
			gameRun();
//		游戏场景结束  游戏资源回收(场景资源)
			gameOver();
			try {
				sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
	/**
	 * 游戏的加载
	 */
	private void gameLoad(int MapID) {
		GameLoad.loadImg(); //加载图片
		GameLoad.loadAni();//加载动画
		GameLoad.loadObj();
		GameLoad.MapLoad(MapID);//可以变为 变量，每一关重新加载  加载地图
////		加载主角
		GameLoad.loadPlay();//也可以带参数，单机还是2人
		GameLoad.loadJanpanese();
		GameLoad.loadHostage();

////		加载敌人NPC等
//		GameLoad.loadEnemy();
//		}
////		全部加载完成，游戏启动
	}
	/**
	 * @说明  游戏进行时
	 * @任务说明  游戏过程中需要做的事情：1.自动化玩家的移动，碰撞，死亡
	 *                                 2.新元素的增加(NPC死亡后出现道具)
	 *                                 3.暂停等等。。。。。
	 * 先实现主角的移动
	 * */
	
	private void gameRun() {
		long gameTime=0L;//给int类型就可以啦
		while(isRunning) {// 预留扩展   true可以变为变量，用于控制管关卡结束等
			Map<GameElement, List<ElementObj>> all = em.getGameElements();
			List<ElementObj> player = em.getElementsByKey(GameElement.PLAY);
			List<ElementObj> enemys = em.getElementsByKey(GameElement.ENEMY);
			List<ElementObj> bullets = em.getElementsByKey(GameElement.BULLET);
			List<ElementObj> hostage = em.getElementsByKey(GameElement.HOSTAGE);
//			List<ElementObj> maps = em.getElementsByKey(GameElement.MAPS);

			moveAndUpdate(all,gameTime);//	游戏元素自动化方法

//			System.out.println(gameTime);

			EnemyPK(enemys,bullets);
			HostagePK(hostage,player);
//			MapPK(files,maps);
			
			gameTime++;//唯一的时间控制
			try {
				sleep(10);//默认理解为 1秒刷新100次 
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public void ElementPK(List<ElementObj> listA,List<ElementObj>listB) {
//		请大家在这里使用循环，做一对一判定，如果为真，就设置2个对象的死亡状态
		for(int i=0;i<listA.size();i++) {
			ElementObj enemy=listA.get(i);
			for(int j=0;j<listB.size();j++) {
				ElementObj file=listB.get(j);
				if(enemy.pk(file)) {
//					问题： 如果是boos，那么也一枪一个吗？？？？
//					将 setLive(false) 变为一个受攻击方法，还可以传入另外一个对象的攻击力
//					当收攻击方法里执行时，如果血量减为0 再进行设置生存为 false
//					扩展 留给大家
//					System.out.println(listB);
					enemy.setLive(false);
					file.setLive(false);
					break;
				}
			}
		}
	}
	public void EnemyPK(List<ElementObj> listA,List<ElementObj>listB) {
//		请大家在这里使用循环，做一对一判定，如果为真，就设置2个对象的死亡状态
		for(int i=0;i<listA.size();i++) {
			ElementObj enemy=listA.get(i);
			for(int j=0;j<listB.size();j++) {
				ElementObj file=listB.get(j);
				if(enemy.pk(file)) {
//					问题： 如果是boos，那么也一枪一个吗？？？？
//					将 setLive(false) 变为一个受攻击方法，还可以传入另外一个对象的攻击力
//					当收攻击方法里执行时，如果血量减为0 再进行设置生存为 false
//					扩展 留给大家
//					System.out.println(listB);
					em.AddScore(1);
					enemy.setLive(false);
					file.setLive(false);
					break;
				}
			}
		}
	}
	public void HostagePK(List<ElementObj> listA,List<ElementObj>listB) {
		for(int i=0;i<listA.size();i++) {
			ElementObj hostage=listA.get(i);
			for(int j=0;j<listB.size();j++) {
				ElementObj player=listB.get(j);
				if(hostage.pk(player)) {
					GameManager.HostageCrash = true;
				}
			}
		}
	}
	

//	游戏元素自动化方法
	public void moveAndUpdate(Map<GameElement, List<ElementObj>> all,long gameTime) {
//		GameElement.values();//隐藏方法  返回值是一个数组,数组的顺序就是定义枚举的顺序
		for(GameElement ge:GameElement.values()) {
			List<ElementObj> list = all.get(ge);
//			编写这样直接操作集合数据的代码建议不要使用迭代器。
//			for(int i=0;i<list.size();i++) {
			for(int i=list.size()-1;i>=0;i--){	
				ElementObj obj=list.get(i);//读取为基类
				if(!obj.isLive()) {//如果死亡
//					list.remove(i--);  //可以使用这样的方式
//					启动一个死亡方法(方法中可以做事情例如:死亡动画 ,掉装备)
					obj.die();//需要大家自己补充
					list.remove(i);
					continue;
				}
				obj.model(gameTime);//调用的模板方法 不是move
			}
		}	
	}

	/**游戏切换关卡*/
	private void gameOver() {
		Map<GameElement, List<ElementObj>> all = em.getGameElements();
		for(GameElement ge:GameElement.values()) {
			List<ElementObj> list = all.get(ge);
//			编写这样直接操作集合数据的代码建议不要使用迭代器。
//			for(int i=0;i<list.size();i++) {
			for(int i=list.size()-1;i>=0;i--){
				ElementObj obj=list.get(i);//读取为基类
				obj.die();//需要大家自己补充
				list.remove(i);
			}
		}
	}
	public void ChangeMap(int mapID){
		isRunning = false;
		this.mapID = mapID;
		try {
			sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
	
}





