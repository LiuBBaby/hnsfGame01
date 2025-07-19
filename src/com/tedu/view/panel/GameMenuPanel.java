package com.tedu.view.panel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.tedu.view.frame.GameFrame;

/**
 * 游戏菜单面板
 */
public class GameMenuPanel extends JPanel {
    
    private GameFrame gameFrame;
    private JButton startButton;
    private JButton exitButton;
    private JLabel titleLabel;
    
    public GameMenuPanel(GameFrame gameFrame) {
        this.gameFrame = gameFrame;
        initPanel();
        initComponents();
        layoutComponents();
        addEventListeners();
    }
    
    /**
     * 初始化面板
     */
    private void initPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.BLACK);
    }
    
    /**
     * 初始化组件
     */
    private void initComponents() {
        // 标题
        titleLabel = new JLabel("坦克大战", JLabel.CENTER);
        titleLabel.setFont(new Font("宋体", Font.BOLD, 48));
        titleLabel.setForeground(Color.YELLOW);
        
        // 按钮
        startButton = new JButton("开始游戏");
        exitButton = new JButton("退出游戏");
        
        // 设置按钮样式
        styleButton(startButton);
        styleButton(exitButton);
    }
    
    /**
     * 设置按钮样式
     */
    private void styleButton(JButton button) {
        button.setFont(new Font("宋体", Font.BOLD, 20));
        button.setPreferredSize(new Dimension(200, 50));
        button.setBackground(Color.DARK_GRAY);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        
        // 添加鼠标悬停效果
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                button.setBackground(Color.GRAY);
            }
            
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                button.setBackground(Color.DARK_GRAY);
            }
        });
    }
    
    /**
     * 布局组件
     */
    private void layoutComponents() {
        // 上部标题
        add(titleLabel, BorderLayout.NORTH);
        
        // 中间按钮区域
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(Color.BLACK);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 0));
        
        // 添加按钮
        buttonPanel.add(Box.createVerticalGlue());
        
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.add(startButton);
        
        buttonPanel.add(Box.createVerticalStrut(20));
        
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonPanel.add(exitButton);
        
        buttonPanel.add(Box.createVerticalGlue());
        
        add(buttonPanel, BorderLayout.CENTER);
        
        // 底部信息
        JLabel infoLabel = new JLabel("使用方向键移动，空格键射击", JLabel.CENTER);
        infoLabel.setFont(new Font("宋体", Font.PLAIN, 14));
        infoLabel.setForeground(Color.LIGHT_GRAY);
        infoLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        add(infoLabel, BorderLayout.SOUTH);
    }
    
    /**
     * 添加事件监听器
     */
    private void addEventListeners() {
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameFrame.startGame();
            }
        });
        
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(
                    GameMenuPanel.this,
                    "确定要退出游戏吗？",
                    "退出确认",
                    JOptionPane.YES_NO_OPTION
                );
                
                if (result == JOptionPane.YES_OPTION) {
                    gameFrame.exitGame();
                }
            }
        });
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // 可以在这里添加背景图片或其他装饰
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // 绘制背景渐变
        GradientPaint gradient = new GradientPaint(
            0, 0, new Color(0, 0, 50),
            0, getHeight(), new Color(0, 0, 0)
        );
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }
}