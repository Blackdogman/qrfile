package org.example.gen;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.thread.ExecutorBuilder;
import cn.hutool.core.util.PageUtil;
import cn.hutool.core.util.StrUtil;
import org.example.bean.FileConst;
import org.example.encode.QrcodeEncode;
import org.example.seeting.QrcodeSetting;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

public class QrcodeGen {

    /**
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        String zipFile = FileConst.ORIGIN_FILE;

        String qrcodePath = FileConst.QRCODE_FILE;

        InputStream inputStream = new FileInputStream(zipFile);

        byte[] dataBytes = IoUtil.readBytes(inputStream);

        if(dataBytes == null || dataBytes.length == 0){
            System.out.println("数据为空");
            return;
        }
        // 转32进制
        String dataStr = Base64.encode(dataBytes);

        File dest = new File(qrcodePath);

        if(dest.exists() == false){
            dest.mkdirs();
        }
        ThreadPoolExecutor executor = ExecutorBuilder.create().setCorePoolSize(16).setMaxPoolSize(Integer.MAX_VALUE).build();
        // 生成的文件总数
        int totalFile = PageUtil.totalPage(dataStr.length(), QrcodeSetting.QR_DATA_LENGTH);

        List<Integer> errors = new ArrayList<>();

        for(int i = 1; i <= totalFile; i++){
            final int currentNum = i;
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        saveToFile(currentNum, dataStr, totalFile, qrcodePath);
                    } catch (Exception e) {
                        e.printStackTrace();
                        errors.add(currentNum);
                    }
                }
            });
        }
        if(errors.size() > 0){
            System.out.println(Arrays.toString(errors.toArray()));
        }
        executor.shutdown();
    }

    public static void saveToFile(int currentNum, String dataStr, int totalFile, String destPath) throws Exception {
        int start = currentNum - 1;
        if(start < 0){
            start = 0;
        }
        start = start * QrcodeSetting.QR_DATA_LENGTH;
        int end = currentNum * QrcodeSetting.QR_DATA_LENGTH + 1;
        if(end > dataStr.length()){
            end = dataStr.length();
        }
        System.out.println("current file = " + currentNum + " total file numbers = " + totalFile);
        String data = dataStr.substring(start, end);

        String fileName = currentNum + ".png";
//        String fileName = "QR" + StrUtil.padPre(String.valueOf(currentNum), QrcodeSetting.QR_NAME_LENGTH, "0") + ".png";

        data = (new StringBuffer()).append(currentNum)
                .append("|").append( totalFile).append(FileConst.SEPARATOR).append(data).toString();

        ByteArrayOutputStream output = QrcodeEncode.encode(data, null);

        String destFile = destPath + "\\" + fileName;

        FileUtil.writeBytes(output.toByteArray(), destFile);
    }
}
