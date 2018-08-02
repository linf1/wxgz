package com.base.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class FileNewName {
	 /**
     * 文件重命名
     * @param
     * @return
     */
	public static String gatFileName(){
		StringBuilder str = new StringBuilder();
		str.append(System.currentTimeMillis());
		str.append(new Random().nextInt(1000));
		return str.toString();
}
	/*public static void main(String[] args) {
		System.out.println(gatFileName());
	}*/
}
