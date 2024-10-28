package org.example.screen;

import cn.hutool.core.swing.ScreenUtil;
import cn.hutool.core.thread.ThreadUtil;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ScreenCapture1 {

    public static int captureInputNumber() throws TesseractException {
        Rectangle angle = new Rectangle();
        int xPoint = 17;
        int yPoint = 1540;
        int width = 288;
        int height = 33;
        angle.setBounds(xPoint, yPoint, width, height);

        BufferedImage image = ScreenUtil.captureScreen(angle);

        ITesseract instance = new Tesseract();

        String result = instance.doOCR(image);

        System.out.println(result);

        return 0;
    }

    public static void main(String[] args) throws TesseractException {


        while(true){
            captureInputNumber();
            ThreadUtil.sleep(200);
        }
    }
}
