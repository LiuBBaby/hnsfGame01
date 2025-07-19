package com.tedu.manager;

/**
 * 游戏线程管理器
 */
public class GameThread extends Thread {
    
    private boolean running = false;
    private boolean paused = false;
    private int fps = 60; // 帧率
    private long frameTime = 1000 / fps; // 每帧时间
    
    private Runnable gameLoop;
    
    public GameThread(Runnable gameLoop) {
        this.gameLoop = gameLoop;
    }
    
    @Override
    public void run() {
        running = true;
        long lastTime = System.currentTimeMillis();
        
        while (running) {
            long currentTime = System.currentTimeMillis();
            long deltaTime = currentTime - lastTime;
            
            if (!paused && deltaTime >= frameTime) {
                // 执行游戏循环
                if (gameLoop != null) {
                    gameLoop.run();
                }
                lastTime = currentTime;
            }
            
            try {
                Thread.sleep(1); // 稍微睡眠以减少CPU使用率
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
    
    /**
     * 停止游戏线程
     */
    public void stopGame() {
        running = false;
    }
    
    /**
     * 暂停游戏
     */
    public void pauseGame() {
        paused = true;
    }
    
    /**
     * 恢复游戏
     */
    public void resumeGame() {
        paused = false;
    }
    
    /**
     * 切换暂停状态
     */
    public void togglePause() {
        paused = !paused;
    }
    
    /**
     * 设置帧率
     */
    public void setFPS(int fps) {
        this.fps = fps;
        this.frameTime = 1000 / fps;
    }
    
    /**
     * 获取当前帧率
     */
    public int getFPS() {
        return fps;
    }
    
    /**
     * 检查游戏是否正在运行
     */
    public boolean isRunning() {
        return running;
    }
    
    /**
     * 检查游戏是否暂停
     */
    public boolean isPaused() {
        return paused;
    }
}