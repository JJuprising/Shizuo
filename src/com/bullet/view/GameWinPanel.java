package com.bullet.view;

import com.bullet.manager.GameManager;
import com.bullet.manager.Settings;
import com.bullet.manager.UIElement;
import com.bullet.manager.UIManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameWinPanel extends JPanel {
    public GameWinPanel(){

    	ImageIcon start = new ImageIcon("res/images/background/Start.png");
    	ImageIcon exit = new ImageIcon("res/images/background/Exit.png");
    	
    	start.setImage(start.getImage().getScaledInstance(200,50,Image.SCALE_DEFAULT));
    	exit.setImage(exit.getImage().getScaledInstance(200,50,Image.SCALE_DEFAULT));
    	
        Label label1 = new Label();
        Label label2 = new Label();

        GameManager.getManager().SetLabel2(label1,label2);
        setLayout(null);

        // 创建两个按钮
        JButton button1 = new JButton(start);
        JButton button2 = new JButton(exit);

        label1.setBounds(360,0,500,300);
        label1.setFont(new Font("宋体",Font.BOLD,80));
        label2.setBounds(400,200,300,300);
        label2.setFont(new Font("宋体",Font.BOLD,40));
        button1.setBounds(200,525,200,50);
        button2.setBounds(600,525,200,50);

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
        this.add(label1);
        this.add(label2);

        // 设置窗口大小和可见性
//        setSize(400, 300);
        setVisible(true);
    }
}
