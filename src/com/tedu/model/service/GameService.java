package com.tedu.model.service;

import java.util.List;
import java.util.Iterator;

import com.tedu.element.*;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.MapLoader;
import com.tedu.model.data.LevelData;

/**
 * 游戏服务类 - 处理游戏业务逻辑
 */
public class GameService {
    private static GameService instance;
    private ElementManager elementManager;
    private LevelData currentLevel;
    private boolean gameRunning = false;
    private boolean gameOver = false;
    private boolean levelComplete = false;
    private int score = 0;
    private int lives = 3;
    
    private GameService() {
        this.elementManager = ElementManager.getManager();
        this.currentLevel = new LevelData();
    }
    
    public static GameService getInstance() {
        if (instance == null) {
            instance = new GameService();
        }
        return instance;
    }
    
    /**
     * 开始游戏
     */
    public void startGame() {
        gameRunning = true;
        gameOver = false;
        levelComplete = false;
        
        // 清空所有元素
        elementManager.clearAll();
        
        // 加载当前关卡
        loadCurrentLevel();
        
        System.out.println("游戏开始 - 关卡 " + currentLevel.getCurrentLevel());
    }
    
    /**
     * 停止游戏
     */
    public void stopGame() {
        gameRunning = false;
        elementManager.clearAll();
        System.out.println("游戏停止");
    }
    
    /**
     * 重新开始游戏
     */
    public void restartGame() {
        score = 0;
        lives = 3;
        currentLevel.setCurrentLevel(1);
        startGame();
    }
    
    /**
     * 加载当前关卡
     */
    private void loadCurrentLevel() {
        try {
            MapLoader.loadMap(currentLevel.getCurrentLevel());
        } catch (Exception e) {
            System.err.println("加载关卡失败，使用默认地图: " + e.getMessage());
            MapLoader.loadDefaultMap();
        }
    }
    
    /**
     * 下一关
     */
    public void nextLevel() {
        currentLevel.nextLevel();
        levelComplete = false;
        
        if (currentLevel.getCurrentLevel() > currentLevel.getMaxLevel()) {
            // 游戏全部通关
            gameOver = true;
            System.out.println("恐喜您！游戏全部通关！");
        } else {
            // 加载下一关
            elementManager.clearAll();
            loadCurrentLevel();
            System.out.println("进入关卡 " + currentLevel.getCurrentLevel());
        }
    }
    
    /**
     * 更新敌人
     */
    public void updateEnemies() {
        List<ElementObj> enemies = elementManager.getElementsByKey(GameElement.ENEMY);
        
        for (ElementObj enemyObj : enemies) {
            if (enemyObj instanceof Enemy) {
                Enemy enemy = (Enemy) enemyObj;
                
                // 更新AI
                enemy.updateAI();
                
                // 尝试射击
                Bullet bullet = enemy.attemptShoot();
                if (bullet != null) {
                    elementManager.addElement(bullet, GameElement.BULLET_ENEMY);
                }
            }
        }
        
        // 检查是否所有敌人都被消灭
        if (enemies.isEmpty()) {
            levelComplete = true;
        }
    }
    
    /**
     * 更新子弹
     */
    public void updateBullets() {
        updateBulletsByType(GameElement.BULLET_PLAY);
        updateBulletsByType(GameElement.BULLET_ENEMY);
    }
    
    /**
     * 更新指定类型的子弹
     */
    private void updateBulletsByType(GameElement bulletType) {
        List<ElementObj> bullets = elementManager.getElementsByKey(bulletType);
        Iterator<ElementObj> iterator = bullets.iterator();
        
        while (iterator.hasNext()) {
            ElementObj bulletObj = iterator.next();
            if (bulletObj instanceof Bullet) {
                Bullet bullet = (Bullet) bulletObj;
                if (!bullet.isLive()) {
                    iterator.remove();
                }
            }
        }
    }
    
    /**
     * 玩家死亡
     */
    public void playerDied() {
        lives--;
        
        if (lives <= 0) {
            gameOver = true;
            System.out.println("游戏结束！最终得分: " + score);
        } else {
            // 重生玩家
            respawnPlayer();
            System.out.println("玩家重生，剩余生命: " + lives);
        }
    }
    
    /**
     * 重生玩家
     */
    private void respawnPlayer() {
        List<ElementObj> players = elementManager.getElementsByKey(GameElement.PLAY);
        if (players.isEmpty()) {
            // 创建新玩家
            Play newPlayer = new Play(200, 500);
            elementManager.addElement(newPlayer, GameElement.PLAY);
        } else {
            // 重置玩家状态
            for (ElementObj playerObj : players) {
                if (playerObj instanceof Play) {
                    Play player = (Play) playerObj;
                    player.reset();
                }
            }
        }
    }
    
    /**
     * 添加得分
     */
    public void addScore(int points) {
        score += points;
    }
    
    /**
     * 敌人被消灭
     */
    public void enemyDestroyed() {
        addScore(100); // 每消灭一个敌人得100分
    }
    
    /**
     * 基地被摧毁
     */
    public void baseDestroyed() {
        gameOver = true;
        System.out.println("基地被摧毁！游戏结束！");
    }
    
    // Getters
    public boolean isGameRunning() {
        return gameRunning;
    }
    
    public boolean isGameOver() {
        return gameOver;
    }
    
    public boolean isLevelComplete() {
        return levelComplete;
    }
    
    public int getScore() {
        return score;
    }
    
    public int getLives() {
        return lives;
    }
    
    public int getCurrentLevel() {
        return currentLevel.getCurrentLevel();
    }
    
    public LevelData getLevelData() {
        return currentLevel;
    }
    
    public ElementManager getElementManager() {
        return elementManager;
    }
}