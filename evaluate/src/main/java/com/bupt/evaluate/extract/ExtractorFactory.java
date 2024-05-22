package com.bupt.evaluate.extract;

//笔画提取器工厂
class ExtractorFactory {

    //创建指定的笔画提取器实例
    static SpecificExtractor createInstance(String className) {
        //取得提取器类名
        String fullClassName = "com.bupt.evaluate.extract.specific_extractor." + className;
        try {
            Class<?> clazz = Class.forName(fullClassName);
            return (SpecificExtractor) clazz.newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            return null;
        }
    }
}
