package com.bupt.inklue.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraControl;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.FocusMeteringAction;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.core.SurfaceOrientedMeteringPointFactory;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;

import com.bupt.evaluate.core.Extractor;
import com.bupt.inklue.R;
import com.bupt.inklue.data.CharData;
import com.bupt.inklue.data.FileManager;
import com.bupt.inklue.data.PracticeData;
import com.bupt.inklue.util.BitmapProcessor;
import com.bupt.inklue.util.FilePathGenerator;
import com.bupt.inklue.util.ResourceDecoder;
import com.bupt.preprocess.Preprocessor;
import com.google.common.util.concurrent.ListenableFuture;

import org.opencv.core.Scalar;

import java.io.File;
import java.util.concurrent.ExecutionException;

//拍照页面
public class CameraActivity extends AppCompatActivity
        implements View.OnClickListener, View.OnTouchListener {

    private Context context;//环境
    private PracticeData practiceData;//练习数据
    private int position = 0;//当前拍摄的汉字在列表中的位置
    private int savedNum = 0;//已经保存的图像数
    private ImageButton button_shot;//拍照按钮
    private ImageButton button_confirm;//确认按钮
    private ImageButton button_torch;//手电筒按钮
    private PreviewView preview_view;//相机预览视图
    private ImageView imageview_top;//预览视图上层的视图
    private ImageView imageview_previous;//上一张图片预览
    private ImageCapture imageCapture;//图像捕捉器，用于拍照
    private CameraControl cameraControl;//相机控制器，用于对焦
    private Scalar color;//绘制标准汉字的颜色
    private boolean canBack = false;//是否可以回到上张图片的拍摄
    private boolean isTorchOn = false;//手电筒是否开启

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏系统顶部状态栏
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_camera);
        context = this;

        //取得视图
        button_shot = findViewById(R.id.button_shot);
        button_confirm = findViewById(R.id.button_confirm);
        button_torch = findViewById(R.id.button_torch);
        preview_view = findViewById(R.id.preview_view);
        imageview_top = findViewById(R.id.imageview_top);
        imageview_previous = findViewById(R.id.imageview_previous);

        //取得练习数据
        practiceData = (PracticeData) getIntent().getSerializableExtra("practiceData");

        //初始化相机
        initCamera();

        //初始化相机预览
        initPreview();

        //设置预览框的触摸监听器
        preview_view.setOnTouchListener(this);

        //设置按钮的点击监听器
        findViewById(R.id.button_back).setOnClickListener(this);
        button_shot.setOnClickListener(this);
        button_confirm.setOnClickListener(this);
        button_torch.setOnClickListener(this);
        imageview_previous.setOnClickListener(this);
    }

    //点击事件回调
    public void onClick(View view) {
        if (view.getId() == R.id.button_back) {
            finish();
        } else if (view.getId() == R.id.button_shot) {
            if (position < practiceData.charsData.size()) {
                canBack = false;//保存照片期间，不可返回
                takePhoto();//拍照
                position++;
                updateTop();//更新预览上层视图
            } else if (position - savedNum >= 3) {
                //如果正在保存的图片数量过多，显示保存中提示
                Toast.makeText(context, R.string.camera_saving, Toast.LENGTH_SHORT).show();
            }
        } else if (view.getId() == R.id.button_confirm) {
            startConfirmActivity();//启动确认页面
        } else if (view.getId() == R.id.button_torch) {
            if (isTorchOn) {
                cameraControl.enableTorch(false);
                button_torch.setImageResource(R.drawable.ic_torch_on);
                isTorchOn = false;
            } else {
                cameraControl.enableTorch(true);
                button_torch.setImageResource(R.drawable.ic_torch_off);
                isTorchOn = true;
            }
        } else if (view.getId() == R.id.imageview_previous) {
            if (canBack) {
                backToPrevious();//回到上一张图片的拍摄
            }
        }
    }

    //触摸事件回调
    @SuppressLint("ClickableViewAccessibility")//触摸事件不认为是点击事件
    public boolean onTouch(View view, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            SurfaceOrientedMeteringPointFactory factory =
                    new SurfaceOrientedMeteringPointFactory(
                            preview_view.getHeight(), preview_view.getWidth());
            if (view.getId() == R.id.preview_view) {
                FocusMeteringAction action = new FocusMeteringAction.Builder(
                        factory.createPoint(event.getY(), event.getX())).build();
                cameraControl.startFocusAndMetering(action);
            }
        }
        return true;
    }

    //启动确认页面
    private void startConfirmActivity() {
        Intent intent = new Intent();
        intent.setClass(this, ConfirmActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("practiceData", practiceData);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    //初始化相机预览
    private void initPreview() {
        //设置相机预览宽高
        int screenWidth = getResources().getDisplayMetrics().widthPixels;
        int screenHeight = getResources().getDisplayMetrics().heightPixels;
        int topBarHeight = getResources().getDimensionPixelSize(R.dimen.top_bar);
        int bottomBarHeight = getResources().getDimensionPixelSize(R.dimen.camera_bottom_bar);
        int size = Math.min(screenWidth, screenHeight - topBarHeight - bottomBarHeight);
        RelativeLayout preview_layout = findViewById(R.id.layout_preview);
        preview_layout.getLayoutParams().width = size;
        preview_layout.getLayoutParams().height = size;
        //关闭硬件加速
        imageview_top.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        //获取绘制标准汉字的颜色
        color = ResourceDecoder.getScalar(this, R.attr.colorTheme);
        //更新预览上层视图
        updateTop();
    }

    //更新预览上层视图
    private void updateTop() {
        if (position < practiceData.charsData.size()) {
            //取得标准汉字半透明图像
            Bitmap bitmap = BitmapProcessor.toTransparent(
                    practiceData.charsData.get(position).getStdImgPath(), color);
            imageview_top.setImageBitmap(bitmap);
        } else {
            imageview_top.setImageBitmap(null);//拍摄完最后一张，不再显示上层图片
        }
    }

    //更新上一张图片预览
    private void updatePrevious() {
        //绘制笔画提取结果，用于预览提取效果
        CharData charData = practiceData.charsData.get(savedNum);
        Bitmap bitmapWritten = BitmapFactory.decodeFile(charData.getWrittenImgPath());
        Bitmap bitmapExtract = Extractor.drawStrokes(charData.getClassName(), bitmapWritten);
        imageview_previous.setImageBitmap(bitmapExtract);
        //保存提取结果图片，以备后续使用
        String extractImgPath = FilePathGenerator.generateCacheJPG(context);
        FileManager.saveBitmap(bitmapExtract, extractImgPath);
        charData.setExtractImgPath(extractImgPath);
    }

    //回到上一张图片的拍摄
    private void backToPrevious() {
        //如果已经保存完最后一张，隐藏确认按钮，显示拍照按钮
        if (savedNum == practiceData.charsData.size()) {
            button_shot.setVisibility(View.VISIBLE);
            button_confirm.setVisibility(View.GONE);
        }
        position--;
        savedNum--;
        //如果当前拍摄的是第一张，则不可返回
        if (position == 0) {
            canBack = false;
        }
        //更新预览上层视图
        Bitmap bitmapTop = BitmapProcessor.toTransparent(
                practiceData.charsData.get(position).getStdImgPath(), color);
        imageview_top.setImageBitmap(bitmapTop);
        //更新上一张图片预览
        Bitmap bitmapPrevious = null;
        if (savedNum > 0) {
            bitmapPrevious = BitmapFactory.decodeFile(
                    practiceData.charsData.get(savedNum - 1).getExtractImgPath());
        }
        imageview_previous.setImageBitmap(bitmapPrevious);
    }

    //初始化相机
    private void initCamera() {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture =
                ProcessCameraProvider.getInstance(this);
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                Preview preview = new Preview.Builder().build();
                CameraSelector cameraSelector = new CameraSelector.Builder().
                        requireLensFacing(CameraSelector.LENS_FACING_BACK).build();
                preview.setSurfaceProvider(preview_view.getSurfaceProvider());
                imageCapture = new ImageCapture.Builder()
                        .build();
                cameraControl = cameraProvider.bindToLifecycle(
                                this, cameraSelector, preview, imageCapture).
                        getCameraControl();
            } catch (ExecutionException | InterruptedException ignored) {
            }
        }, ContextCompat.getMainExecutor(this));
    }

    //拍照
    private void takePhoto() {
        String writtenImgPath = FilePathGenerator.generateCacheJPG(this);
        File photoFile = new File(writtenImgPath);
        ImageCapture.OutputFileOptions outputFileOptions = new ImageCapture.OutputFileOptions
                .Builder(photoFile)
                .build();
        imageCapture.takePicture(outputFileOptions, ContextCompat.getMainExecutor(this),
                new ImageCapture.OnImageSavedCallback() {
                    public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                        if (savedNum < practiceData.charsData.size()) {
                            CharData charData = practiceData.charsData.get(savedNum);
                            //设置书写图片路径
                            charData.setWrittenImgPath(writtenImgPath);
                            //预处理书写图片
                            Bitmap bitmapWritten = BitmapFactory.decodeFile(writtenImgPath);
                            Bitmap bitmapStd = BitmapFactory.decodeFile(charData.getStdImgPath());
                            Bitmap bitmapProc = Preprocessor.preprocess(bitmapWritten, bitmapStd);
                            FileManager.saveBitmap(bitmapProc, writtenImgPath);
                            //更新上一张图片预览
                            updatePrevious();
                            savedNum++;
                            canBack = true;
                            //如果保存完最后一张，隐藏拍照按钮，显示确认按钮
                            if (savedNum == practiceData.charsData.size()) {
                                button_shot.setVisibility(View.INVISIBLE);
                                button_confirm.setVisibility(View.VISIBLE);
                            }
                        }
                    }

                    public void onError(@NonNull ImageCaptureException exception) {
                        Toast.makeText(context, R.string.camera_error, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
