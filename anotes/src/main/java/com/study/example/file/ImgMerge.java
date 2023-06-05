package com.study.example.file;

import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdmodel.PDDocument;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ImgMerge {

    public static void main(String[] args) {
        try {
            System.out.println("file=" + jpgsToJpg("ceshi", "E:\\workspace_idea\\studyNotes\\1.jpg", "E:\\workspace_idea\\studyNotes\\2.jpg"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 功能描述：多个jpg文件合并成一个jpg到本地
     *
     * @author songchengye
     * @date 2022/4/7 下午2:26
     */
    public static File jpgsToJpg(String newFileName, String... oldFileUrls) throws IOException {
        FileOutputStream outStream = null;
        PDDocument doc = null;
        InputStream inStream = null;
        byte[] bytes = null;
        try {
            //得到图片的二进制数据，以二进制封装得到数据，具有通用性
            List<BufferedImage> bufferedImages = new ArrayList<>();
            for (int i = 0; i < oldFileUrls.length; i++) {
                String oldFileUrl = oldFileUrls[i];
                //new一个URL对象
                URL url = new URL(oldFileUrl);
                //打开链接
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                //设置请求方式为"GET"
                conn.setRequestMethod("GET");
                //超时响应时间为5秒
                conn.setConnectTimeout(5 * 1000);
                //通过输入流获取图片数据
                inStream = conn.getInputStream();
                bufferedImages.add(ImageIO.read(inStream));
            }
            //合并多张图片为1张
            BufferedImage bufferedImage = mergeImage(false, bufferedImages);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpg", byteArrayOutputStream);
            bytes = byteArrayOutputStream.toByteArray();
            //指定文件路径
            String path = System.getProperty("user.dir") + "-" + newFileName + ".jpg";
            log.info("path=" + path);
            File jpgFile = new File(path);
            //为读取文件提供输出流通道
            outStream = new FileOutputStream(jpgFile);
            //写入数据
            outStream.write(bytes);
            return jpgFile;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭输出流
            if (outStream != null) {
                outStream.close();
            }
            //关闭输入流
            if (inStream != null) {
                inStream.close();
            }
            if (doc != null) {
                doc.close();
            }
        }
        return null;
    }

    /**
     * 合成任意数量的图片
     *
     * @param isHorizontal
     * @param imgs
     * @return
     * @throws IOException
     */
    public static BufferedImage mergeImage(boolean isHorizontal, List<BufferedImage> imgs) throws IOException {
        // 生成新图片
        BufferedImage DestImage = null;
        // 计算新图片的长和高
        int allw = 0, allh = 0, allwMax = 0, allhMax = 0;
        for (BufferedImage img : imgs) {
            allw += img.getWidth();
            allh += img.getHeight();
            if (img.getWidth() > allwMax) {
                allwMax = img.getWidth();
            }
            if (img.getHeight() > allhMax) {
                allhMax = img.getHeight();
            }
        }
        // 创建新图片
        if (isHorizontal) {
            DestImage = new BufferedImage(allw, allhMax, BufferedImage.TYPE_INT_RGB);
        } else {
            DestImage = new BufferedImage(allwMax, allh, BufferedImage.TYPE_INT_RGB);
        }

        // 合并所有子图片到新图片
        int wx = 0, wy = 0;
        for (BufferedImage img : imgs) {
            int w1 = img.getWidth();
            int h1 = img.getHeight();
            // 从图片中读取RGB
            int[] ImageArrayOne = new int[w1 * h1];
            ImageArrayOne = img.getRGB(0, 0, w1, h1, ImageArrayOne, 0, w1); // 逐行扫描图像中各个像素的RGB到数组中
            if (isHorizontal) { // 水平方向合并
                DestImage.setRGB(wx, 0, w1, h1, ImageArrayOne, 0, w1); // 设置上半部分或左半部分的RGB
            } else { // 垂直方向合并
                DestImage.setRGB(0, wy, w1, h1, ImageArrayOne, 0, w1); // 设置上半部分或左半部分的RGB
            }
            wx += w1;
            wy += h1;
        }
        return DestImage;
    }

}
