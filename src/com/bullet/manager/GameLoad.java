package com.bullet.manager;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import javax.swing.ImageIcon;
import com.bullet.element.ElementObj;

//提供 static方法来对整个游戏进行加载
public class GameLoad {
	//资源管理器
	private static ElementManager em=ElementManager.getManager();
	//使用Map来存储图片资源
	public static Map<String,ImageIcon> imgMap = new HashMap<>();
	//用户读取文件的类
	private static Properties pro =new Properties();

//  动画片段集合  使用map来进行存储
	public static Map<String, ArrayList<String>> aniMap = new HashMap<>();


	/**
	 * @说明 传入地图id有加载方法依据文件规则自动产生地图文件名称，加载文件
	 * @param mapId  文件编号 文件id
	 */
	public static void MapLoad(int mapId) {
		String mapName="com/bullet/data/"+mapId+".map";
		ClassLoader classLoader = GameLoad.class.getClassLoader();
		InputStream maps = classLoader.getResourceAsStream(mapName);
		if(maps == null) {
			System.out.println("配置文件读取异常,请重新安装");
			return;
		}
		try {
			pro.clear();
			pro.load(maps);
//			可以直接动态的获取所有的key，有key就可以获取 value
			Enumeration<?> names = pro.propertyNames();
//			获取是无序的
			while(names.hasMoreElements()) {
				String key=names.nextElement().toString();
				//System.out.println(pro.getProperty(key));//得到key对应的value

				String [] arrs=pro.getProperty(key).split(";");//是否需要删除？
				//System.out.println(arrs.length);

				for(int i=0;i<arrs.length;i++) {
					ElementObj obj = getObj("mapobj");
					ElementObj map = obj.createElement(key+","+arrs[i]);
					if(key.equals("BACKGROUND")){
						map.setH(Settings.GameY);
						em.addElement(map, GameElement.BACKGROUND);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//加载图片资源，可以在loadImg()中添加参数来为不同的关卡加载图片资源，因此texturl的命名方式就根据关卡类似于String mapName="com/bullet/data/"+mapId+".map";
	//存储数据的是imgMap，imgMap的key是名字，value是路径
	public static Map<String,ImageIcon> EnemyImgMap = new HashMap<>();
	public static void loadImg() {
		String texturl="com/bullet/data/GameData.pro";
		ClassLoader classLoader = GameLoad.class.getClassLoader();
		InputStream texts = classLoader.getResourceAsStream(texturl);
		pro.clear();
		try {
			pro.load(texts);
			Set<Object> set = pro.keySet();//是一个set集合
			for(Object o:set) {
				String url=pro.getProperty(o.toString());
				imgMap.put(o.toString(), new ImageIcon(url));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		//由于格式不统一，把敌人和主角的图片位置分开放
		String enemyurl= "com/bullet/data/EnemyImage";
		ClassLoader classLoader1 = GameLoad.class.getClassLoader();
		InputStream img = classLoader1.getResourceAsStream(enemyurl);
		pro.clear();
		try {
			pro.load(img);
			Set<Object> set = pro.keySet();//是一个set集合
			for(Object o:set) {
				String url=pro.getProperty(o.toString());
				EnemyImgMap.put(o.toString(), new ImageIcon(url));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 *@说明 加载动画片段
	 */
	public static void loadAni() {
//		得到我们的文件路径
		String aniName="com/bullet/data/animation.pro";
//		使用io流来获取文件对象   得到类加载器
		ClassLoader classLoader = GameLoad.class.getClassLoader();
		InputStream ani = classLoader.getResourceAsStream(aniName);
		if(ani ==null) {
			System.out.println("ani配置文件读取异常,请重新安装");
			return;
		}
		try {
//			以后用的 都是 xml 和 json
			pro.clear();
			pro.load(ani);
//			可以直接动态的获取所有的key，有key就可以获取 value
//			java学习中最好的老师 是 java的API文档。
			Enumeration<?> names = pro.propertyNames();
			while(names.hasMoreElements()) {//获取是无序的
//				这样的迭代都有一个问题：一次迭代一个元素。
				String key=names.nextElement().toString();
//				System.out.println(pro.getProperty(key));
//				就可以自动的创建和加载 我们的地图啦
				String [] arrs=pro.getProperty(key).split(";");
//				System.out.println(key);
				ArrayList<String> stringList = new ArrayList<String>();
				for(int i=0;i<arrs.length;i++) {
					stringList.add(arrs[i]);
				}
				aniMap.put(key,stringList);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 加载玩家
	 */
	public static void loadPlay() {
		String playStr="100,400,right";
		ElementObj obj=getObj("play");
		ElementObj play = obj.createElement(playStr);

		//加载飞机机身和机翼

		ElementObj planeObj=getObj("plane");
		ElementObj planeBody = planeObj.createElement("BODY"); //机身


		//机翼
		ElementObj wingObj1=getObj("wing");
		ElementObj font_wing=wingObj1.createElement("FRONT_WING");
		ElementObj wingObj2=getObj("wing");
		ElementObj back_wing=wingObj2.createElement("BACK_WING");

		//

		String footStr="100,445,RIGHT_STAND";
		ElementObj obj2=getObj("playerfoot");
		ElementObj foot = obj2.createElement(footStr);

		em.addElement(play, GameElement.PLAY);
		em.addElement(foot, GameElement.PLAY);
		em.addElement(planeBody, GameElement.PLANE);
		em.addElement(font_wing,GameElement.PLANE);
		em.addElement(back_wing,GameElement.PLANE);
	}

	//加载鬼子
	public static void loadJanpanese(){
		Random random = new Random();
		int randomInRange = random.nextInt(4)+3;
		ElementObj obj = getObj("enemy");
		for(int i=1;i<=randomInRange;i++){
			int randomLocation = random.nextInt(2);//0代表Left，1代表Right
			if (randomLocation == 0){
				ElementObj enemy = obj.createElement("Left");
				em.addElement(enemy,GameElement.ENEMY);
			}
			if (randomLocation == 1){
				ElementObj enemy = obj.createElement("Right");
				em.addElement(enemy,GameElement.ENEMY);
			}
		}
	}
	
	//加载人质
	public static void loadHostage() {
		ElementObj obj = getObj("hostage");
		ElementObj hostage = obj.createElement("");
		em.addElement(hostage, GameElement.HOSTAGE);
	}
	//要通过这种方式实例化，必须先在obj.pro中给定类的key以及对应的value，我们只需要传入str，便可以创建对象
	public static ElementObj getObj(String str) {
		try {
			Class<?> class1 = objMap.get(str);
			Object newInstance = class1.newInstance();
			if(newInstance instanceof ElementObj) {
				return (ElementObj)newInstance;
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	//这里是使用getObj函数的内部实现。将字符串对应相应的类，从而可以使用配置文件中固定的str（key）来实例化。
	private static Map<String,Class<?>> objMap=new HashMap<>();
	public static void loadObj() {
		String objurl="com/bullet/data/obj.pro";
		ClassLoader classLoader = GameLoad.class.getClassLoader();
		InputStream texts = classLoader.getResourceAsStream(objurl);
		pro.clear();
		try {
			pro.load(texts);
			Set<Object> set = pro.keySet();//key的set集合
			for(Object o:set) {
				String classUrl=pro.getProperty(o.toString());
				//使用反射直接获取类
				Class<?> forName = Class.forName(classUrl);
				objMap.put(o.toString(), forName);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
