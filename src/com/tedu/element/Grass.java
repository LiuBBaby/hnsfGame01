package com.tedu.element;

import java.awt.Graphics;
import javax.swing.ImageIcon;

/**
 * @说明 草地类
 * @author renjj
 *
 */
public class Grass extends ElementObj {

	public Grass(int x, int y, int w, int h, ImageIcon icon) {
		super(x, y, w, h, icon);
	}

	@Override
	public void showElement(Graphics g) {
		g.drawImage(this.getIcon().getImage(), 
				this.getX(), this.getY(), 
				this.getW(), this.getH(), null);
	}

}