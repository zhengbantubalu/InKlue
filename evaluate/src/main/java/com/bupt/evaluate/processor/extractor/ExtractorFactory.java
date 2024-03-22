package com.bupt.evaluate.processor.extractor;

//笔画提取器工厂，用于创建笔画提取器实例(旧方法)
public class ExtractorFactory {

    //创建指定的笔画提取器实例
    public static Extractor createInstance(String cnChar) {
        //取得提取器类名
        String fullClassName = "com.bupt.evaluate.processor.extractor." + UTF2CodePoint(cnChar);
        try {
            Class<?> clazz = Class.forName(fullClassName);
            return (Extractor) clazz.newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            return null;
        }
    }

    //将UTF编码转为Unicode码点字符串，其中字母为大写
    private static String UTF2CodePoint(String UTF) {
        StringBuilder codePoint = new StringBuilder();
        for (char c : UTF.toCharArray()) {
            codePoint.append("U").append(String.format("%04X", (int) c));
        }
        return codePoint.toString();
    }
}
