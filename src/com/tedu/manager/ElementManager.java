package com.tedu.manager;

import java.util.List;
import java.util.Map;
import java.util.Objects;

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
    private Map<String, List<Object>> gameElements;

    public Map<String, List<Object>> getGameElements() {
        return gameElements;
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
    private static ElementManager EM;//本类的实体 引用
    public static ElementManager getManager(){
        return EM;
    }
    private ElementManager(){//私有化构造方法

    }
}
