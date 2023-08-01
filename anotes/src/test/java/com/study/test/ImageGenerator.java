package com.study.test;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageGenerator {
    public static void main(String[] args) {
        int width = 85;
        int height = 150;
        // 创建一个空白的 BufferedImage 对象
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // 获取 Graphics2D 绘图上下文
        Graphics2D g2d = image.createGraphics();

        // 设置背景颜色和字体
        g2d.setColor(java.awt.Color.getHSBColor(0.5855f,0.8941f,0.3333f));
        g2d.fillRect(0, 0, width, height);
        g2d.setColor(java.awt.Color.getHSBColor(0.0000f,0.0000f,0.7804f));
        g2d.setFont(new Font("MicrosoftYaHei", Font.PLAIN, 12));

        // 在图片上绘制文字
        String line1 = "叶尖损伤指数";
        String line2 = "叶片3Y-20";

        FontMetrics fm = g2d.getFontMetrics();
//        int x1 = (width - fm.stringWidth(line1)) / 2; // 文字居中
//        int x2 = (width - fm.stringWidth(line2)) / 2; // 文字居中
        int x1 = 10;//文字向左对齐
        int x2 = 10;
//        int y = fm.getAscent(); // 文字靠上
        int y = 30; // 文字靠上

        g2d.drawString(line1, x1, y);
        g2d.drawString(line2, x2, y + fm.getHeight()+5);// 设置两行文字之间的间距为5像素

        // 释放绘图上下文资源
        g2d.dispose();

        // 保存图片
        File output = new File("D:\\generated_image.png");
        try {
            ImageIO.write(image, "png", output);
            System.out.println("图片生成成功！");
        } catch (IOException e) {
            System.out.println("图片生成失败：" + e.getMessage());
        }
    }
}