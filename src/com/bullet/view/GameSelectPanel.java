package com.bullet.view;

import com.bullet.manager.Settings;
import com.bullet.manager.UIElement;
import com.bullet.manager.UIManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameSelectPanel extends JPanel {
    public GameSelectPanel(){
    	ImageIcon background = new ImageIcon("res/images/background/LOGO.png");
    	ImageIcon select = new ImageIcon("res/images/background/Select.png");
    	ImageIcon level1 = new ImageIcon("res/images/background/1.png");
    	ImageIcon level2 = new ImageIcon("res/images/background/2.png");
    	
    	background.setImage(background.getImage().getScaledInstance(Settings.GameX,Settings.GameY,Image.SCALE_DEFAULT));
    	select.setImage(select.getImage().getScaledInstance(300,100,Image.SCALE_DEFAULT));
    	level1.setImage(level1.getImage().getScaledInstance(300,200,Image.SCALE_DEFAULT));
    	level2.setImage(level2.getImage().getScaledInstance(300,200,Image.SCALE_DEFAULT));
    	
    	JLabel label = new JLabel(background);
    	JLabel label2 = new JLabel(select);
        setLayout(null);
        // 创建两个按钮
        JButton button1 = new JButton(level1);
        JButton button2 = new JButton(level2);

        label.setBounds(0,0,Settings.GameX,Settings.GameY);
        label2.setBounds(50,50,300,100);
        button1.setBounds(50,200,300,200);
        button2.setBounds(600,200,300,200);

        // 为按钮添加点击事件监听器
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                UIManager um = UIManager.getManager();
                um.SetPanel(UIElement.Level1);
            }
        });

        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                UIManager um = UIManager.getManager();
                um.SetPanel(UIElement.Level2);
            }
        });

        // 将标签和按钮添加到面板中      
        this.add(button1);
        this.add(button2);
        this.add(label2);
        this.add(label);       

        // 设置窗口大小和可见性
        setSize(400, 300);
        setVisible(true);
    }
}
