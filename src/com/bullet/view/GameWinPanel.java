package com.bullet.view;

import com.bullet.manager.UIElement;
import com.bullet.manager.UIManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameWinPanel extends JPanel {
    public GameWinPanel(){
//        // 创建一个标签，用于显示标题图片
//        JLabel label = new JLabel(new ImageIcon("path/to/image.png"));
        JLabel label = new JLabel("你赢了！");
        setLayout(null);
        // 创建两个按钮
        JButton button1 = new JButton("关卡选择");
        JButton button2 = new JButton("主菜单");

        label.setBounds(600,100,200,50);
        label.setFont(new Font("宋体",Font.BOLD,20));
        button1.setBounds(500,200,200,100);
        button2.setBounds(500,400,200,100);

        // 为按钮添加点击事件监听器
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                UIManager um = UIManager.getManager();
                um.SetPanel(UIElement.Select);
            }
        });

        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                UIManager um = UIManager.getManager();
                um.SetPanel(UIElement.Start);
            }
        });

        // 将标签和按钮添加到面板中
        this.add(label);
        this.add(button1);
        this.add(button2);

        // 设置窗口大小和可见性
        setSize(400, 300);
        setVisible(true);
    }
}
