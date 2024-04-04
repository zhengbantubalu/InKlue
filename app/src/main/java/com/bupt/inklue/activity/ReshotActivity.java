package com.bupt.inklue.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;

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

import com.bupt.inklue.R;
import com.bupt.inklue.data.CardData;
import com.bupt.inklue.util.BitmapProcessor;
import com.bupt.inklue.util.ResourceDecoder;
import com.google.android.material.snackbar.Snackbar;
import com.google.common.util.concurrent.ListenableFuture;

import org.opencv.core.Scalar;

import java.io.File;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

//重拍页面
public class ReshotActivity extends AppCompatActivity
        implements View.OnClickListener, View.OnTouchListener {

    private Context context;//环境
    private CardData cardData;//图像卡片数据
    private ImageButton button_confirm;//“确认”按钮
    private ImageButton button_cancel;//“取消”按钮
    private PreviewView preview_view;//相机预览视图
    private ImageView imageview_above;//预览视图上层的视图
    private ImageCapture imageCapture;//图像捕捉器，用于拍照
    private CameraControl cameraControl;//相机控制器，用于对焦
    private Bitmap stdBitmap;//标准汉字半透明图像
    private ImageButton button_torch;//手电筒开关
    private boolean isTorchOn;//手电筒是否开启

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        //隐藏系统顶部状态栏
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_camera);
        context = this;

        //取得视图
        button_confirm = findViewById(R.id.button_confirm);
        button_cancel = findViewById(R.id.button_cancel);
        preview_view = findViewById(R.id.preview_view);
        imageview_above = findViewById(R.id.imageview_top);
        button_torch = findViewById(R.id.button_torch);

        //加载OpenCV
        System.loadLibrary("opencv_java3");

        //取得图像卡片数据
        cardData = (CardData) getIntent().getSerializableExtra("cardData");

        //初始化相机
        initCamera();

        //初始化预览上层视图
        initImageView();

        //设置预览框的触摸监听器
        preview_view.setOnTouchListener(this);

        //设置按钮的点击监听器
        findViewById(R.id.button_back).setOnClickListener(this);
        findViewById(R.id.button_shot).setOnClickListener(this);
        button_confirm.setOnClickListener(this);
        button_cancel.setOnClickListener(this);
        button_torch.setOnClickListener(this);
    }

    //点击事件回调
    public void onClick(View view) {
        if (view.getId() == R.id.button_back) {
            finish();
        } else if (view.getId() == R.id.button_shot) {
            takePhoto();
        } else if (view.getId() == R.id.button_confirm) {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putSerializable("cardData", cardData);
            intent.putExtras(bundle);
            setResult(Activity.RESULT_FIRST_USER, intent);
            finish();
        } else if (view.getId() == R.id.button_cancel) {
            //隐藏“确认”和“取消”按钮
            button_confirm.setVisibility(View.GONE);
            button_cancel.setVisibility(View.GONE);
            //显示手电筒开关
            button_torch.setVisibility(View.VISIBLE);
            //重置预览上层视图
            imageview_above.setImageBitmap(stdBitmap);
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

    //初始化预览上层视图
    private void initImageView() {
        //关闭硬件加速
        imageview_above.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        //获取绘制标准汉字的颜色
        Scalar color = ResourceDecoder.getScalar(this, R.attr.colorTheme);
        //取得标准汉字半透明图像
        stdBitmap = BitmapProcessor.toTransparent(cardData.getStdImgPath(), color);
        imageview_above.setImageBitmap(stdBitmap);
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
                imageCapture = new ImageCapture.Builder().build();
                cameraControl = cameraProvider.bindToLifecycle(
                                this, cameraSelector, preview, imageCapture).
                        getCameraControl();
            } catch (ExecutionException | InterruptedException ignored) {
            }
        }, ContextCompat.getMainExecutor(this));
    }

    //展示图片
    private void showPhoto() {
        //显示“确认”和“取消”按钮
        button_confirm.setVisibility(View.VISIBLE);
        button_cancel.setVisibility(View.VISIBLE);
        //隐藏手电筒开关
        button_torch.setVisibility(View.GONE);
        //预处理图像并显示
        BitmapProcessor.preprocess(cardData.getWrittenImgPath(), 512);
        Bitmap bitmap = BitmapFactory.decodeFile(cardData.getWrittenImgPath());
        imageview_above.setImageBitmap(bitmap);
    }

    //拍照
    private void takePhoto() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS", Locale.getDefault());
        String time = sdf.format(new Date());
        String writtenImgPath = getExternalCacheDir() + "/" + time + ".jpg";
        File photoFile = new File(writtenImgPath);
        ImageCapture.OutputFileOptions outputFileOptions = new ImageCapture.OutputFileOptions
                .Builder(photoFile)
                .build();
        imageCapture.takePicture(outputFileOptions, ContextCompat.getMainExecutor(this),
                new ImageCapture.OnImageSavedCallback() {
                    public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                        cardData.setWrittenImgPath(writtenImgPath);
                        showPhoto();//展示图片
                    }

                    public void onError(@NonNull ImageCaptureException exception) {
                        cameraError();
                    }
                });
    }

    //显示拍照失败信息
    private void cameraError() {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                R.string.camera_error, Snackbar.LENGTH_SHORT);
        snackbar.setAnchorView(R.id.bottom_bar);
        int color = ResourceDecoder.getColor(context, R.attr.colorBackground);
        snackbar.setBackgroundTint(color);
        snackbar.show();
    }
}