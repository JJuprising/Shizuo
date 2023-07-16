package com.bullet.controller;

import java.util.List;
import java.util.Map;
import com.bullet.element.ElementObj;
import com.bullet.manager.*;


//主线程
public class GameThread extends Thread{
	private ElementManager em;
	private GameManager gm;
	private SoundManager sm;

	private int mapID;
	
	public GameThread(int mapID) {
		em=ElementManager.getManager();
		gm = GameManager.getManager();
		sm = SoundManager.getManager();
		this.mapID = mapID;
	}

	@Override
	public void run() {
		while(true) {
			if(gm.IsGameRunning()){
				gameLoad(mapID);
				gameRun();
				gameOver();
			}
			try {
				sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	//游戏加载
	private void gameLoad(int MapID) {
		GameLoad.loadImg(); //加载图片
		GameLoad.loadAni();//加载动画
		GameLoad.loadObj();//加载类列表
		GameLoad.loadMap(MapID);//加载地图
//		GameLoad.loadPlayer();//加载主角
//		GameLoad.loadPlane();//加载飞机
//		GameLoad.loadBoss();//加载boss
//		GameLoad.loadJapanese();//加载敌人
//		GameLoad.loadHostage();//加载人质

//		//加载敌人NPC等
//		GameLoad.loadEnemy();
//		}
//		//全部加载完成，游戏启动
		gm.setGameStates(true);
	}

//	自动化玩家的移动、碰撞、死亡，NPC死亡掉落
	private void gameRun() {
		long gameTime=0L;
		while(GameManager.IsGameRunning()) {
			gameTime++;//唯一的时间控制
			GameManager.gameTime = gameTime;
			Map<GameElement, List<ElementObj>> all = em.getGameElements();
			List<ElementObj> players = em.getElementsByKey(GameElement.PLAY);
			List<ElementObj> enemys = em.getElementsByKey(GameElement.ENEMY);
			List<ElementObj> bullets = em.getElementsByKey(GameElement.BULLET);
			List<ElementObj> hostages = em.getElementsByKey(GameElement.HOSTAGE);
			List<ElementObj> plane = em.getElementsByKey(GameElement.PLANE);
			List<ElementObj> kits = em.getElementsByKey(GameElement.KIT);
			List<ElementObj> canons = em.getElementsByKey(GameElement.CANONFILE);
			List<ElementObj> canonenemys = em.getElementsByKey(GameElement.ENEMYCANON);
			List<ElementObj> boss = em.getElementsByKey(GameElement.BOSS);
			List<ElementObj> enemybullet = em.getElementsByKey(GameElement.ENEMYFILE);
//			List<ElementObj> maps = em.getElementsByKey(GameElement.MAPS);
			moveAndUpdate(all,gameTime);//	游戏元素自动化方法
//			System.out.println(gameTime);
			EnemyPK(canonenemys,bullets);
			EnemyPK(enemys,bullets);
			PlanePK(plane,bullets);
			BossPK(boss,bullets);
			CanonPK(canons,players);//炮弹碰到主角，炮弹触发动画
			HostagePK(hostages,players);
			ElementPK(kits,players);
			BulletPK(enemybullet,players);
			BulletPK(canons,players);
			if(GetEnemyCount()<1&&GameManager.IsGameRunning()){
				gm.EndGame();
				break;
			}
			try {
				sleep(Settings.RefreshSpeed);//1秒刷新100次
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void ElementPK(List<ElementObj> listA,List<ElementObj>listB) {
		for(int i=0;i<listA.size();i++) {
			ElementObj emo1=listA.get(i);
			for(int j=0;j<listB.size();j++) {
				ElementObj emo2=listB.get(j);
				if(emo1.pk(emo2)) {
					emo1.setLive(false);
					break;
				}
			}
		}
	}

	public void EnemyPK(List<ElementObj> listA,List<ElementObj>listB) {
		for(int i=0;i<listA.size();i++) {
			ElementObj enemy=listA.get(i);
			for(int j=0;j<listB.size();j++) {
				ElementObj file=listB.get(j);
				if(enemy.pk(file)) {
					enemy.setLive(false);
					file.setLive(false);
					break;
				}
			}
		}
	}

	public void PlanePK(List<ElementObj> listA,List<ElementObj>listB) {
		boolean isHit = false;
		for(int i=0;i<listB.size();i++) {
			ElementObj file=listB.get(i);
			for(int j=0;j<listA.size();j++) {
				ElementObj enemy=listA.get(j);
				if(file.pk(enemy)) {
					file.setLive(false);
					enemy.setLive(false);
					isHit = true;
					break;
				}
			}
			if(isHit){
				for(int j=0;j<listA.size();j++) {
					ElementObj enemy=listA.get(j);
					enemy.setLive(false);
				}
				break;
			}
		}
	}

	public void BossPK(List<ElementObj> listA,List<ElementObj>listB) {
		for(int i=0;i<listA.size();i++) {
			ElementObj enemy=listA.get(i);
			for(int j=0;j<listB.size();j++) {
				ElementObj file=listB.get(j);
				if(enemy.pk(file)) {
					file.setLive(false);
					if(gm.getBossHp()>0){

						gm.setBossHp(-1);
					}else{
						enemy.setLive(false);
					}
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

	public void BulletPK(List<ElementObj> listA,List<ElementObj>listB) {
		for(int i=0;i<listA.size();i++) {
			ElementObj bullet=listA.get(i);
			for(int j=0;j<listB.size();j++) {
				ElementObj player=listB.get(j);
				if(bullet.pk(player)) {
					GameManager.HostageCrash = true;
					bullet.setLive(false);
					gm.setHp(-1);
				}
			}
		}
	}
	public void CanonPK(List<ElementObj> listA,List<ElementObj>listB) {
		for(int i=0;i<listA.size();i++) {
			ElementObj bullet=listA.get(i);
			for(int j=0;j<listB.size();j++) {
				ElementObj player=listB.get(j);
				if(bullet.pk(player)) {
					GameManager.HostageCrash = true;
					bullet.setLive(false);
					gm.setHp(-2);
				}
			}
		}
	}
	

//	游戏元素自动化方法
	public void moveAndUpdate(Map<GameElement, List<ElementObj>> all,long gameTime) {
		for(GameElement ge:GameElement.values()) {
			List<ElementObj> list = all.get(ge);
			for(int i=list.size()-1;i>=0;i--){	
				ElementObj obj=list.get(i);//读取为基类
				if(!obj.isLive()) {//如果死亡
					obj.die();
					list.remove(i);
					continue;
				}
				obj.model(gameTime);
			}
//			if(ge==GameElement.ENEMY){
//				if(list.size()==0){
//					gm.StopGame();//赢！
//				}
//			}
		}	
	}

// 	游戏切换关卡
	private void gameOver() {
		System.out.println("End");
		if(!GameManager.IsGameRunning()){
			Map<GameElement, List<ElementObj>> all = em.getGameElements();
			for(GameElement ge:GameElement.values()) {
				List<ElementObj> list = all.get(ge);
				for(int i=list.size()-1;i>=0;i--){
					ElementObj obj=list.get(i);//读取为基类
					obj.die();//死亡时创建死亡实例
					list.remove(i);
				}
			}
		}
	}

	public void ChangeMap(int mapID){
		gm.StopGame();
		this.mapID = mapID;
		gm.setMapID(mapID);
		gm.StartGame();
		try {
			sleep(50);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public int GetEnemyCount(){
		ElementManager em = ElementManager.getManager();

		List<ElementObj> enemys = em.getElementsByKey(GameElement.ENEMY);
		List<ElementObj> plane = em.getElementsByKey(GameElement.PLANE);
		List<ElementObj> boss = em.getElementsByKey(GameElement.BOSS);
		List<ElementObj> hostage = em.getElementsByKey(GameElement.HOSTAGE);
		List<ElementObj> canon = em.getElementsByKey(GameElement.ENEMYCANON);

		int count = enemys.size()+plane.size()+boss.size()+hostage.size()+ canon.size();
//		System.out.println(count);
		return count;
	}
}