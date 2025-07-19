package com.tedu.view.panel;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.List;

import com.tedu.element.ElementObj;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;

/**
 * 游戏主面板
 */
public class GameMainPanel extends JPanel {
    
    private ElementManager elementManager;
    
    public GameMainPanel() {
        this.elementManager = ElementManager.getManager();
        setBackground(Color.BLACK);
        setFocusable(true);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // 开启反锯齿
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // 绘制所有游戏元素
        drawGameElements(g2d);
        
        // 绘制UI信息
        drawUI(g2d);
    }
    
    /**
     * 绘制游戏元素
     */
    private void drawGameElements(Graphics2D g) {
        // 按照层次绘制元素
        
        // 1. 地形元素（底层）
        drawElementsByType(g, GameElement.RIVER);
        drawElementsByType(g, GameElement.GRASS);
        drawElementsByType(g, GameElement.BRICK);
        drawElementsByType(g, GameElement.IRON);
        drawElementsByType(g, GameElement.BASE);
        
        // 2. 坦克（中层）
        drawElementsByType(g, GameElement.PLAY);
        drawElementsByType(g, GameElement.ENEMY);
        
        // 3. 子弹（顶层）
        drawElementsByType(g, GameElement.BULLET_PLAY);
        drawElementsByType(g, GameElement.BULLET_ENEMY);
    }
    
    /**
     * 绘制指定类型的元素
     */
    private void drawElementsByType(Graphics2D g, GameElement elementType) {
        List<ElementObj> elements = elementManager.getElementsByKey(elementType);
        for (ElementObj element : elements) {
            if (element != null) {
                element.showElement(g);
            }
        }
    }
    
    /**
     * 绘制UI信息
     */
    private void drawUI(Graphics2D g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        
        // 显示游戏信息
        int y = 20;
        
        // 显示敌人数量
        int enemyCount = elementManager.getElementCount(GameElement.ENEMY);
        g.drawString("敌人数量: " + enemyCount, 10, y);
        y += 25;
        
        // 显示玩家生命值
        List<ElementObj> players = elementManager.getElementsByKey(GameElement.PLAY);
        if (!players.isEmpty()) {
            // 这里可以显示玩家的生命值等信息
            g.drawString("玩家状态: 正常", 10, y);
        }
        y += 25;
        
        // 显示控制说明
        g.setFont(new Font("Arial", Font.PLAIN, 12));
        g.setColor(Color.LIGHT_GRAY);
        g.drawString("方向键: 移动  空格: 射击  ESC: 暂停", 10, getHeight() - 10);
    }
    
    /**
     * 绘制游戏结束界面
     */
    public void drawGameOver(Graphics2D g) {
        g.setColor(new Color(0, 0, 0, 150)); // 半透明黑色
        g.fillRect(0, 0, getWidth(), getHeight());
        
        g.setColor(Color.RED);
        g.setFont(new Font("Arial", Font.BOLD, 48));
        String gameOverText = "GAME OVER";
        int textWidth = g.getFontMetrics().stringWidth(gameOverText);
        g.drawString(gameOverText, (getWidth() - textWidth) / 2, getHeight() / 2);
        
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        String restartText = "按 R 重新开始";
        int restartWidth = g.getFontMetrics().stringWidth(restartText);
        g.drawString(restartText, (getWidth() - restartWidth) / 2, getHeight() / 2 + 60);
    }
    
    /**
     * 绘制关卡完成界面
     */
    public void drawLevelComplete(Graphics2D g) {
        g.setColor(new Color(0, 0, 0, 150)); // 半透明黑色
        g.fillRect(0, 0, getWidth(), getHeight());
        
        g.setColor(Color.GREEN);
        g.setFont(new Font("Arial", Font.BOLD, 36));
        String completeText = "LEVEL COMPLETE!";
        int textWidth = g.getFontMetrics().stringWidth(completeText);
        g.drawString(completeText, (getWidth() - textWidth) / 2, getHeight() / 2);
        
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 16));
        String nextText = "按任意键继续";
        int nextWidth = g.getFontMetrics().stringWidth(nextText);
        g.drawString(nextText, (getWidth() - nextWidth) / 2, getHeight() / 2 + 60);
    }
}