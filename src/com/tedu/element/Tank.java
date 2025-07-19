package com.tedu.element;

import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;

/**
 * 坦克基类
 */
public abstract class Tank extends ElementObj {
    
    // 游戏区域边界
    public static final int GAME_WIDTH = 780;
    public static final int GAME_HEIGHT = 580;
    public static final int TANK_SIZE = 40;
    
    // 坦克属性
    protected Direction direction = Direction.UP;
    protected int speed = 3;
    protected int health = 1;
    protected long lastShootTime = 0;
    protected long shootCooldown = 500; // 射击冷却时间（毫秒）
    protected boolean isAlive = true;
    
    // 不同方向的图片
    protected Map<Direction, ImageIcon> directionImages;
    
    public Tank(int x, int y, String tankType) {
        super(x, y, TANK_SIZE, TANK_SIZE, null);
        initImages(tankType);
        updateIcon();
    }
    
    /**
     * 初始化不同方向的图片
     */
    protected void initImages(String tankType) {
        directionImages = new HashMap<>();
        try {
            directionImages.put(Direction.UP, new ImageIcon("resource/image/tank/" + tankType + "/" + tankType + "_up.png"));
            directionImages.put(Direction.DOWN, new ImageIcon("resource/image/tank/" + tankType + "/" + tankType + "_down.png"));
            directionImages.put(Direction.LEFT, new ImageIcon("resource/image/tank/" + tankType + "/" + tankType + "_left.png"));
            directionImages.put(Direction.RIGHT, new ImageIcon("resource/image/tank/" + tankType + "/" + tankType + "_right.png"));
        } catch (Exception e) {
            System.err.println("加载坦克图片失败: " + e.getMessage());
        }
    }
    
    /**
     * 更新当前方向的图片
     */
    protected void updateIcon() {
        ImageIcon currentIcon = directionImages.get(direction);
        if (currentIcon != null) {
            setIcon(currentIcon);
        }
    }
    
    @Override
    public void showElement(Graphics g) {
        if (isAlive && getIcon() != null) {
            getIcon().paintIcon(null, g, getX(), getY());
        }
    }
    
    /**
     * 移动坦克
     */
    public void move() {
        if (!isAlive) return;
        
        int newX = getX();
        int newY = getY();
        
        switch (direction) {
            case UP:
                newY -= speed;
                break;
            case DOWN:
                newY += speed;
                break;
            case LEFT:
                newX -= speed;
                break;
            case RIGHT:
                newX += speed;
                break;
        }
        
        // 边界检查
        if (newX >= 0 && newX <= GAME_WIDTH - TANK_SIZE && 
            newY >= 0 && newY <= GAME_HEIGHT - TANK_SIZE) {
            setX(newX);
            setY(newY);
        }
    }
    
    /**
     * 改变方向
     */
    public void changeDirection(Direction newDirection) {
        if (this.direction != newDirection) {
            this.direction = newDirection;
            updateIcon();
        }
    }
    
    /**
     * 射击
     */
    public Bullet shoot() {
        if (!isAlive) return null;
        
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastShootTime < shootCooldown) {
            return null; // 冷却中
        }
        
        lastShootTime = currentTime;
        
        // 计算子弹出生位置（坦克中心）
        int bulletX = getCenterX() - 5; // 子弹宽度的一半
        int bulletY = getCenterY() - 5; // 子弹高度的一半
        
        return new Bullet(bulletX, bulletY, direction);
    }
    
    /**
     * 受到伤害
     */
    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            isAlive = false;
        }
    }
    
    /**
     * 检查是否与其他坦克碰撞
     */
    public boolean checkCollisionWithTank(Tank other) {
        if (!isAlive || !other.isAlive) return false;
        return isCollision(other);
    }
    
    // Getters and Setters
    public Direction getDirection() {
        return direction;
    }
    
    public void setDirection(Direction direction) {
        changeDirection(direction);
    }
    
    public int getSpeed() {
        return speed;
    }
    
    public void setSpeed(int speed) {
        this.speed = speed;
    }
    
    public int getHealth() {
        return health;
    }
    
    public void setHealth(int health) {
        this.health = health;
    }
    
    public boolean isAlive() {
        return isAlive;
    }
    
    public void setAlive(boolean alive) {
        isAlive = alive;
    }
    
    public long getShootCooldown() {
        return shootCooldown;
    }
    
    public void setShootCooldown(long shootCooldown) {
        this.shootCooldown = shootCooldown;
    }
}