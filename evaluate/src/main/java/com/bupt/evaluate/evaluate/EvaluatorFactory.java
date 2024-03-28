package com.bupt.evaluate.evaluate;

//笔画特征评价器工厂
public class EvaluatorFactory {

    //创建指定的笔画特征评价器实例
    static FeatureEvaluator createInstance(int code) {
        //取得提取器类名
        String fullClassName = "com.bupt.evaluate.evaluate.feature_evaluator." + getName(code) + "Evaluator";
        try {
            Class<?> clazz = Class.forName(fullClassName);
            return (FeatureEvaluator) clazz.newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            return null;
        }
    }

    //根据笔画特征评价器编码获取类名，无匹配则返回空字符串
    private static String getName(int code) {
        switch (code) {
            case EvaluationBuilder.ANGLE:
                return "Angle";
            case EvaluationBuilder.LENGTH:
                return "Length";
            case EvaluationBuilder.LINEARITY:
                return "Linearity";
            case EvaluationBuilder.POSITION:
                return "Position";
            case EvaluationBuilder.SIMILARITY:
                return "Similarity";
        }
        return "";
    }
}
