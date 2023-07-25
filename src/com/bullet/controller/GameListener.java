package com.bullet.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.bullet.element.ElementObj;
import com.bullet.manager.ElementManager;
import com.bullet.manager.GameElement;

/**
 * @说明 监听类，用于监听用户的操作 KeyListener
 * @author renjj
 */
public class GameListener implements KeyListener{
	private ElementManager em=ElementManager.getManager();
	private Set<Integer> set=new HashSet<Integer>();
	
	@Override
	public void keyTyped(KeyEvent e) {}

	//按下
	@Override
	public void keyPressed(KeyEvent e) {
		int key=e.getKeyCode();
		if(set.contains(key)) { //判定集合中是否已经存在,包含这个对象
			return;
		}
		set.add(key);
		List<ElementObj> play = em.getElementsByKey(GameElement.PLAY);
		for(ElementObj obj:play) {
			obj.keyClick(true, e.getKeyCode());
		}
	}

	//松开
	@Override
	public void keyReleased(KeyEvent e) {
		if(!set.contains(e.getKeyCode())) {//如果这个不存在，就停止
			return;
		}//存在(已经按过这个按键)
		set.remove(e.getKeyCode());//移除数据
		List<ElementObj> play = em.getElementsByKey(GameElement.PLAY);
		for(ElementObj obj:play) {
			obj.keyClick(false, e.getKeyCode());
		}
	}
}
