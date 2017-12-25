package com.rong.common.util;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Model;

/**
 * Created with IntelliJ IDEA.
 * Author: StevenChow
 * Date: 13-4-4
 */
public class HtmlTagKit {
//    private static final Pattern SIMPLE_IMG_PATTERN = Pattern.compile("\\[\\d{2,3}]");
//    private static final String IMG_SRC_FRONT = "<img src='/img/emoticon/";
//    private static final String IMG_SRC_BHIND = ".gif'";

//    public static String restoreImgSrc(String content){
//        Matcher matcher = SIMPLE_IMG_PATTERN.matcher(content);
//        while(matcher.find()){
//            String result = matcher.group();
//            String faceNumber = result.replaceAll("\\[", "").replaceAll("]", "");
//            content = matcher.replaceAll(IMG_SRC_FRONT + faceNumber + IMG_SRC_BHIND);
//        }
//        return content;
//    }

    @SuppressWarnings("rawtypes")
	public static void processHtmlSpecialTag(Model model, String... attrNames){
        for (String attrName : attrNames) {
            String content = model.getStr(attrName);
            if(StrKit.notBlank(content)){
                String temp = content.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
                model.set(attrName, temp);
            }
        }
    }
}
