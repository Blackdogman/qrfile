package org.example.screen;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.swing.ScreenUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.StrUtil;

import java.awt.*;

public class ScreenCapture {

    public static void main(String[] args) {

        String imagePath = "F:\\code_img\\images\\010";
        int startSeq = 1;
        Rectangle angle = new Rectangle();
        int xPoint = 85;
        int yPoint = 337;
        int width = 1203 - xPoint;
        int height = 1460 - yPoint;
        angle.setBounds(xPoint, yPoint, width, height);
        FileUtil.mkParentDirs(imagePath);
        FileUtil.mkdir(imagePath);
        while(true){
            String fileName = "QR" + StrUtil.padPre(String.valueOf(startSeq)
                    , 6, "0");

            String file = imagePath + "\\" +  fileName + ".png";

            ScreenUtil.captureScreen(angle, FileUtil.file(file));
            ThreadUtil.sleep(200);
            startSeq ++;
        }
    }
}
