package com.tedu.manager;

import java.util.List;
import java.util.Iterator;

import com.tedu.element.*;

/**
 * 碰撞检测器
 */
public class CollisionDetector {
    
    private ElementManager em;
    
    public CollisionDetector() {
        this.em = ElementManager.getManager();
    }
    
    /**
     * 检测所有碰撞
     */
    public void detectAllCollisions() {
        detectBulletCollisions();
        detectTankCollisions();
    }
    
    /**
     * 检测子弹碰撞
     */
    private void detectBulletCollisions() {
        // 玩家子弹碰撞检测
        detectPlayerBulletCollisions();
        
        // 敌人子弹碰撞检测
        detectEnemyBulletCollisions();
    }
    
    /**
     * 检测玩家子弹碰撞
     */
    private void detectPlayerBulletCollisions() {
        List<ElementObj> playerBullets = em.getElementsByKey(GameElement.BULLET_PLAY);
        List<ElementObj> enemies = em.getElementsByKey(GameElement.ENEMY);
        List<ElementObj> bricks = em.getElementsByKey(GameElement.BRICK);
        List<ElementObj> irons = em.getElementsByKey(GameElement.IRON);
        
        Iterator<ElementObj> bulletIter = playerBullets.iterator();
        while (bulletIter.hasNext()) {
            Bullet bullet = (Bullet) bulletIter.next();
            
            if (!bullet.isLive()) {
                bulletIter.remove();
                continue;
            }
            
            // 与敌人碰撞
            Iterator<ElementObj> enemyIter = enemies.iterator();
            while (enemyIter.hasNext()) {
                Enemy enemy = (Enemy) enemyIter.next();
                if (enemy.isAlive() && bullet.isCollision(enemy)) {
                    bullet.setLive(false);
                    enemy.takeDamage(1);
                    bulletIter.remove();
                    if (!enemy.isAlive()) {
                        enemyIter.remove();
                    }
                    break;
                }
            }
            
            if (!bullet.isLive()) continue;
            
            // 与砖块碰撞
            Iterator<ElementObj> brickIter = bricks.iterator();
            while (brickIter.hasNext()) {
                ElementObj brick = brickIter.next();
                if (bullet.isCollision(brick)) {
                    bullet.setLive(false);
                    bulletIter.remove();
                    brickIter.remove();
                    break;
                }
            }
            
            if (!bullet.isLive()) continue;
            
            // 与铁块碰撞
            for (ElementObj iron : irons) {
                if (bullet.isCollision(iron)) {
                    bullet.setLive(false);
                    bulletIter.remove();
                    break;
                }
            }
        }
    }
    
    /**
     * 检测敌人子弹碰撞
     */
    private void detectEnemyBulletCollisions() {
        List<ElementObj> enemyBullets = em.getElementsByKey(GameElement.BULLET_ENEMY);
        List<ElementObj> players = em.getElementsByKey(GameElement.PLAY);
        List<ElementObj> bricks = em.getElementsByKey(GameElement.BRICK);
        List<ElementObj> irons = em.getElementsByKey(GameElement.IRON);
        List<ElementObj> bases = em.getElementsByKey(GameElement.BASE);
        
        Iterator<ElementObj> bulletIter = enemyBullets.iterator();
        while (bulletIter.hasNext()) {
            Bullet bullet = (Bullet) bulletIter.next();
            
            if (!bullet.isLive()) {
                bulletIter.remove();
                continue;
            }
            
            // 与玩家碰撞
            for (ElementObj playerObj : players) {
                Play player = (Play) playerObj;
                if (player.isAlive() && bullet.isCollision(player)) {
                    bullet.setLive(false);
                    player.takeDamage(1);
                    bulletIter.remove();
                    break;
                }
            }
            
            if (!bullet.isLive()) continue;
            
            // 与基地碰撞
            for (ElementObj base : bases) {
                if (bullet.isCollision(base)) {
                    bullet.setLive(false);
                    bulletIter.remove();
                    // 游戏结束逻辑
                    break;
                }
            }
            
            if (!bullet.isLive()) continue;
            
            // 与砖块碰撞
            Iterator<ElementObj> brickIter = bricks.iterator();
            while (brickIter.hasNext()) {
                ElementObj brick = brickIter.next();
                if (bullet.isCollision(brick)) {
                    bullet.setLive(false);
                    bulletIter.remove();
                    brickIter.remove();
                    break;
                }
            }
            
            if (!bullet.isLive()) continue;
            
            // 与铁块碰撞
            for (ElementObj iron : irons) {
                if (bullet.isCollision(iron)) {
                    bullet.setLive(false);
                    bulletIter.remove();
                    break;
                }
            }
        }
    }
    
