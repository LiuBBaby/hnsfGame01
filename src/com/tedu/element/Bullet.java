package com.tedu.element;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;

/**
 * @说明 子弹类
 * @author renjj
 *
 */
public class Bullet extends ElementObj {

	private Direction dir;
	private int speed = 10;
	private boolean live = true;
	
	public Bullet() {
		
	}
	
	public Bullet(int x, int y, Direction dir) {
		super(x, y, 10, 10, null);
		this.dir = dir;
	}
	
	@Override
	public void showElement(Graphics g) {
		if(live) {
			g.setColor(Color.YELLOW);
			g.fillOval(this.getX(), this.getY(), this.getW(), this.getH());
			move();
		}
	}
	
	public void move() {
		switch (dir) {
		case UP:
			this.setY(this.getY()-speed);
			break;
		case DOWN:
			this.setY(this.getY()+speed);
			break;
		case LEFT:
			this.setX(this.getX()-speed);
			break;
		case RIGHT:
			this.setX(this.getX()+speed);
			break;
		}
		
		// 判断边界
		if(this.getX()<0 || this.getX()>800 || this.getY()<0 || this.getY()>600) {
			live = false;
		}
	}

	public Direction getDir() {
		return dir;
	}

	public void setDir(Direction dir) {
		this.dir = dir;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}
	
}