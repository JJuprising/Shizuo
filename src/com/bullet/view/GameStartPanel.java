package com.bullet.view;

import com.bullet.manager.Settings;
import com.bullet.manager.UIElement;
import com.bullet.manager.UIManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameStartPanel extends JPanel {
    public GameStartPanel(){
//        // 创建一个标签，用于显示标题图片
    	ImageIcon background = new ImageIcon("res/images/background/LOGO.png");
    	ImageIcon start = new ImageIcon("res/images/background/Start.png");
    	ImageIcon exit = new ImageIcon("res/images/background/Exit.png");
    	
    	background.setImage(background.getImage().getScaledInstance(Settings.GameX,Settings.GameY,Image.SCALE_DEFAULT));
    	start.setImage(start.getImage().getScaledInstance(200,50,Image.SCALE_DEFAULT));
    	exit.setImage(exit.getImage().getScaledInstance(200,50,Image.SCALE_DEFAULT));
    	
        JLabel label = new JLabel(background);
        setLayout(null);
        // 创建两个按钮
        JButton button1 = new JButton(start);
        JButton button2 = new JButton(exit);

        label.setBounds(0,0,Settings.GameX,Settings.GameY);
        label.setFont(new Font("宋体",Font.BOLD,20));
        button1.setBounds(200,470,200,50);
        button2.setBounds(600,470,200,50);

        // 为按钮添加点击事件监听器
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                UIManager um = UIManager.getManager();
                um.SetPanel(UIElement.Select);
            }
        });

        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(114514);
            }
        });

        // 将标签和按钮添加到面板中
        
        this.add(button1);
        this.add(button2);
        this.add(label);

        // 设置窗口大小和可见性
//        setSize(400, 300);
        setVisible(true);
    }
}
