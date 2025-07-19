package com.tedu.element;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.HashSet;
import java.util.Set;

import javax.swing.ImageIcon;

/**
 * @说明 玩家坦克类
 * @author renjj
 *
 */
public class Play extends Tank {
    
    // 当前按下的按键集合
    private Set<Integer> pressedKeys = new HashSet<>();
    
    public Play() {
        super(200, 300, "play1");
    }
    
    public Play(int x, int y) {
        super(x, y, "play1");
    }
    
    public Play(int x, int y, String playerType) {
        super(x, y, playerType);
    }
    
    /**
     * 处理按键按下事件
     */
    public void keyPressed(KeyEvent e) {
        pressedKeys.add(e.getKeyCode());
        
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                changeDirection(Direction.UP);
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                changeDirection(Direction.DOWN);
                break;
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                changeDirection(Direction.LEFT);
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                changeDirection(Direction.RIGHT);
                break;
        }
    }
    
    /**
     * 处理按键释放事件
     */
    public void keyReleased(KeyEvent e) {
        pressedKeys.remove(e.getKeyCode());
    }
    
    /**
     * 检查是否按下了射击键
     */
    public boolean isShootKeyPressed() {
        return pressedKeys.contains(KeyEvent.VK_SPACE) || 
               pressedKeys.contains(KeyEvent.VK_ENTER);
    }
    
    /**
     * 检查是否有移动键被按下
     */
    public boolean isMoving() {
        return pressedKeys.contains(KeyEvent.VK_UP) ||
               pressedKeys.contains(KeyEvent.VK_DOWN) ||
               pressedKeys.contains(KeyEvent.VK_LEFT) ||
               pressedKeys.contains(KeyEvent.VK_RIGHT) ||
               pressedKeys.contains(KeyEvent.VK_W) ||
               pressedKeys.contains(KeyEvent.VK_S) ||
               pressedKeys.contains(KeyEvent.VK_A) ||
               pressedKeys.contains(KeyEvent.VK_D);
    }
    
    /**
     * 更新玩家坦克状态
     */
    public void update() {
        if (isMoving()) {
            move();
        }
    }
    
    @Override
    public void showElement(Graphics g) {
        super.showElement(g);
        
        // 可以在这里添加玩家坦克的特殊显示效果
        // 比如生命值显示等
    }
    
    /**
     * 重置玩家坦克状态
     */
    public void reset() {
        setX(200);
        setY(300);
        setDirection(Direction.UP);
        setHealth(1);
        setAlive(true);
        pressedKeys.clear();
    }
    
    /**
     * 获取当前按下的按键集合
     */
    public Set<Integer> getPressedKeys() {
        return new HashSet<>(pressedKeys);
    }
}