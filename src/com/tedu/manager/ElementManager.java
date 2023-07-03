package com.tedu.manager;

import com.tedu.element.ElementObj;

import java.util.*;

/**
 * @说明 本类是元素管理器，专门存储所有的元素，同时，提供方法
 *       给予视图和控制获取数据
 *
 *       管理器只有一个--单例模式
 */
public class ElementManager {
    /*
     * String作为key匹配所有的元素
     *
     * 枚举类型 区分不一样的资源，用于获取资源
     * List中元素的泛型应该是元素的基类
     * 所有元素都可以存放到map集合中，显示模块只需要获取到map就可以
     * 显示所有界面需要显示的元素(调用元素的基类showElement;
     */
    private Map<GameElement, List<ElementObj>> gameElements;

    //视图读取
    public Map<GameElement, List<ElementObj>> getGameElements() {
        return gameElements;
    }
    //添加元素(多半由加载器调用)
    public void addElement(ElementObj obj,GameElement ge){
        gameElements.get(ge).add(obj);//添加对象到集合中，按key值进行存储
    }
    //依据key返回list集合，取出某一类元素
    public List<ElementObj> getElementsByKey(GameElement ge){
        return gameElements.get(ge);
    }
    /**
     * 单例模式：内存中有且只有一个实例
     * 饿汉模式-启动自动加载实例
     * 饱汉模式-需要使用的时候才加载实例
     *
     * 编写方式：
     * 1.需要一个静态的属性 单例的引用
     * 2.提供一个静态的方法(返回这个实例) return单例的引用
     * 3.防止别人使用，私有化构造方法
     */
    private static ElementManager EM=null;//本类的实体 引用
    //synchronized线程锁->保证本方法执行中只有一个线程
    public static synchronized ElementManager getManager(){
        if(EM==null){
            //空值判定
            EM=new ElementManager();
        }
        return EM;
    }
    private ElementManager(){//私有化构造方法
        init();//构造方法没办法重写，因此要“重写”构造直接重写init()就可以
    }
    public void init(){
        //实例化对象
        gameElements=new HashMap<GameElement,List<ElementObj>>();
        //将每种元素集合都加入到map中
        gameElements.put(GameElement.PLAY,new ArrayList<ElementObj>());//玩家
        gameElements.put(GameElement.MAPS,new ArrayList<ElementObj>());//地图
        gameElements.put(GameElement.ENEMY,new ArrayList<ElementObj>());//敌人
        gameElements.put(GameElement.BOSS,new ArrayList<ElementObj>());//boss
    }
}
