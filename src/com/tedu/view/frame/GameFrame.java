package com.tedu.view.frame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import com.tedu.view.panel.GameMainPanel;
import com.tedu.view.panel.GameMenuPanel;
import com.tedu.controller.game.GameController;

/**
 * 游戏主窗口
 */
public class GameFrame extends JFrame {
    
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;
    
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private GameMainPanel gameMainPanel;
    private GameMenuPanel gameMenuPanel;
    private GameController gameController;
    
    public GameFrame() {
        initFrame();
        initPanels();
        initController();
    }
    
    /**
     * 初始化窗口
     */
    private void initFrame() {
        setTitle("坦克大战 - MVC架构版本");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        
        // 窗口居中
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - WINDOW_WIDTH) / 2;
        int y = (screenSize.height - WINDOW_HEIGHT) / 2;
        setLocation(x, y);
        
        // 设置图标
        try {
            setIconImage(Toolkit.getDefaultToolkit().getImage("resource/image/icon.png"));
        } catch (Exception e) {
            System.err.println("加载图标失败: " + e.getMessage());
        }
    }
    
    /**
     * 初始化面板
     */
    private void initPanels() {
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
        // 创建各个面板
        gameMenuPanel = new GameMenuPanel(this);
        gameMainPanel = new GameMainPanel();
        
        // 添加面板到卡片布局
        mainPanel.add(gameMenuPanel, "MENU");
        mainPanel.add(gameMainPanel, "GAME");
        
        add(mainPanel);
        
        // 默认显示菜单
        showMenuPanel();
    }
    
    /**
     * 初始化控制器
     */
    private void initController() {
        gameController = GameController.getInstance();
        gameController.init(gameMainPanel);
    }
    
    /**
     * 显示菜单面板
     */
    public void showMenuPanel() {
        cardLayout.show(mainPanel, "MENU");
    }
    
    /**
     * 显示游戏面板
     */
    public void showGamePanel() {
        cardLayout.show(mainPanel, "GAME");
        gameMainPanel.requestFocus(); // 获取焦点以接收键盘输入
    }
    
    /**
     * 开始游戏
     */
    public void startGame() {
        showGamePanel();
        gameController.startGame();
    }
    
    /**
     * 返回菜单
     */
    public void backToMenu() {
        gameController.stopGame();
        showMenuPanel();
    }
    
    /**
     * 退出游戏
     */
    public void exitGame() {
        gameController.stopGame();
        System.exit(0);
    }
    
    // Getters
    public GameMainPanel getGameMainPanel() {
        return gameMainPanel;
    }
    
    public GameMenuPanel getGameMenuPanel() {
        return gameMenuPanel;
    }
    
    public GameController getGameController() {
        return gameController;
    }
}