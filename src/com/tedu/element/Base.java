package com.tedu.element;

import java.awt.Graphics;
import javax.swing.ImageIcon;

/**
 * 基地类
 */
public class Base extends ElementObj {
    
    public Base(int x, int y, int w, int h, ImageIcon icon) {
        super(x, y, w, h, icon);
    }
    
    @Override
    public void showElement(Graphics g) {
        g.drawImage(this.getIcon().getImage(), 
                this.getX(), this.getY(), 
                this.getW(), this.getH(), null);
    }
}