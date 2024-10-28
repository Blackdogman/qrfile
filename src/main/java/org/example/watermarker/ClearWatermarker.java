package org.example.watermarker;

import cn.hutool.core.util.StrUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;

public class ClearWatermarker {

    public static OutputStream clear(String filePath) throws Exception{
        if(StrUtil.isBlank(filePath)){
            throw new RuntimeException("路径不能为空");
        }
        File file = new File(filePath);

        if(file == null || file.exists() == false){
            throw new RuntimeException("文件不存在");
        }
        if(file.isDirectory() == true){
            throw new RuntimeException("路径非目录");
        }
        BufferedImage image = ImageIO.read(file);

        return clear(image);
    }

    public static OutputStream clear(BufferedImage image) throws Exception{
        int width = image.getWidth();
        int height = image.getHeight();
        for (int x = 0; x < width;x++) {
            for (int y = 0; y < height; y++) {
                int rgb = image.getRGB(x, y);
                if(rgb>=-800000) {
                    image.setRGB(x, y, -1);
                }
            }
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        ImageIO.write(image, "PNG",outputStream);

        return outputStream;
    }
}
