package com.bullet.element;

import com.bullet.manager.GameLoad;
import com.bullet.manager.Settings;
import com.bullet.view.Animation;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PlayerFoot extends ElementObj{
    /**
     * 移动属性:
     * 1.单属性  配合  方向枚举类型使用; 一次只能移动一个方向
     * 2.双属性  上下 和 左右    配合boolean值使用 例如： true代表上 false 为下 不动？？
     *                      需要另外一个变来确定是否按下方向键
     *                约定    0 代表不动   1代表上   2代表下
     * 3.4属性  上下左右都可以  boolean配合使用  true 代表移动 false 不移动
     * 						同时按上和下 怎么办？后按的会重置先按的
     * 说明：以上3种方式 是代码编写和判定方式 不一样
     * 说明：游戏中非常多的判定，建议灵活使用 判定属性；很多状态值也使用判定属性
     *      多状态 可以使用map<泛型,boolean>;set<判定对象> 判定对象中有时间
     *
     * @问题 1.图片要读取到内存中： 加载器  临时处理方式，手动编写存储到内存中
     *       2.什么时候进行修改图片(因为图片是在父类中的属性存储)
     *       3.图片应该使用什么集合进行存储
     */
    private boolean left=false; //左
    private boolean up=false;   //上
    private boolean right=false;//右
    private boolean down=false; //下

    private boolean isRight = true;
    private boolean isMoving = false;

    Animation animation;

    ArrayList<String> leftRunAnimationList = new ArrayList<String>(){
        {
            add("LEFT_RUN_0");
            add("LEFT_RUN_1");
            add("LEFT_RUN_2");
            add("LEFT_RUN_3");
            add("LEFT_RUN_4");
            add("LEFT_RUN_5");
            add("LEFT_RUN_6");
            add("LEFT_RUN_7");
            add("LEFT_RUN_8");

        }
    };
    ArrayList<String> rightRunAnimationList = new ArrayList<String>(){
        {
            add("RIGHT_RUN_0");
            add("RIGHT_RUN_1");
            add("RIGHT_RUN_2");
            add("RIGHT_RUN_3");
            add("RIGHT_RUN_4");
            add("RIGHT_RUN_5");
            add("RIGHT_RUN_6");
            add("RIGHT_RUN_7");
            add("RIGHT_RUN_8");

        }
    };


    //	变量专门用来记录当前主角面向的方向,默认为是up
    private String fx="RIGHT_STAND";
    public PlayerFoot() {
    }
    public PlayerFoot(int x, int y, int w, int h, ImageIcon icon) {
        super(x, y, w, h, icon);
    }
    //题外话: 过时的方法能用吗？ 可以用，也能够用，因为你不用jdk底层使用
    @Override
    public ElementObj createElement(String str) {
        String[] split = str.split(",");
        this.setX(Integer.parseInt(split[0]));
        this.setY(Integer.parseInt(split[1]));
        ImageIcon icon2 = GameLoad.imgMap.get(split[2]);

        this.setW(icon2.getIconWidth());
        this.setH(icon2.getIconHeight());

        this.setIcon(icon2);
        
        animation = new Animation(4);
        return this;
    }


    /**
     * 面向对象中第1个思想： 对象自己的事情自己做
     */
    @Override
    public void showElement(Graphics g) {
//		绘画图片
        g.drawImage(this.getIcon().getImage(),
                isRight?this.getX():this.getX()+50, this.getY(),
                this.getW(), this.getH(), null);

    }
    /*
     * @说明 重写方法： 重写的要求：方法名称 和参数类型序列 必须和父类的方法一样
     * @重点 监听的数据需要改变状态值
     */
    @Override   // 注解 通过反射机制，为类或者方法或者属性 添加的注释(相当于身份证判定)
    public void keyClick(boolean bl,int key) { //只有按下或者鬆開才會 调用这个方法
//		System.out.println("测试："+key);
        if(bl) {//按下
            switch(key) {  //怎么优化 大家中午思考;加 监听会持续触发；有没办法触发一次
                case 37:
                    isMoving = true;
                    this.down=false;this.up=false;
                    this.right=false;this.left=true;
                    isRight = false;
                    this.fx="LEFT_STAND";
                    break;
                case 38:
                    isMoving = true;
                    this.right=false;this.left=false;
                    this.down=false; this.up=true;

                    break;
                case 39:
                    isMoving = true;
                    this.down=false;this.up=false;
                    this.left=false; this.right=true;
                    isRight = true;
                    this.fx="RIGHT_STAND";
                    break;
                case 40:
                    isMoving = true;
                    this.right=false;this.left=false;
                    this.up=false; this.down=true;
				break;
            }
        }else {
            isMoving = false;
            switch(key) {
                case 37: this.left=false;  break;
                case 38: this.up=false;    break;
                case 39: this.right=false; break;
                case 40: this.down=false;  break;
            }

        }
    }


    //	@Override
//	public int compareTo(Play o) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
    @Override
    public void move() {
        if (this.left && this.getX()>0) {
            this.setX(this.getX() - Settings.playerSpeed);
        }
        if (this.up  && this.getY()>Settings.GameY-Settings.FloorHeight+Settings.playerBodyHeight) {
            this.setY(this.getY() - Settings.playerSpeed);
        }
        if (this.right && this.getX()<Settings.GameX-this.getW()) {  //坐标的跳转由大家来完成
            this.setX(this.getX() + Settings.playerSpeed);
        }
        if (this.down && this.getY()<Settings.GameY-Settings.playerFootHeight) {
            this.setY(this.getY() + Settings.playerSpeed);
        }
    }
    @Override
    protected void updateImage(long gameTime) {
//		ImageIcon icon=GameLoad.imgMap.get(fx);
//		System.out.println(icon.getIconHeight());//得到图片的高度
//		如果高度是小于等于0 就说明你的这个图片路径有问题

        if(isMoving){
            animation.SetAnimation(isRight?rightRunAnimationList:leftRunAnimationList);
            this.setIcon(animation.LoadSprite(gameTime));
        }else{
            animation.ResetAnimation();
            this.setIcon(GameLoad.imgMap.get(fx));
        }
    }


    @Override
    public String toString() {// 这里是偷懒，直接使用toString；建议自己定义一个方法
        //  {X:3,y:5,f:up,t:A} json格式
        int x=this.getX();
        int y=this.getY();
        switch(this.fx) { // 子弹在发射时候就已经给予固定的轨迹。可以加上目标，修改json格式
            case "up": x+=50;break;
            // 一般不会写20等数值，一般情况下 图片大小就是显示大小；一般情况下可以使用图片大小参与运算
            case "left": y+=20;break;
            case "right": x+=80;y+=20;break;
            case "down": y+=50;x+=50; break;
        }//个人认为： 玩游戏有助于 理解面向对象思想;不能专门玩，需要思考，父类应该怎么抽象，子类应该怎么实现
//		学习技术不犯法，但是不要用技术做犯法的事.
        return "x:"+x+",y:"+y+",f:"+this.fx;
    }
}
