package org.example.encode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.example.bean.FileConst;
import org.example.seeting.QrcodeSetting;

import java.io.ByteArrayOutputStream;
import java.util.Map;

public class QrcodeEncode {

    public static ByteArrayOutputStream encode(String contents) throws Exception{

        return encode(contents, BarcodeFormat.QR_CODE, QrcodeSetting.DEFAULT_WIDTH, QrcodeSetting.DEFAULT_HEIGHT);
    }

    public static ByteArrayOutputStream encode(String contents, Map<EncodeHintType, ?> hints) throws Exception{

        return encode(contents, BarcodeFormat.QR_CODE, QrcodeSetting.DEFAULT_WIDTH, QrcodeSetting.DEFAULT_HEIGHT, hints);
    }

    public static ByteArrayOutputStream encode(String contents, BarcodeFormat format, int width, int height) throws Exception{
        return encode(contents, format, width, height, null);
    }

    public static ByteArrayOutputStream encode(String contents, BarcodeFormat format, int width, int height, Map<EncodeHintType, ?> hints) throws Exception {

        QRCodeWriter writer = new QRCodeWriter();

        BitMatrix bitMatrix = writer.encode(contents, format, width, height,hints);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        MatrixToImageWriter.writeToStream(bitMatrix, FileConst.DEFAULT_FORMAT, outputStream);

        return outputStream;
    }
}
