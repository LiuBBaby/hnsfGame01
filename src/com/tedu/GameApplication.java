package com.tedu;

import com.tedu.view.frame.GameFrame;

/**
 * 游戏主入口 - MVC架构版本
 */
public class GameApplication {
    
    public static void main(String[] args) {
        // 创建并显示游戏窗体
        GameFrame gameFrame = new GameFrame();
        gameFrame.setVisible(true);
        
        System.out.println("=== 坦克大战 MVC版本启动 ===");
        System.out.println("项目结构已重构为标准MVC架构");
        System.out.println("Model: 数据和业务逻辑");
        System.out.println("View: 用户界面");
        System.out.println("Controller: 控制逻辑");
        System.out.println("============================");
    }
}