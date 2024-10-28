package org.example.decode;

import org.example.bean.FileConst;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

public class CheckIfLose_step02 {

	/**
	 * 检查生成的文本文件是否有缺少行
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception{
		int allSize = FileConst.QRCODE_NUM;
		BufferedReader br = new BufferedReader(new FileReader(new File(FileConst.BASE64_TXT)));
		String line = null;
		Map<String,String> data = new HashMap();
		int lose = 0;
		while((line = br.readLine())!=null) {
			//System.out.println(line);
			if(!line.contains(FileConst.SEPARATOR))continue;
			String [] arr = line.split(FileConst.SEPARATOR);
			data.put(arr[0], arr[1]);
		}
		br.close();
		for(int i=1;i<=allSize;i++) {
			if(!data.containsKey(i+"|" + allSize)) {
				System.out.println("----------"+i);
				lose++;
			}
		}
		System.out.println("ȱ:"+lose);
	}

}
