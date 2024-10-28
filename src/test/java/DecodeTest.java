import org.example.bean.FileConst;
import org.example.clean.QRClean;
import org.example.decode.CheckIfLose_step02;
import org.example.decode.TestQRDecode;
import org.example.gen.QrcodeGen;
import org.example.merge.Merge_step03;

public class DecodeTest {


    public static void main(String[] args) throws Exception {
//        gen(args);
//        QRClean.resort(FileConst.QRCODE_FILE);
        QRClean.delFile = false;
        clean(args);
//        decode(args);
//        check(args);
//        merge(args);
    }

    public static void gen(String[] args) throws Exception {
        QrcodeGen.main(args);
    }
    public static void decode(String[] args) throws Exception {
        TestQRDecode.main(args);
    }

    public static void check(String[] args) throws Exception {
        CheckIfLose_step02.main(args);
    }

    public static void merge(String[] args) throws Exception {
        Merge_step03.main(args);
    }

    public static void clean(String[] args) throws Exception {
        QRClean.main(args);
    }
}
