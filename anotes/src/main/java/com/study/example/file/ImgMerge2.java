package com.study.example.file;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;
/**
 * 图片合并方法
 * @author Java自学通
 *
 */
@SuppressWarnings("all")
public class ImgMerge2 {
    /**
     * @param args
     */
    public static void main(String[] args) {
// TODO Auto-generated method stub
        ArrayList<String> list =new ArrayList<String> ();
//        list.add("E:\\workspace_idea\\studyNotes\\1.jpg");
//        list.add("E:\\workspace_idea\\studyNotes\\12.jpg");
//        list.add("E:\\workspace_idea\\studyNotes\\13.jpg");
        String path="E:\\workspace_idea\\studyNotes";
//        doVImageMerging3(list,path);
        list.add("E:\\workspace_idea\\studyNotes\\generated_image.png");
        list.add("E:\\workspace_idea\\studyNotes\\linechart3.png");
        doVImageMerging2(list,path);
    }
    //2张图片水平合并
    public static String doVImageMerging2(ArrayList<String> list, String pathFile) {
// 读取待合并的文件
        BufferedImage bi1 = null;
        BufferedImage bi2 = null;
// 调用mergeImage方法获得合并后的图像
        BufferedImage destImg = null;
        try {
            bi1 = getBufferedImage(list.get(0));
            bi2 = getBufferedImage(list.get(1));
        } catch (IOException e) {
            e.printStackTrace();
        }
// 调用mergeImage方法获得合并后的图像
        try {
            destImg = mergeImage(bi1, bi2, true);//true为水平，false为垂直
        } catch (IOException e) {
            e.printStackTrace();
        }
// 保存图像
        String savePath = pathFile + "/";//文件存放路径
        String fileName = createImageName() + ".jpg";//文件名称
        String fileType = "jpg";//文件格式
        saveImage(destImg, savePath, fileName, fileType);
        return savePath + fileName;
    }
    //3张图片垂直合并
    public static String doVImageMerging3(ArrayList<String> list, String pathFile) {
// 读取待合并的文件
        BufferedImage bi1 = null;
        BufferedImage bi2 = null;
        BufferedImage bi3 = null;
// 调用mergeImage方法获得合并后的图像
        BufferedImage destImg = null;
        try {
            bi2 = getBufferedImage(list.get(0));
            bi1 = getBufferedImage(list.get(1));
            bi3 = getBufferedImage(list.get(2));
        } catch (IOException e) {
            e.printStackTrace();
        }
// 调用mergeImage方法获得合并后的图像
        try {
            destImg = mergeImage(bi1, bi2,bi3, false);//true为水平，false为垂直
        } catch (IOException e) {
            e.printStackTrace();
        }
// 保存图像
        String savePath = pathFile + "/";//文件存放路径
        String fileName = createImageName() + ".jpg";//文件名称
        String fileType = "jpg";//文件格式
        saveImage(destImg, savePath, fileName, fileType);
        return savePath + fileName;
    }
    public static BufferedImage getBufferedImage(String fileUrl) throws IOException {
        File f = new File(fileUrl);
        return ImageIO.read(f);
    }
    /**
     * 待合并的两张图必须满足这样的前提，如果水平方向合并，则高度必须相等；如果是垂直方向合并，宽度必须相等。
     * mergeImage方法不做判断，自己判断。
     *
     * @param img1
     * 待合并的第一张图
     * @param img2
     * 带合并的第二张图
     * @param isHorizontal
     * 为true时表示水平方向合并，为false时表示垂直方向合并
     * @return 返回合并后的BufferedImage对象
     * @throws IOException
     */
    public static BufferedImage mergeImage(BufferedImage img1, BufferedImage img2,BufferedImage img3, boolean isHorizontal) throws IOException {
        int w1 = img1.getWidth();
        int h1 = img1.getHeight();
        int w2 = img2.getWidth();
        int h2 = img2.getHeight();
        int w3 = img3.getWidth();
        int h3 = img3.getHeight();
// 从图片中读取RGB
        int[] ImageArrayOne = new int[w1 * h1];
        ImageArrayOne = img1.getRGB(0, 0, w1, h1, ImageArrayOne, 0, w1); // 逐行扫描图像中各个像素的RGB到数组中
        int[] ImageArrayTwo = new int[w2 * h2];
        ImageArrayTwo = img2.getRGB(0, 0, w2, h2, ImageArrayTwo, 0, w2);
        int[] ImageArrayThree = new int[w3 * h3];
        ImageArrayThree = img3.getRGB(0, 0, w3, h3, ImageArrayThree, 0, w3);
// 生成新图片
        BufferedImage DestImage = null;
        if (isHorizontal) { // 水平方向合并
// DestImage = new BufferedImage(w1+w2, h1, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = null;
            if (h1 >= h2) {
                DestImage = new BufferedImage(w1 + w2, h1, BufferedImage.TYPE_INT_RGB);
                g2d = DestImage.createGraphics();
                g2d.setPaint(Color.WHITE);
                g2d.fillRect(0, 0, w1 + w2, h1);
                g2d.dispose();
            } else {
                DestImage = new BufferedImage(w2, h1, BufferedImage.TYPE_INT_RGB);//TYPE_INT_RGB
                g2d = DestImage.createGraphics();
                g2d.setPaint(Color.WHITE);
                g2d.fillRect(0, 0, w2 + w1, h1);
                g2d.dispose();
            }
            DestImage.setRGB(0, 0, w1, h1, ImageArrayOne, 0, w1); // 设置上半部分或左半部分的RGB
            DestImage.setRGB(w1, 0, w2, h2, ImageArrayTwo, 0, w2);
        } else { // 垂直方向合并
            Graphics2D g2d = null;
            if (w1 >= w2) {
                DestImage = new BufferedImage(w1, h1 + h2+h3, BufferedImage.TYPE_INT_RGB);//TYPE_INT_RGB
                g2d = DestImage.createGraphics();
                g2d.setPaint(Color.WHITE);
                g2d.fillRect(0, 0, w1 + w2 + w3, h1 + h2 + h3);
                g2d.dispose();
            } else {
                DestImage = new BufferedImage(w2, h1 + h2 + h3, BufferedImage.TYPE_INT_RGB);//TYPE_INT_RGB
                g2d = DestImage.createGraphics();
                g2d.setPaint(Color.WHITE);
                g2d.fillRect(0, 0, w2 + w1 + w3, h1 + h2 + h3);
                g2d.dispose();
            }
            DestImage.setRGB(0, 0, w1, h1, ImageArrayOne, 0, w1); // 设置上半部分或左半部分的RGB
            DestImage.setRGB(0, h1, w2, h2, ImageArrayTwo, 0, w2); // 设置下半部分的RGB
            DestImage.setRGB(0, h1 + h2, w3, h3, ImageArrayThree, 0, w3); // 设置下半部分的RGB
        }
        return DestImage;
    }

