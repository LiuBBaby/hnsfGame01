package com.tedu.manager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.ImageIcon;

import com.tedu.element.*;

/**
 * 地图加载器
 */
public class MapLoader {
    
    private static final int TILE_SIZE = 20; // 每个瓦片的大小
    
    /**
     * 加载指定关卡的地图
     */
    public static void loadMap(int level) {
        ElementManager em = ElementManager.getManager();
        
        try {
            // 加载指定关卡的地图文件
            String mapFile = "src/com/tedu/data/level" + level + ".map";
            BufferedReader reader = new BufferedReader(new FileReader(mapFile));
            String line;
            
            // 加载图片资源
            ImageIcon brickIcon = new ImageIcon("resource/image/wall/brick.png");
            ImageIcon ironIcon = new ImageIcon("resource/image/wall/iron.png");
            ImageIcon grassIcon = new ImageIcon("resource/image/wall/grass.png");
            ImageIcon riverIcon = new ImageIcon("resource/image/wall/river.png");
            ImageIcon baseIcon = new ImageIcon("resource/image/wall/base.png");
            
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                
                String[] parts = line.split("=");
                if (parts.length != 2) {
                    continue;
                }
                
                String elementType = parts[0].trim();
                String coordinates = parts[1].trim();
                
                // 解析坐标
                String[] coords = coordinates.split(";");
                
                for (String coord : coords) {
                    if (coord.trim().isEmpty()) {
                        continue;
                    }
                    
                    String[] xy = coord.split(",");
                    if (xy.length != 2) {
                        continue;
                    }
                    
                    try {
                        int x = Integer.parseInt(xy[0].trim()) * TILE_SIZE;
                        int y = Integer.parseInt(xy[1].trim()) * TILE_SIZE;
                        
                        // 根据元素类型创建对应的元素
                        switch (elementType.toLowerCase()) {
                            case "brick":
                                em.addElement(new Brick(x, y, TILE_SIZE, TILE_SIZE, brickIcon), GameElement.BRICK);
                                break;
                            case "iron":
                                em.addElement(new Iron(x, y, TILE_SIZE, TILE_SIZE, ironIcon), GameElement.IRON);
                                break;
                            case "grass":
                                em.addElement(new Grass(x, y, TILE_SIZE, TILE_SIZE, grassIcon), GameElement.GRASS);
                                break;
                            case "river":
                                em.addElement(new River(x, y, TILE_SIZE, TILE_SIZE, riverIcon), GameElement.RIVER);
                                break;
                            case "base":
                                em.addElement(new Base(x, y, TILE_SIZE, TILE_SIZE, baseIcon), GameElement.BASE);
                                break;
                            case "enemy":
                                em.addElement(new Enemy(x, y), GameElement.ENEMY);
                                break;
                            case "player":
                                em.addElement(new Play(x, y), GameElement.PLAY);
                                break;
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("解析坐标失败: " + coord);
                    }
                }
            }
            
            reader.close();
            System.out.println("关卡 " + level + " 加载完成");
            
        } catch (IOException e) {
            System.err.println("加载地图文件失败: " + e.getMessage());
        }
    }
    
    /**
     * 加载默认地图（用于测试）
     */
    public static void loadDefaultMap() {
        ElementManager em = ElementManager.getManager();
        
        // 加载图片资源
        ImageIcon brickIcon = new ImageIcon("resource/image/wall/brick.png");
        ImageIcon ironIcon = new ImageIcon("resource/image/wall/iron.png");
        ImageIcon baseIcon = new ImageIcon("resource/image/wall/base.png");
        
        // 创建一些基本元素
        // 在地图周围创建铁墙
        for (int i = 0; i < 39; i++) {
            // 上边界
            em.addElement(new Iron(i * TILE_SIZE, 0, TILE_SIZE, TILE_SIZE, ironIcon), GameElement.IRON);
            // 下边界
            em.addElement(new Iron(i * TILE_SIZE, 29 * TILE_SIZE, TILE_SIZE, TILE_SIZE, ironIcon), GameElement.IRON);
        }
        
        for (int i = 0; i < 30; i++) {
            // 左边界
            em.addElement(new Iron(0, i * TILE_SIZE, TILE_SIZE, TILE_SIZE, ironIcon), GameElement.IRON);
            // 右边界
            em.addElement(new Iron(38 * TILE_SIZE, i * TILE_SIZE, TILE_SIZE, TILE_SIZE, ironIcon), GameElement.IRON);
        }
        
        // 添加一些砖块
        for (int i = 5; i < 15; i++) {
            em.addElement(new Brick(i * TILE_SIZE, 10 * TILE_SIZE, TILE_SIZE, TILE_SIZE, brickIcon), GameElement.BRICK);
        }
        
        // 添加基地
        em.addElement(new Base(19 * TILE_SIZE, 27 * TILE_SIZE, TILE_SIZE, TILE_SIZE, baseIcon), GameElement.BASE);
        
        // 添加玩家
        em.addElement(new Play(200, 500), GameElement.PLAY);
        
        // 添加敌人
        em.addElement(new Enemy(100, 100), GameElement.ENEMY);
        em.addElement(new Enemy(300, 100), GameElement.ENEMY);
        em.addElement(new Enemy(500, 100), GameElement.ENEMY);
        
        System.out.println("默认地图加载完成");
    }
}