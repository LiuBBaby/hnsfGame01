package com.tedu.manager;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

/**
 * 输入管理器
 */
public class InputManager implements KeyListener {
    
    private Set<Integer> pressedKeys = new HashSet<>();
    private Set<Integer> justPressedKeys = new HashSet<>();
    private Set<Integer> justReleasedKeys = new HashSet<>();
    
    /**
     * 更新输入状态（每帧调用）
     */
    public void update() {
        justPressedKeys.clear();
        justReleasedKeys.clear();
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (!pressedKeys.contains(keyCode)) {
            justPressedKeys.add(keyCode);
        }
        pressedKeys.add(keyCode);
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        pressedKeys.remove(keyCode);
        justReleasedKeys.add(keyCode);
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        // 不需要实现
    }
    
    /**
     * 检查按键是否正在被按下
     */
    public boolean isKeyPressed(int keyCode) {
        return pressedKeys.contains(keyCode);
    }
    
    /**
     * 检查按键是否刚刚被按下
     */
    public boolean isKeyJustPressed(int keyCode) {
        return justPressedKeys.contains(keyCode);
    }
    
    /**
     * 检查按键是否刚刚被释放
     */
    public boolean isKeyJustReleased(int keyCode) {
        return justReleasedKeys.contains(keyCode);
    }
    
    /**
     * 检查移动键是否被按下
     */
    public boolean isMovementKeyPressed() {
        return isKeyPressed(KeyEvent.VK_UP) ||
               isKeyPressed(KeyEvent.VK_DOWN) ||
               isKeyPressed(KeyEvent.VK_LEFT) ||
               isKeyPressed(KeyEvent.VK_RIGHT) ||
               isKeyPressed(KeyEvent.VK_W) ||
               isKeyPressed(KeyEvent.VK_S) ||
               isKeyPressed(KeyEvent.VK_A) ||
               isKeyPressed(KeyEvent.VK_D);
    }
    
    /**
     * 检查射击键是否被按下
     */
    public boolean isShootKeyPressed() {
        return isKeyPressed(KeyEvent.VK_SPACE) ||
               isKeyPressed(KeyEvent.VK_ENTER);
    }
    
    /**
     * 检查射击键是否刚刚被按下
     */
    public boolean isShootKeyJustPressed() {
        return isKeyJustPressed(KeyEvent.VK_SPACE) ||
               isKeyJustPressed(KeyEvent.VK_ENTER);
    }
    
    /**
     * 棆查暂停键是否刚刚被按下
     */
    public boolean isPauseKeyJustPressed() {
        return isKeyJustPressed(KeyEvent.VK_ESCAPE) ||
               isKeyJustPressed(KeyEvent.VK_P);
    }
    
    /**
     * 清空所有输入状态
     */
    public void clear() {
        pressedKeys.clear();
        justPressedKeys.clear();
        justReleasedKeys.clear();
    }
    
    /**
     * 获取当前按下的所有按键
     */
    public Set<Integer> getPressedKeys() {
        return new HashSet<>(pressedKeys);
    }
}