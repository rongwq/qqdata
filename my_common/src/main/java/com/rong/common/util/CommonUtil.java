package com.rong.common.util;

import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.List;

public class CommonUtil {

	public static String createGwGroupId(Integer groupId){
		//月份+年份+该群的序号
		Calendar cal = Calendar.getInstance();
		String month = String.valueOf(cal.get(Calendar.MONTH) + 1);
		String year =  String.valueOf(cal.get(Calendar.YEAR)).substring(2);
		String gwGroupId = month+year+groupId.toString();
		 return gwGroupId;
	}
	
	public static String createGwGroupName(String groupName){
		Long now = System.currentTimeMillis();
		StringBuffer sb = new StringBuffer();
		sb.append(groupName);
		sb.append(now);
		return sb.toString();
	}
		
	/**
	 * 讲list转换成带,分隔的字符串
	 * @param stringList
	 * @return
	 */
	public static String listToString(List<String> stringList){
        if (stringList==null) {
            return null;
        }
        StringBuilder result=new StringBuilder();
        boolean flag=false;
        for (String string : stringList) {
            if (flag) {
                result.append(",");
            }else {
                flag=true;
            }
            result.append(string);
        }
        return result.toString();
    }
	
	/**
	 * 重写线程等待方法
	 * @param time
	 */
	public static void sleep(long time){
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 默认等待2秒
	 */
	public static void sleep(){
		sleep(3000);
	}

	/**
	 * md5加密
	 * @param src
	 * @return
	 */
	public static String getMD5(String src) {
		StringBuffer sb = new StringBuffer();
		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			md.update(src.getBytes());
			for (byte b : md.digest())
				sb.append(Integer.toString(b >>> 4 & 0xF, 16)).append(
						Integer.toString(b & 0xF, 16));
		} catch (NoSuchAlgorithmException e) {
		}
		return sb.toString();
	}
	
	/**
	 * 校验入库qq格式
	 * 
	 * @param qqType
	 * @param qqData
	 * 格式如下：
	 * 白号 2383088706----xzzqt11480 
	 * 三问号 2383088706----xzzqt11480----您母亲的姓名是？----uesxvm----您高中班主任的名字是？----unojku----您父亲的姓名是？----uyzkox 
	 * 绑机号 2383088706----xzzqt11480----您母亲的姓名是？----uesxvm----您高中班主任的名字是？----unojku----您父亲的姓名是？----uyzkox----15243834134 
	 * 令牌号 2383088706----xzzqt11480----您母亲的姓名是？----uesxvm----您高中班主任的名字是？----unojku----您父亲的姓名是？----uyzkox----15243834134----token
	 * 181203补充格式
	 * 令牌号格式是：QQ号码----密码----手机号码----令牌码
	 * 绑定手机号：        QQ号码----密码----手机号码
	 * @return
	 */
	public static boolean validQqData(String qqData) {
		String qqDataStrs[] = qqData.split("\n");
		for (int i = 0; i < qqDataStrs.length; i++) {
			String vals[] = qqDataStrs[i].split("----");
			if (!(vals.length ==2 ||vals.length ==3 ||vals.length ==4 || vals.length==8 || vals.length==9 || vals.length==10)) {
				return false;
			}
		}
		return true;
	}
	
	public static boolean validQqData4Api(String qqData) {
		String qqDataStrs[] = qqData.split(";");
		for (int i = 0; i < qqDataStrs.length; i++) {
			String vals[] = qqDataStrs[i].split("-");
			if (!(vals.length ==2 ||vals.length ==3 ||vals.length ==4 || vals.length==8 || vals.length==9 || vals.length==10)) {
				return false;
			}
		}
		return true;
	}
}
