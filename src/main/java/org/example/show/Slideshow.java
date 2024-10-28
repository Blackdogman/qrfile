package org.example.show;

import org.example.bean.FileConst;

import javax.swing.*;
import java.awt.*;

public class Slideshow extends JFrame {
    private final JLabel imageLabel;
    private final JLabel filenameLabel;
    private final Timer timer;
    private int currentIndex = 1;
    private final int imageCount = 5000; // 图片数量
    private final String imageFolder = FileConst.QRCODE_FILE;

    public Slideshow() {
        setTitle("自动播放图片");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        imageLabel = new JLabel("", SwingConstants.CENTER);
        filenameLabel = new JLabel("", SwingConstants.RIGHT);
        filenameLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        add(imageLabel, BorderLayout.CENTER);
        add(filenameLabel, BorderLayout.SOUTH);

        // 图片切换间隔（毫秒）
        timer = new Timer(100, e -> showNextImage());
        preloadImages();
        timer.start();
    }

    private void preloadImages() {
        for (int i = 1; i <= imageCount; i++) {
            new ImageIcon(imageFolder + i + ".png");
        }
    }

    private void showNextImage() {
        String imagePath = imageFolder + currentIndex + ".png";
        ImageIcon originalIcon = new ImageIcon(imagePath);
        Image originalImage = originalIcon.getImage();

        // 获取展示框尺寸
        int containerWidth = getWidth();
        int containerHeight = getHeight();

        // 计算缩放后的图片尺寸，保持宽高比
        int newWidth = containerWidth * 90 / 100;
        int newHeight = containerHeight * 90 / 100;
        int originalWidth = originalImage.getWidth(null);
        int originalHeight = originalImage.getHeight(null);
        double scaleFactor = Math.min((double) newWidth / originalWidth, (double) newHeight / originalHeight);

        newWidth = (int) (originalWidth * scaleFactor);
        newHeight = (int) (originalHeight * scaleFactor);

        Image scaledImage = originalImage.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        imageLabel.setIcon(scaledIcon);
        filenameLabel.setText(currentIndex + ".png");

        currentIndex = (currentIndex % imageCount) + 1;
        if (currentIndex == 1) {
            timer.stop();
            JOptionPane.showMessageDialog(this, "图片播放完毕！");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Slideshow().setVisible(true));
    }
}
