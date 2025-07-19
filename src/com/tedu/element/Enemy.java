package com.tedu.element;

import java.awt.Graphics;
import java.util.Random;

/**
 * @说明 敌人坦克类
 * @author renjj
 *
 */
public class Enemy extends Tank {
    
    private Random random = new Random();
    private long lastDirectionChange = 0;
    private long directionChangeInterval = 2000; // 2秒改变一次方向
    private long lastShootAttempt = 0;
    private long shootInterval = 1500; // 1.5秒尝试射击一次
    
    public Enemy() {
        super(100, 100, "bot");
        // 随机初始方向
        Direction[] directions = Direction.values();
        direction = directions[random.nextInt(directions.length)];
        updateIcon();
    }
    
    public Enemy(int x, int y) {
        super(x, y, "bot");
        Direction[] directions = Direction.values();
        direction = directions[random.nextInt(directions.length)];
        updateIcon();
    }
    
    /**
     * AI行为更新
     */
    public void updateAI() {
        if (!isAlive) return;
        
        long currentTime = System.currentTimeMillis();
        
        // 定期改变方向
        if (currentTime - lastDirectionChange > directionChangeInterval) {
            changeRandomDirection();
            lastDirectionChange = currentTime;
        }
        
        // 移动
        move();
    }
    
    /**
     * 随机改变方向
     */
    private void changeRandomDirection() {
        Direction[] directions = Direction.values();
        Direction newDirection;
        
        // 避免连续相同方向
        do {
            newDirection = directions[random.nextInt(directions.length)];
        } while (newDirection == direction && random.nextFloat() < 0.7f);
        
        changeDirection(newDirection);
    }
    
    /**
     * AI射击逻辑
     */
    public Bullet attemptShoot() {
        if (!isAlive) return null;
        
        long currentTime = System.currentTimeMillis();
        
        // 定期尝试射击
        if (currentTime - lastShootAttempt > shootInterval) {
            lastShootAttempt = currentTime;
            
            // 30%的几率射击
            if (random.nextFloat() < 0.3f) {
                return shoot();
            }
        }
        
        return null;
    }
    
    /**
     * 处理碰撞，改变方向
     */
    public void handleCollision() {
        // 碰撞时随机改变方向
        changeRandomDirection();
    }
    
    @Override
    public void move() {
        if (!isAlive) return;
        
        int oldX = getX();
        int oldY = getY();
        
        super.move();
        
        // 如果位置没有改变（可能撞到边界），则改变方向
        if (getX() == oldX && getY() == oldY) {
            handleCollision();
        }
    }
    
    @Override
    public void showElement(Graphics g) {
        super.showElement(g);
        
        // 可以在这里添加敌人坦克的特殊显示效果
        // 比如警告标识等
    }
    
    /**
     * 设置AI难度
     */
    public void setDifficulty(int level) {
        switch (level) {
            case 1: // 简单
                speed = 2;
                shootInterval = 3000;
                directionChangeInterval = 3000;
                break;
            case 2: // 中等
                speed = 3;
                shootInterval = 2000;
                directionChangeInterval = 2500;
                break;
            case 3: // 困难
                speed = 4;
                shootInterval = 1000;
                directionChangeInterval = 1500;
                break;
            default:
                speed = 3;
                shootInterval = 1500;
                directionChangeInterval = 2000;
        }
    }
    
    // Getters for AI parameters
    public long getDirectionChangeInterval() {
        return directionChangeInterval;
    }
    
    public void setDirectionChangeInterval(long interval) {
        this.directionChangeInterval = interval;
    }
    
    public long getShootInterval() {
        return shootInterval;
    }
    
    public void setShootInterval(long interval) {
        this.shootInterval = interval;
    }
}