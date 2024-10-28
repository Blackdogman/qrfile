package org.example.seeting;

/**
 * Alphanumeric表示"字母、数字混合"，Binary表示"8位字节数据"（即一个字节），Kanji表示"日本汉字"。
 * Numeric 数据：7089个
 * Alphanumeric 数据：4296个
 * 8位字节数据：2953个
 * Kanji 汉字数据：1817个
 *
 */
public class QrcodeSetting {

    public static final int DEFAULT_WIDTH = 1024;
    public static final int DEFAULT_HEIGHT = 1024;

//    public static  final Map DEFAULT_HINTS = new HashMap();


    public static final int QR_DATA_LENGTH = 2800;

//    static{
//        DEFAULT_HINTS.put(EncodeHintType.CHARACTER_SET, CharacterSetECI.UTF8);
//        DEFAULT_HINTS.put(EncodeHintType.QR_VERSION, 40);
//        DEFAULT_HINTS.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.Q);
//        //优化精度
//        DEFAULT_HINTS.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
//
//    }

}