    /**
     * 检测坦克碰撞
     */
    private void detectTankCollisions() {
        List<ElementObj> players = em.getElementsByKey(GameElement.PLAY);
        List<ElementObj> enemies = em.getElementsByKey(GameElement.ENEMY);
        List<ElementObj> bricks = em.getElementsByKey(GameElement.BRICK);
        List<ElementObj> irons = em.getElementsByKey(GameElement.IRON);
        List<ElementObj> rivers = em.getElementsByKey(GameElement.RIVER);
        
        // 检测玩家与地形碰撞
        for (ElementObj playerObj : players) {
            Tank player = (Tank) playerObj;
            if (!player.isAlive()) continue;
            
            checkTankTerrainCollision(player, bricks, irons, rivers);
        }
        
        // 检测敌人与地形碰撞
        for (ElementObj enemyObj : enemies) {
            Tank enemy = (Tank) enemyObj;
            if (!enemy.isAlive()) continue;
            
            checkTankTerrainCollision(enemy, bricks, irons, rivers);
        }
        
        // 检测坦克之间碰撞
        checkTankToTankCollisions(players, enemies);
    }
    
    /**
     * 检测坦克与地形碰撞
     */
    private void checkTankTerrainCollision(Tank tank, List<ElementObj> bricks, 
                                         List<ElementObj> irons, List<ElementObj> rivers) {
        // 与砖块碰撞
        for (ElementObj brick : bricks) {
            if (tank.isCollision(brick)) {
                // 处理碰撞，可能需要回退坦克位置
                handleTankCollision(tank);
                return;
            }
        }
        
        // 与铁块碰撞
        for (ElementObj iron : irons) {
            if (tank.isCollision(iron)) {
                handleTankCollision(tank);
                return;
            }
        }
        
        // 与河流碰撞
        for (ElementObj river : rivers) {
            if (tank.isCollision(river)) {
                handleTankCollision(tank);
                return;
            }
        }
    }
    
    /**
     * 检测坦克之间碰撞
     */
    private void checkTankToTankCollisions(List<ElementObj> players, List<ElementObj> enemies) {
        // 玩家与敌人碰撞
        for (ElementObj playerObj : players) {
            Tank player = (Tank) playerObj;
            if (!player.isAlive()) continue;
            
            for (ElementObj enemyObj : enemies) {
                Tank enemy = (Tank) enemyObj;
                if (enemy.isAlive() && player.checkCollisionWithTank(enemy)) {
                    handleTankCollision(player);
                    handleTankCollision(enemy);
                }
            }
        }
        
        // 敌人之间碰撞
        for (int i = 0; i < enemies.size(); i++) {
            Tank enemy1 = (Tank) enemies.get(i);
            if (!enemy1.isAlive()) continue;
            
            for (int j = i + 1; j < enemies.size(); j++) {
                Tank enemy2 = (Tank) enemies.get(j);
                if (enemy2.isAlive() && enemy1.checkCollisionWithTank(enemy2)) {
                    handleTankCollision(enemy1);
                    handleTankCollision(enemy2);
                }
            }
        }
    }
    
    /**
     * 处理坦克碰撞
     */
    private void handleTankCollision(Tank tank) {
        // 简单处理：如果是敌人坦克，让它改变方向
        if (tank instanceof Enemy) {
            ((Enemy) tank).handleCollision();
        }
        // 对于玩家坦克，可以在这里添加回退逻辑
    }
}