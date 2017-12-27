package com.rong.common.util;

import java.io.BufferedOutputStream;
import java.util.List;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

public class TxtExportUtil {
	    // 输出TXT  
	    public static void writeToTxt(HttpServletResponse response, List<Object> list) {  
	        response.setContentType("text/plain");// 一下两行关键的设置  
	        response.addHeader("Content-Disposition",  
	                "attachment;filename=期刊出版社.txt");// filename指定默认的名字  
	        BufferedOutputStream buff = null;  
	        StringBuffer write = new StringBuffer();  
	        String tab = "  ";  
	        String enter = "\r\n";  
	        ServletOutputStream outSTr = null;  
	        try {  
	            outSTr = response.getOutputStream();// 建立  
	            buff = new BufferedOutputStream(outSTr);  
	            for (int i = 0; i < list.size(); i++) {  
	                write.append("期刊名称：" + tab);  
	                write.append(enter);          
	            }  
	            buff.write(write.toString().getBytes("UTF-8"));  
	            buff.flush();  
	            buff.close();  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        } finally {  
	            try {  
	                buff.close();  
	                outSTr.close();  
	            } catch (Exception e) {  
	                e.printStackTrace();  
	            }  
	        }  
	    }  
	}  
