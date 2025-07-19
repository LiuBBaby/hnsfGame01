package com.tedu.controller.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import com.tedu.element.Direction;
import com.tedu.element.ElementObj;
import com.tedu.element.Play;
import com.tedu.element.Bullet;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.view.panel.GameMainPanel;

/**
 * 输入控制器
 */
public class InputController implements KeyListener {
    
    private GameMainPanel gamePanel;
    private ElementManager elementManager;
    
    public InputController(GameMainPanel gamePanel) {
        this.gamePanel = gamePanel;
        this.elementManager = ElementManager.getManager();
        
        // 注册键盘监听器
        gamePanel.addKeyListener(this);
        gamePanel.setFocusable(true);
        gamePanel.requestFocus();
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        List<ElementObj> players = elementManager.getElementsByKey(GameElement.PLAY);
        
        for (ElementObj playerObj : players) {
            if (playerObj instanceof Play) {
                Play player = (Play) playerObj;
                player.keyPressed(e);
                
                // 处理射击
                if (e.getKeyCode() == KeyEvent.VK_SPACE || e.getKeyCode() == KeyEvent.VK_ENTER) {
                    Bullet bullet = player.shoot();
                    if (bullet != null) {
                        elementManager.addElement(bullet, GameElement.BULLET_PLAY);
                    }
                }
            }
        }
        
        // 处理游戏控制键
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                // 暂停游戏
                System.out.println("游戏暂停");
                break;
            case KeyEvent.VK_R:
                // 重新开始
                System.out.println("重新开始游戏");
                break;
        }
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        List<ElementObj> players = elementManager.getElementsByKey(GameElement.PLAY);
        
        for (ElementObj playerObj : players) {
            if (playerObj instanceof Play) {
                Play player = (Play) playerObj;
                player.keyReleased(e);
            }
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        // 不需要实现
    }
    
    /**
     * 处理输入（由游戏循环调用）
     */
    public void handleInput() {
        // 这里可以处理持续按键的逻辑
        // 目前由Play类自己处理
    }
}