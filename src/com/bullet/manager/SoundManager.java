package com.bullet.manager;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class SoundManager {
	private static SoundManager SM = null;
	
	public static synchronized SoundManager getManager() {
		if(SM == null) {//控制判定
            SM=new SoundManager();
        }
		return SM;		
	}
	
	public void PlayBGM(String url) {
		Clip audioClip;	
		try {
            File file = new File(url);
            if (file.exists()) {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
                audioClip = AudioSystem.getClip();
                audioClip.open(audioInputStream);
                audioClip.start();
            } else {
                System.out.println("Sound file not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	public void PlaySound(String url) {
		Clip audioClip;
		try {
            File file = new File(url);
            if (file.exists()) {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
                audioClip = AudioSystem.getClip();
                audioClip.open(audioInputStream);
                audioClip.start();
            } else {
                System.out.println("Sound file not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
}
