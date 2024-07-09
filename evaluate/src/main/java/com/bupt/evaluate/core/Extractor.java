package com.bupt.evaluate.core;

import android.graphics.Bitmap;

import com.bupt.evaluate.data.Contours;
import com.bupt.evaluate.data.Points;
import com.bupt.evaluate.data.Stroke;
import com.bupt.evaluate.data.Strokes;
import com.bupt.evaluate.util.Constants;
import com.bupt.evaluate.util.ImageDrawer;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.ximgproc.Ximgproc;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

//汉字特征提取器
public class Extractor {

    //绘制笔画提取结果，用于预览提取效果
    public static Bitmap drawStrokes(String className, Bitmap inputBmp) {
        //此方法设置了超时时间
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<Bitmap> future = executor.submit(() -> {
            //转换图像格式
            Mat img = new Mat();
            Utils.bitmapToMat(inputBmp, img, true);
            //从图像中提取笔画
            Strokes strokes = getStrokes(className, img.clone());
            //绘制笔画提取结果
            for (Stroke stroke : strokes) {
                if (!stroke.isEmpty()) {
                    if (stroke.isStraight) {
                        //直线拟合
                        stroke.fitLine();
                    } else {
                        //曲线插值
                        stroke.interpolateCurve(stroke.getLength() / Constants.STEP_SIZE + 1);
                    }
                    ImageDrawer.drawStroke(img, stroke, new Scalar(Constants.COLOR_BLUE));
                }
            }
            //转换图像格式
            Bitmap outputBmp = Bitmap.createBitmap(img.cols(), img.rows(), Bitmap.Config.ARGB_8888);
            Utils.matToBitmap(img, outputBmp);
            return outputBmp;
        });
        Bitmap outputBmp = inputBmp;//如果超时则返回原图
        try {
            outputBmp = future.get(1, TimeUnit.SECONDS);//超时时间为1秒
        } catch (ExecutionException | InterruptedException | TimeoutException ignored) {
        } finally {
            executor.shutdownNow();
        }
        return outputBmp;
    }

    //根据提取器类名从图像中提取笔画
    static Strokes getStrokes(String className, Mat img) {
        //预处理图像，细化为骨架
        preprocess(img);
        //从图像中提取特征点
        Points points = Points.mat2Points(img);
        //根据图像和特征点提取轮廓
        Contours contours = Contours.mat2Contours(img, points);
        //根据轮廓和特征点提取汉字笔画
        Strokes strokes = Strokes.extractStrokes(className, contours, points);
        //绘制提取结果，用于测试
        //Imgproc.cvtColor(img, img, Imgproc.COLOR_GRAY2RGB);
        //ImageDrawer.drawPoints(img, points);
        //ImageDrawer.drawContours(img, contours);
        //ImageDrawer.drawStrokes(img, strokes);
        return strokes;
    }

    //预处理图像，细化为骨架，输出图像为单通道Mat
    private static void preprocess(Mat img) {
        Imgproc.cvtColor(img, img, Imgproc.COLOR_RGB2GRAY);//灰度化
        Imgproc.threshold(img, img, 127, 255, Imgproc.THRESH_BINARY_INV);//阈值
        Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(9, 9));
        Imgproc.morphologyEx(img, img, Imgproc.MORPH_OPEN, kernel);//开运算
        Imgproc.morphologyEx(img, img, Imgproc.MORPH_CLOSE, kernel);//闭运算
        Ximgproc.thinning(img, img, Ximgproc.THINNING_ZHANGSUEN);//细化
    }
}
