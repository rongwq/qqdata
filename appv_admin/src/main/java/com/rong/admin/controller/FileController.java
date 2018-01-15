package com.rong.admin.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;

import com.jfinal.core.Controller;
import com.jfinal.kit.PathKit;
import com.jfinal.upload.UploadFile;
import com.rong.common.bean.MyConst;
import com.rong.common.util.FtpUtil;
import com.rong.common.util.ImageResizer;

/**
 * 文件上传
 * @author Wenqiang-Rong
 * @date 2018年1月15日
 */
public class FileController extends Controller {
	
	public void upload() {
		String webPath = PathKit.getWebRootPath();
		Integer size = 10 * 1024 * 1024;
		String upload = MyConst.ftp_files.substring(1, MyConst.ftp_files.length() - 1);
		String filePath = webPath.substring(0, webPath.lastIndexOf(File.separator) + 1) + upload;
		UploadFile uploadFile = this.getFiles(filePath, size, "utf-8").get(0);
		String fileName = uploadFile.getFileName();
		File file = uploadFile.getFile();
		//上传ftp
		int random = new Random().nextInt(89999999) + 10000000;
		String rename = System.currentTimeMillis() + "" + random ;
		rename = rename + fileName.substring(fileName.lastIndexOf("."));
		long fileSize = file.length();
		FtpUtil.uploadFiles(rename, file);
		String url = MyConst.imgUrlHead + MyConst.ftp_files + rename;
		deletes(fileName);
		file.delete();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("url", url);
		map.put("size", fileSize);
		renderJson("file", map);
	}
	
	
	public void uploadImg() {
		String webPath = PathKit.getWebRootPath();
		Integer size = 10 * 1024 * 1024;
		String upload = MyConst.ftp_imgs.substring(1, MyConst.ftp_imgs.length() - 1);
		String filePath = webPath.substring(0, webPath.lastIndexOf(File.separator) + 1) + upload;
		UploadFile uploadFile = this.getFiles(filePath, size, "utf-8").get(0);
		String fileName = uploadFile.getFileName();
		String path = filePath + File.separator + fileName;
		File file = uploadFile.getFile();
		//上传ftp
		int random = new Random().nextInt(89999999) + 10000000;
		String rename = System.currentTimeMillis() + "" + random ;
		rename = rename + fileName.substring(fileName.lastIndexOf("."));
		long fileSize = file.length();
		try {
			// 判断是否是图片类型文件，如果不是则要删除文件，并返回提示
			// 因为在上传时使用了uploadFile，已经加载进了文件，所以只能使用删除方式
			BufferedImage image = ImageIO.read(file);
			if (image == null) {
				file.delete();
				renderJson("error", true);
				return;
			}
			if (fileSize > 1 * 1024 * 1024) {
				ImageResizer.resizep(path, path, 0.2);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		FtpUtil.uploadFiles(rename, file,"img");
		String imgPath = MyConst.imgUrlHead + MyConst.ftp_imgs + rename;
		deletes(fileName);
		file.delete();
		renderJson("imgPath", imgPath);
	}
	
	public void deletes(String fileName) {
		if (fileName.lastIndexOf("/") != -1) {			
			fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
		}
		String webPath = PathKit.getWebRootPath();
		String upload = MyConst.ftp_imgs.substring(1, MyConst.ftp_imgs.length() - 1);
		String filePath = webPath.substring(0, webPath.lastIndexOf(File.separator) + 1) + upload;
		File file = new File(filePath + File.separator + fileName);
		if (file.exists()) {
			if(file.delete()) {
				renderJson("result", true);
			} else {
				renderJson("result", false);
			}
		} else {
			renderJson("result", true);
		}
	}
	
	

	public void delete() {
		String fileName = getPara("fileName");
		if (fileName.lastIndexOf("/") != -1) {			
			fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
		}
		
		if(FtpUtil.deleteFile(fileName)) {
			renderJson("result", true);
		} else {
			renderJson("result", false);
		}
	}
}
