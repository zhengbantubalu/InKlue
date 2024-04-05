package com.bupt.inklue.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
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

import com.bupt.inklue.R;
import com.bupt.inklue.data.CardData;
import com.bupt.inklue.data.CardsData;
import com.bupt.inklue.util.BitmapProcessor;
import com.bupt.inklue.util.ResourceDecoder;
import com.google.common.util.concurrent.ListenableFuture;

import org.opencv.core.Scalar;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

//拍照页面
public class CameraActivity extends AppCompatActivity
        implements View.OnClickListener, View.OnTouchListener {

    private Context context;//环境
    private CardsData charCardsData;//汉字卡片数据列表
    private int position = 0;//当前拍摄的汉字在卡片列表中的位置
    private int savedNum = 0;//已经保存的图像数
    private PreviewView preview_view;//相机预览视图
    private ImageView imageview_above;//预览视图上层的视图
    private ImageCapture imageCapture;//图像捕捉器，用于拍照
    private CameraControl cameraControl;//相机控制器，用于对焦
    private Scalar color;//绘制标准汉字的颜色
    private ImageButton button_torch;//手电筒开关
    private boolean isTorchOn;//手电筒是否开启

    @SuppressWarnings("unchecked")//忽略取得图像卡片数据时类型转换产生的警告
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏系统顶部状态栏
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_camera);
        context = this;

        //取得视图
        preview_view = findViewById(R.id.preview_view);
        imageview_above = findViewById(R.id.imageview_top);
        button_torch = findViewById(R.id.button_torch);

        //加载OpenCV
        System.loadLibrary("opencv_java3");

        //取得汉字卡片数据
        charCardsData = new CardsData((List<CardData>)
                (getIntent().getSerializableExtra("charCardsData")));

        //初始化相机
        initCamera();

        //初始化预览上层视图
        initImageView();

        //设置预览框的触摸监听器
        preview_view.setOnTouchListener(this);

        //设置按钮的点击监听器
        findViewById(R.id.button_back).setOnClickListener(this);
        findViewById(R.id.button_shot).setOnClickListener(this);
        button_torch.setOnClickListener(this);
    }

    //点击事件回调
    public void onClick(View view) {
        if (view.getId() == R.id.button_back) {
            finish();
        } else if (view.getId() == R.id.button_shot) {
            if (savedNum < charCardsData.size()) {
                takePhoto();
                position++;
                updateImageView();//更新预览上层视图
            }
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

    //启动确认页面
    private void startConfirmActivity() {
        Intent intent = new Intent();
        intent.setClass(this, ConfirmActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("charCardsData", charCardsData);
        bundle.putSerializable("practiceCardData",
                getIntent().getSerializableExtra("practiceCardData"));
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    //初始化预览上层视图
    private void initImageView() {
        //关闭硬件加速
        imageview_above.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        //获取绘制标准汉字的颜色
        color = ResourceDecoder.getScalar(this, R.attr.colorTheme);
        //更新预览上层视图
        updateImageView();
    }

    //更新预览上层视图
    private void updateImageView() {
        if (position < charCardsData.size()) {
            //取得标准汉字半透明图像
            Bitmap bitmap = BitmapProcessor.toTransparent(charCardsData.get(position).getStdImgPath(), color);
            imageview_above.setImageBitmap(bitmap);
        } else if (position == charCardsData.size()) {
            Toast.makeText(context, R.string.camera_saving, Toast.LENGTH_SHORT).show();
        }
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
                        if (savedNum < charCardsData.size()) {
                            charCardsData.get(savedNum).setWrittenImgPath(writtenImgPath);
                            savedNum++;
                            if (savedNum == charCardsData.size()) {
                                //如果保存完最后一张，则启动确认页面
                                startConfirmActivity();
                            }
                        } else {
                            Toast.makeText(context, R.string.camera_error, Toast.LENGTH_SHORT).show();
                        }
                    }

                    public void onError(@NonNull ImageCaptureException exception) {
                        Toast.makeText(context, R.string.camera_error, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
