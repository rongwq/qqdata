package com.rong.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;

public class TxtExportUtil {
	public static boolean writeTxtFile(String content, File file) throws Exception {
		RandomAccessFile mm = null;
		boolean flag = false;
		FileOutputStream o = null;
		try {
			o = new FileOutputStream(file);
			o.write(content.getBytes("UTF-8"));
			o.close();
			flag = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (mm != null) {
				mm.close();
			}
		}
		return flag;
	}

	/**
	 * 创建文件
	 * @param fileName
	 * @return
	 */
	public static boolean createFile(File file) throws Exception {
		boolean flag = false;
		try {
			if (!file.exists()) {
				file.createNewFile();
				flag = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
}
