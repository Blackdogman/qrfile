package org.example.merge;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.io.FileUtil;
import org.example.bean.FileConst;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Merge_step03 {

	/**
	 * 读取Base64编码，解码生成文件
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args)throws Exception {
		int allSize = FileConst.QRCODE_NUM;
		BufferedReader br = new BufferedReader(new FileReader(new File(FileConst.BASE64_TXT)));
		String line = null;
		Map<String,String> data = new HashMap();
		while((line = br.readLine())!=null) {
			//System.out.println(line);
			if(!line.contains(FileConst.SEPARATOR))continue;
			String [] arr = line.split(FileConst.SEPARATOR);
			data.put(arr[0], arr[1]);
		}
		br.close();

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		for (int i = 1; i <= allSize; i++) {
			String lineStr = data.get(i + "|" + allSize);

			outputStream.write(Base64.decode(lineStr));
		}
		FileUtil.writeBytes(outputStream.toByteArray(),FileConst.DEST_FILE);

//		StringBuffer sb = new StringBuffer("");
//		for(int i=1;i<=allSize;i++) {
//			sb.append(data.get(i+"|" + allSize));
//		}
//		byte [] b = Base64.decode(sb.toString());
//		FileOutputStream fos = new FileOutputStream(new File("D:\\AKATSUGI\\mtd.zip"));
//		fos.write(b);
//		fos.close();
		System.out.println("done");
	}

}
