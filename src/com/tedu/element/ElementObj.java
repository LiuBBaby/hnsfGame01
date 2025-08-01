package com.tedu.element;

import java.awt.Graphics;

import javax.swing.ImageIcon;

/**
 * @说明 所有元素的基类。
 * @author renjj
 *
 */
public abstract class ElementObj {

	private int x;
	private int y;
	private int w;
	private int h;
	private ImageIcon icon;
//	还有。。。。 各种必要的状态值，例如：是否生存.
	
	public ElementObj() {	//这个构造其实没有作用，只是为继承的时候不报错写的	
	}
	/**
	 * @说明 带参数的构造方法; 可以由子类传输数据到父类
	 * @param x    左上角X坐标
	 * @param y    左上角y坐标
	 * @param w    w宽度
	 * @param h    h高度
	 * @param icon  图片
	 */
	public ElementObj(int x, int y, int w, int h, ImageIcon icon) {
		super();
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.icon = icon;
	}
	/**
	 * @说明 抽象方法，显示元素
	 * @param g  画笔 用于进行绘画
	 */
	public abstract void showElement(Graphics g);
	
	
	/**
	 * 只要是 VO类 POJO 就要为属性生成 get和set方法
	 */
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getW() {
		return w;
	}
	public void setW(int w) {
		this.w = w;
	}
	public int getH() {
		return h;
	}
	public void setH(int h) {
		this.h = h;
	}
	public ImageIcon getIcon() {
		return icon;
	}
	public void setIcon(ImageIcon icon) {
		this.icon = icon;
	}
	
	/**
	 * @说明 获取元素的中心点X坐标
	 * @return
	 */
	public int getCenterX() {
		return x+w/2;
	}
	/**
	 * @说明 获取元素的中心点Y坐标
	 * @return
	 */
	public int getCenterY() {
		return y+h/2;
	}
	
	/**
	 * @说明 判断两个元素是否碰撞
	 * @param obj
	 * @return
	 */
	public boolean isCollision(ElementObj obj) {
		int x1 = this.x;
		int y1 = this.y;
		int w1 = this.w;
		int h1 = this.h;
		
		int x2 = obj.x;
		int y2 = obj.y;
		int w2 = obj.w;
		int h2 = obj.h;
		
		if(x1>=x2 && x1>=x2+w2) {
			return false;
		}
		if(x1+w1<=x2) {
			return false;
		}
		if(y1>=y2+h2) {
			return false;
		}
		if(y1+h1<=y2) {
			return false;
		}
		return true;
	}
	
}