package com.tedu.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tedu.element.ElementObj;

/**
 * @说明 本类是元素管理器，专门存储所有的元素，同时，提供方法
 * 		给予视图和控制获取数据
 * @author renjj
 * @问题一：存储所有元素数据，怎么存放？ list map set 3大集合
 * @问题二：管理器是视图和控制要访问，管理器就必须只有一个，单例模式
 */
public class ElementManager {

	private Map<GameElement,List<ElementObj>> gameElements;	
//	本方法一定不够用
	public Map<GameElement, List<ElementObj>> getGameElements() {
		return gameElements;
	}
//	添加元素(多半由加载器调用)
	public void addElement(ElementObj obj,GameElement ge) {
//		List<ElementObj> list = gameElements.get(ge);
//		list.add(obj);
		gameElements.get(ge).add(obj);//添加对象到集合中，按key值就行存储
	}
//	依据key返回 list集合，取出某一类元素
	public List<ElementObj> getElementsByKey(GameElement ge){
		return gameElements.get(ge);
	}	

	private static ElementManager EM=null; //引用
//	synchronized线程锁->保证本方法执行中只有一个线程
	public static synchronized ElementManager getManager() {
		if(EM == null) {//控制判定
			EM=new ElementManager();
		}
		return EM;
	}
	private ElementManager() {//私有化构造方法
		init(); //实例化方法
	}

	/**
	 * 本方法是为 将来可能出现的功能扩展，重写init方法准备的。
	 */
	public void init() {//实例化在这里完成
		gameElements = new HashMap<GameElement, List<ElementObj>>();
		//为每一个枚举对象创建一个集合
		for(GameElement ge:GameElement.values()) {
			gameElements.put(ge, new ArrayList<ElementObj>());
		}
	}
	
	/**
	 * 清空所有元素
	 */
	public void clearAll() {
		for(List<ElementObj> list : gameElements.values()) {
			list.clear();
		}
	}
	
	/**
	 * 清空指定类型的元素
	 */
	public void clearElementsByType(GameElement ge) {
		gameElements.get(ge).clear();
	}
	
	/**
	 * 移除指定元素
	 */
	public void removeElement(ElementObj obj, GameElement ge) {
		gameElements.get(ge).remove(obj);
	}
	
	/**
	 * 获取指定类型元素的数量
	 */
	public int getElementCount(GameElement ge) {
		return gameElements.get(ge).size();
	}
	
	/**
	 * 检查指定类型的元素是否为空
	 */
	public boolean isEmpty(GameElement ge) {
		return gameElements.get(ge).isEmpty();
	}
}