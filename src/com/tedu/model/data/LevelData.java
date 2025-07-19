package com.tedu.model.data;

/**
 * 关卡数据类
 */
public class LevelData {
    
    private int currentLevel = 1;
    private int maxLevel = 2; // 目前有两个关卡
    private String[] levelNames = {
        "第一关 - 新手教学",
        "第二关 - 迷宫挑战"
    };
    private String[] levelDescriptions = {
        "简单的入门关卡，熟悉游戏操作",
        "复杂的迷宫结构，需要策略和技巧"
    };
    
    public LevelData() {
        // 默认构造函数
    }
    
    public LevelData(int startLevel) {
        if (startLevel >= 1 && startLevel <= maxLevel) {
            this.currentLevel = startLevel;
        }
    }
    
    /**
     * 进入下一关
     */
    public void nextLevel() {
        if (currentLevel < maxLevel) {
            currentLevel++;
        }
    }
    
    /**
     * 返回上一关
     */
    public void previousLevel() {
        if (currentLevel > 1) {
            currentLevel--;
        }
    }
    
    /**
     * 设置当前关卡
     */
    public void setCurrentLevel(int level) {
        if (level >= 1 && level <= maxLevel) {
            this.currentLevel = level;
        }
    }
    
    /**
     * 重置到第一关
     */
    public void reset() {
        this.currentLevel = 1;
    }
    
    /**
     * 检查是否是最后一关
     */
    public boolean isLastLevel() {
        return currentLevel >= maxLevel;
    }
    
    /**
     * 检查是否是第一关
     */
    public boolean isFirstLevel() {
        return currentLevel <= 1;
    }
    
    /**
     * 获取当前关卡名称
     */
    public String getCurrentLevelName() {
        if (currentLevel >= 1 && currentLevel <= levelNames.length) {
            return levelNames[currentLevel - 1];
        }
        return "未知关卡";
    }
    
    /**
     * 获取当前关卡描述
     */
    public String getCurrentLevelDescription() {
        if (currentLevel >= 1 && currentLevel <= levelDescriptions.length) {
            return levelDescriptions[currentLevel - 1];
        }
        return "无描述";
    }
    
    /**
     * 获取指定关卡名称
     */
    public String getLevelName(int level) {
        if (level >= 1 && level <= levelNames.length) {
            return levelNames[level - 1];
        }
        return "未知关卡";
    }
    
    /**
     * 获取指定关卡描述
     */
    public String getLevelDescription(int level) {
        if (level >= 1 && level <= levelDescriptions.length) {
            return levelDescriptions[level - 1];
        }
        return "无描述";
    }
    
    /**
     * 获取进度百分比
     */
    public double getProgress() {
        return (double) currentLevel / maxLevel * 100.0;
    }
    
    // Getters and Setters
    public int getCurrentLevel() {
        return currentLevel;
    }
    
    public int getMaxLevel() {
        return maxLevel;
    }
    
    public void setMaxLevel(int maxLevel) {
        if (maxLevel > 0) {
            this.maxLevel = maxLevel;
            // 如果当前关卡超过了新的最大关卡数，则调整
            if (this.currentLevel > maxLevel) {
                this.currentLevel = maxLevel;
            }
        }
    }
    
    public String[] getLevelNames() {
        return levelNames.clone(); // 返回副本以防止外部修改
    }
    
    public void setLevelNames(String[] levelNames) {
        if (levelNames != null && levelNames.length > 0) {
            this.levelNames = levelNames.clone();
        }
    }
    
    public String[] getLevelDescriptions() {
        return levelDescriptions.clone();
    }
    
    public void setLevelDescriptions(String[] levelDescriptions) {
        if (levelDescriptions != null && levelDescriptions.length > 0) {
            this.levelDescriptions = levelDescriptions.clone();
        }
    }
    
    @Override
    public String toString() {
        return String.format("LevelData{currentLevel=%d, maxLevel=%d, levelName='%s'}", 
                           currentLevel, maxLevel, getCurrentLevelName());
    }
}