package com.tedu.controller.game;

import com.tedu.model.service.GameService;
import com.tedu.model.data.LevelData;
import com.tedu.view.panel.GameMainPanel;
import com.tedu.controller.input.InputController;
import com.tedu.manager.InputManager;
import com.tedu.manager.ElementManager;
import com.tedu.manager.GameElement;
import com.tedu.manager.CollisionDetector;
import com.tedu.element.Direction;
import com.tedu.element.ElementObj;
import com.tedu.element.Play;
import com.tedu.element.Bullet;
import java.util.List;

/**
 * 游戏控制器 - 协调Model和View
 */
public class GameController {
    private static GameController instance;
    private GameService gameService;
    private GameMainPanel gamePanel;
    private InputController inputController;
    private boolean gameRunning = false;
    private ElementManager elementManager;
    private CollisionDetector collisionDetector;
    
    private GameController() {
        this.gameService = GameService.getInstance();
        this.elementManager = ElementManager.getManager();
        this.collisionDetector = new CollisionDetector();
    }
    
    public static GameController getInstance() {
        if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }
    
    public void init(GameMainPanel gamePanel) {
        this.gamePanel = gamePanel;
        this.inputController = new InputController(gamePanel);
    }
    
    /**
     * 开始游戏
     */
    public void startGame() {
        gameRunning = true;
        gameService.startGame();
        
        // 启动游戏循环
        Thread gameThread = new Thread(this::gameLoop);
        gameThread.start();
    }
    
    /**
     * 游戏主循环
     */
    private void gameLoop() {
        while (gameRunning) {
            try {
                // 更新游戏状态
                updateGame();
                
                // 重绘界面
                if (gamePanel != null) {
                    gamePanel.repaint();
                }
                
                // 控制帧率
                Thread.sleep(16); // 约60FPS
                
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
    
    /**
     * 更新游戏状态
     */
    private void updateGame() {
        // 处理输入
        handleInput();
        
        // 更新游戏元素
        updateGameElements();
        
        // 碰撞检测
        collisionDetector.detectAllCollisions();
        
        // 检查游戏状态
        checkGameState();
    }
    
    /**
     * 处理输入
     */
    private void handleInput() {
        if (inputController != null) {
            inputController.handleInput();
        }
    }
    
    /**
     * 更新游戏元素
     */
    private void updateGameElements() {
        // 更新玩家
        List<ElementObj> players = elementManager.getElementsByKey(GameElement.PLAY);
        for (ElementObj playerObj : players) {
            if (playerObj instanceof Play) {
                Play player = (Play) playerObj;
                player.update();
            }
        }
        
        // 更新敌人
        gameService.updateEnemies();
        
        // 更新子弹
        gameService.updateBullets();
    }
    
    /**
     * 检查游戏状态
     */
    private void checkGameState() {
        if (gameService.isGameOver()) {
            gameRunning = false;
            // 处理游戏结束
            handleGameOver();
        } else if (gameService.isLevelComplete()) {
            // 处理关卡完成
            handleLevelComplete();
        }
    }
    
    /**
     * 处理游戏结束
     */
    private void handleGameOver() {
        System.out.println("游戏结束!");
        // 可以在这里显示游戏结束界面
    }
    
    /**
     * 处理关卡完成
     */
    private void handleLevelComplete() {
        System.out.println("关卡完成!");
        gameService.nextLevel();
    }
    
    /**
     * 暂停游戏
     */
    public void pauseGame() {
        gameRunning = false;
    }
    
    /**
     * 恢复游戏
     */
    public void resumeGame() {
        if (!gameRunning) {
            startGame();
        }
    }
    
    /**
     * 停止游戏
     */
    public void stopGame() {
        gameRunning = false;
        gameService.stopGame();
    }
    
    /**
     * 重新开始游戏
     */
    public void restartGame() {
        stopGame();
        gameService.restartGame();
        startGame();
    }
    
    // Getters
    public boolean isGameRunning() {
        return gameRunning;
    }
    
    public GameService getGameService() {
        return gameService;
    }
}