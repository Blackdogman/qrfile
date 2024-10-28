package org.example.decode;

import boofcv.abst.fiducial.QrCodeDetector;
import boofcv.alg.fiducial.qrcode.QrCode;
import boofcv.factory.fiducial.ConfigQrCode;
import boofcv.factory.fiducial.FactoryFiducial;
import boofcv.io.UtilIO;
import boofcv.io.image.ConvertBufferedImage;
import boofcv.io.image.UtilImageIO;
import boofcv.struct.image.GrayU8;
import org.example.bean.FileConst;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;

public class TestQRDecode {
    /**
     * 读取二维码，转化为Base64编码值生成文本文件
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception{
        String path = FileConst.QRCODE_FILE;
        File dir = new File(path);
        //文件命名注意一下，要按顺序
        File[] qrFiles = dir.listFiles();
        PrintWriter pw = new PrintWriter(new FileWriter(FileConst.BASE64_TXT));
        int x = 0;
        int all = 0;
        String preText = "";
        for (int i = 0; i < qrFiles.length; i++) {
            String text = deEncodeByPath(qrFiles[i].getAbsolutePath());
            all++;
            if (text != null) {
                if(text.equals(preText)) {
                    System.out.println("image " + qrFiles[i].getName() + " is same to " + qrFiles[i - 1].getName());
                    qrFiles[i].delete();
                    continue;
                }
                pw.println(text);
                System.out.println(qrFiles[i].getName());
				qrFiles[i].delete();
                x++;
                preText = text;
            }
        }
        System.out.println("it works on "+x+" out of "+all);
        pw.flush();
        pw.close();
    }
    /**
     * 解析二维码,此方法解析一个路径的二维码图片
     * path:图片路径
     */
    public static String deEncodeByPath(String path) {
        BufferedImage input = UtilImageIO.loadImageNotNull(UtilIO.pathExample(path));
        GrayU8 gray = ConvertBufferedImage.convertFrom(input, (GrayU8)null);

        ConfigQrCode config = new ConfigQrCode();
//		config.considerTransposed = false; // by default, it will consider incorrectly encoded markers. Faster if false
        QrCodeDetector<GrayU8> detector = FactoryFiducial.qrcode(config, GrayU8.class);

        detector.process(gray);

        // Gets a list of all the qr codes it could successfully detect and decode
        List<QrCode> detections = detector.getDetections();
        if(detections!=null&&!detections.isEmpty()) {
            return detections.get(0).message;
        }
        return null;
    }

}
