package org.example.show;

import org.example.bean.FileConst;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageCompressor {

    public static void main(String[] args) {
        String sourceDir = FileConst.QRCODE_FILE;
        String outputDir = FileConst.QRCODE_FILE_COMPRESSOR;
        File dir = new File(sourceDir);
        if (!dir.exists() || !dir.isDirectory()) {
            System.err.println("源目录不存在或不是一个目录");
            return;
        }

        File[] files = dir.listFiles((d, name) -> name.toLowerCase().endsWith(".jpg") || name.toLowerCase().endsWith(".jpeg") || name.toLowerCase().endsWith(".png"));
        if (files == null || files.length == 0) {
            System.err.println("目录中没有找到图片文件");
            return;
        }

        for (File file : files) {
            try {
                compressImage(file, outputDir);
            } catch (IOException e) {
                System.err.println("压缩图片时发生错误: " + file.getName());
                e.printStackTrace();
            }
        }
    }

    private static void compressImage(File inputFile, String outputDir) throws IOException {
        BufferedImage image = ImageIO.read(inputFile);
        int newWidth = image.getWidth() / 2;
        int newHeight = image.getHeight() / 2;
        Image scaledImage = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);

        BufferedImage outputImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = outputImage.createGraphics();
        g2d.drawImage(scaledImage, 0, 0, null);
        g2d.dispose();

        String outputFilePath = outputDir + "/" + inputFile.getName();
        ImageIO.write(outputImage, "png", new File(outputFilePath));
        System.out.println("压缩图片并保存到: " + outputFilePath);
    }
}
