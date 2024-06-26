package com.bupt.evaluate.util;

//常量类
public class Constants {
    //笔画评价标准，与评分正相关
    public static final int LINEARITY_CRITERION = 20;//笔画平直度评价标准
    public static final int LENGTH_CRITERION = 20;//笔画长度评价标准
    public static final int ANGLE_CRITERION = 15;//笔画倾角评价标准
    public static final int POSITION_CRITERION = 50;//笔画位置评价标准
    public static final int SIMILARITY_CRITERION = 15;//笔画相似度评价标准

    //笔画评价常量
    public static final int MIN_SCORE = 70;//需要提供建议的最低分数
    public static final int MIN_OFFSET = 10;//需要提供位置建议的最小偏移量

    //笔画提取常量
    public static final int IMAGE_SIZE = 512;//汉字图片的边长
    public static final int MAX_DISTANCE = 8;//可以认为两点是同一点的最大距离
    public static final int MAX_ANGLE = 5;//可以认为某点是折返点的最大角度
    public static final int STEP_SIZE = 30;//曲线插值的步长

    //图形绘制常量
    public static final int THICKNESS = 3;//绘制线条的宽度
    public static final double[] COLOR_RED = {255, 0, 0};//红色
    public static final double[] COLOR_GREEN = {0, 255, 0};//绿色
    public static final double[] COLOR_BLUE = {0, 200, 255};//蓝色
    public static final double[] COLOR_YELLOW = {255, 255, 0};//黄色
}
