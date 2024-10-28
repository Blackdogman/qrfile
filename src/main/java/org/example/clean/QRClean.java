package org.example.clean;

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
import java.util.List;

/**
 * 清理相邻\空白重复的二维码
 */
public class QRClean {
    public static boolean delFile = true;

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
        String preText = "";
        int preIndex = 0;
        for (int i = 0; i < qrFiles.length; i++) {
            String text = deEncodeByPath(qrFiles[i].getAbsolutePath());
            if (text != null) {
                if(text.equals(preText)) {
                    System.out.println("image " + qrFiles[i].getName() + " is same to " + qrFiles[preIndex].getName());
                    if(delFile) {
                        qrFiles[i].delete();
                    }
                    continue;
                }
                preIndex = i;
                preText = text;
            }else {
                System.out.println(qrFiles[i].getName() + " is blank file");
                if(delFile) {
                    qrFiles[i].delete();
                }
            }
        }



        System.out.println("============================文件处理完成, 开始重新排序目录文件============================");
        resort(path);
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

    /**
     * 重新把文件从1开始排列
     */
    public static void resort(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists() || !directory.isDirectory()) {
            System.out.println("目标目录不存在或不是一个目录");
            return;
        }

        File[] files = directory.listFiles();
        if (files == null || files.length == 0) {
            System.out.println("目录中没有找到文件");
            return;
        }

        int count = 1;
        for (File file : files) {
            if (file.isFile()) {
                String newFileName = String.format("%06d%s", count++, getFileExtension(file));
                File newFile = new File(directory, newFileName);
                if (file.renameTo(newFile)) {
                    System.out.println("Renamed: " + file.getName() + " -> " + newFileName);
                } else {
                    System.out.println("Failed to rename: " + file.getName());
                }
            }
        }
    }

    private static String getFileExtension(File file) {
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex);
    }
}
