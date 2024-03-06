package com.bupt.evaluate.utils;

//转码器
public class Transcoder {

    //将UTF编码转为Unicode码点字符串，其中字母为大写
    public static String UTF2CodePoint(String UTF) {
        StringBuilder codePoint = new StringBuilder();
        for (char c : UTF.toCharArray()) {
            codePoint.append("U").append(String.format("%04X", (int) c));
        }
        return codePoint.toString();
    }
}
