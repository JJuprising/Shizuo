package com.bullet.manager;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bullet.element.ElementObj;
import com.bullet.view.GameJFrame;

/**
 * @说明 本类是元素管理器，专门存储所有的元素，同时，提供方法
 * 		给予视图和控制获取数据
 * @author renjj
 * @问题一：存储所有元素数据，怎么存放？ list map set 3大集合
 * @问题二：管理器是视图和控制要访问，管理器就必须只有一个，单例模式
 */
public class ElementManager {
	private Map<GameElement,List<ElementObj>> gameElements;
	public Map<GameElement, List<ElementObj>> getGameElements() {
		return gameElements;
	}

	public void addElement(ElementObj obj,GameElement ge) {
		gameElements.get(ge).add(obj);//添加对象到集合中，按key值就行存储
	}

//	依据key返回 list集合，取出某一类元素
	public List<ElementObj> getElementsByKey(GameElement ge){
		return gameElements.get(ge);
	}
	private static ElementManager EM=null; //引用
//	synchronized线程锁->保证本方法执行中只有一个线程
	public static synchronized ElementManager getManager() {
		if(EM == null) {//控制判定
			EM=new ElementManager();
		}
		return EM;
	}
	private ElementManager() {//私有化构造方法
		init(); //实例化方法
	}

	public void init() {//实例化在这里完成
		gameElements=new HashMap<GameElement,List<ElementObj>>();
		for(GameElement ge:GameElement.values()) {
			gameElements.put(ge,new ArrayList<ElementObj>());
		}
	}

}