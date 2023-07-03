package com.tedu.show;

import com.tedu.element.ElementObj;
import com.tedu.element.Play;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.Set;
import java.util.List;
/**
 * 游戏的主要面板
 * 主要进行元素的显示，同时进行界面的刷新(多线程)
 *
 * java开发首先思考的是：做继承或者是接口实现
 */
public class GameMainJPanel extends JPanel {
    //联动管理器
    private ElementManager em;

    public GameMainJPanel(){
        init();
        //测试代码
        load();
    }
    public void load(){
        //图片导入
        ImageIcon icon=new ImageIcon("image/tank/play1/player1_up.png");
        ElementObj obj=new Play(100,100,50,50,icon);//实例化对象
        //将对象放入到元素管理器中
        //em.getElementsByKey(GameElement.PLAY).add(obj);
        em.addElement(obj,GameElement.PLAY);//直接添加
    }
    public void init(){
        em=ElementManager.getManager();//得到元素管理器对象
    }

    @Override//用于绘画的 Graphics 画笔 专门用于绘画的
    public void paint(Graphics g) {
        super.paint(g);
//        g.setColor(new Color(255,255,255));
//        g.setFont(new Font("微软雅黑",Font.BOLD,48));
//        g.drawString("i love JAVA",200,200);//一定在绘画之前设置颜色和字体
//        //设置的坐标设置的是图形的左上角，而不是中心位置
////        g.fillOval(300,300,100,100);//有填充的圆
////        g.setColor(new Color(200,200,200));
////        g.drawOval(400,400,100,200);//圆圈 椭圆
//        g.fillOval(300,300,100,200);//有填充的圆
//        //前左
//        g.fillOval(275,325,50,25);
//        //前右
//        g.fillOval(375,325,50,25);
//        //后左
//        g.fillOval(275,450,50,25);
//        //后右
//        g.fillOval(375,450,50,25);
        em.getGameElements();//得到所有的key集合
        Map<GameElement,List<ElementObj>> all=em.getGameElements();
        Set<GameElement> set=all.keySet();//得到所有的key集合
        for (GameElement ge:set){//迭代器
            List<ElementObj> list=all.get(ge);
            for(int i=0;i<list.size();i++){
                ElementObj obj=list.get(i);
                obj.showElement(g);//调用每个类的自己的show方法完成自己的显示
            }
        }
    }
}
