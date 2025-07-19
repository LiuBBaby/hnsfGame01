package com.tedu.element;

import java.awt.Graphics;
import javax.swing.ImageIcon;

/**
 * @说明 铁块类
 * @author renjj
 *
 */
public class Iron extends ElementObj {

	public Iron(int x, int y, int w, int h, ImageIcon icon) {
		super(x, y, w, h, icon);
	}

	@Override
	public void showElement(Graphics g) {
		g.drawImage(this.getIcon().getImage(), 
				this.getX(), this.getY(), 
				this.getW(), this.getH(), null);
	}

}