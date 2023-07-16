package com.bullet.game;

import com.bullet.controller.GameListener;
import com.bullet.controller.GameThread;
import com.bullet.manager.SoundManager;
import com.bullet.manager.UIElement;
import com.bullet.manager.UIManager;
import com.bullet.view.GameJFrame;
import com.bullet.view.GameMainJPanel;
import com.bullet.view.GameStartPanel;

public class GameStart {
	/**
	 * 程序的唯一入口
	 */
	public static void main(String[] args) {

		UIManager um = UIManager.getManager();

		um.init();

		um.SetPanel(UIElement.Start);
		SoundManager.getManager().PlaySound("res/music/music (13).wav");


	}

}