    public static BufferedImage mergeImage(BufferedImage img1, BufferedImage img2, boolean isHorizontal) throws IOException {
        int w1 = img1.getWidth();
        int h1 = img1.getHeight();
        int w2 = img2.getWidth();
        int h2 = img2.getHeight();
// 从图片中读取RGB
        int[] ImageArrayOne = new int[w1 * h1];
        ImageArrayOne = img1.getRGB(0, 0, w1, h1, ImageArrayOne, 0, w1); // 逐行扫描图像中各个像素的RGB到数组中
        int[] ImageArrayTwo = new int[w2 * h2];
        ImageArrayTwo = img2.getRGB(0, 0, w2, h2, ImageArrayTwo, 0, w2);
// 生成新图片
        BufferedImage DestImage = null;
        if (isHorizontal) { // 水平方向合并
// DestImage = new BufferedImage(w1+w2, h1, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = null;
            if (h1 >= h2) {
                DestImage = new BufferedImage(w1 + w2, h1, BufferedImage.TYPE_INT_RGB);
                g2d = DestImage.createGraphics();
                g2d.setPaint(Color.WHITE);
                g2d.fillRect(0, 0, w1 + w2, h1);
                g2d.dispose();
            } else {
                DestImage = new BufferedImage(w2, h1, BufferedImage.TYPE_INT_RGB);//TYPE_INT_RGB
                g2d = DestImage.createGraphics();
                g2d.setPaint(Color.WHITE);
                g2d.fillRect(0, 0, w2 + w1, h1);
                g2d.dispose();
            }
            DestImage.setRGB(0, 0, w1, h1, ImageArrayOne, 0, w1); // 设置上半部分或左半部分的RGB
            DestImage.setRGB(w1, 0, w2, h2, ImageArrayTwo, 0, w2);
        } else { // 垂直方向合并
            Graphics2D g2d = null;
            if (w1 >= w2) {
                DestImage = new BufferedImage(w1, h1 + h2, BufferedImage.TYPE_INT_RGB);//TYPE_INT_RGB
                g2d = DestImage.createGraphics();
                g2d.setPaint(Color.WHITE);
                g2d.fillRect(0, 0, w1 + w2, h1 + h2);
                g2d.dispose();
            } else {
                DestImage = new BufferedImage(w2, h1 + h2, BufferedImage.TYPE_INT_RGB);//TYPE_INT_RGB
                g2d = DestImage.createGraphics();
                g2d.setPaint(Color.WHITE);
                g2d.fillRect(0, 0, w2 + w1, h1 + h2);
                g2d.dispose();
            }
            DestImage.setRGB(0, 0, w1, h1, ImageArrayOne, 0, w1); // 设置上半部分或左半部分的RGB
            DestImage.setRGB(0, h1, w2, h2, ImageArrayTwo, 0, w2); // 设置下半部分的RGB
        }
        return DestImage;
    }
    //随机生成图片名称
    public static String createImageName() {
// 创建 GUID 对象
        UUID uuid = UUID.randomUUID();
// 得到对象产生的ID
        String a = uuid.toString();
// 转换为大写
        a = a.toUpperCase();
// 替换 -
        a = a.replaceAll("-", "");
        return a;
    }
    public static boolean saveImage(BufferedImage savedImg, String saveDir, String fileName, String format) {
        boolean flag = false;
// 先检查保存的图片格式是否正确
        String[] legalFormats = { "jpg", "JPG", "png", "PNG", "bmp", "BMP" };
        int i = 0;
        for (i = 0; i < legalFormats.length; i++) {
            if (format.equals(legalFormats[i])) {
                break;
            }
        }
        if (i == legalFormats.length) { // 图片格式不支持
            System.out.println("不是保存所支持的图片格式!");
            return false;
        }
// 再检查文件后缀和保存的格式是否一致
        String postfix = fileName.substring(fileName.lastIndexOf('.') + 1);
        if (!postfix.equalsIgnoreCase(format)) {
            System.out.println("待保存文件后缀和保存的格式不一致!");
            return false;
        }
        String fileUrl = saveDir + fileName;
        File file = new File(fileUrl);
        try {
            flag = ImageIO.write(savedImg, format, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return flag;
    }
}
