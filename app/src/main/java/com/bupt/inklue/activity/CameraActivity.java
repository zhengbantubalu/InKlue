package com.bupt.inklue.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraControl;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.FocusMeteringAction;
import androidx.camera.core.Preview;
import androidx.camera.core.SurfaceOrientedMeteringPointFactory;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;

import com.bupt.inklue.R;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;

//拍照页面
public class CameraActivity extends AppCompatActivity
        implements View.OnClickListener, View.OnTouchListener {

    PreviewView preview_view;//相机预览视图
    CameraControl cameraControl;//相机控制器，用于对焦

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏系统顶部状态栏
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_camera);

        //取得相机预览视图
        preview_view = findViewById(R.id.preview_view);

        //初始化相机
        initCamera();

        //设置预览框的触摸监听器
        preview_view.setOnTouchListener(this);

        //设置按钮的点击监听器
        findViewById(R.id.button_back).setOnClickListener(this);
    }

    //点击事件回调
    public void onClick(View view) {
        if (view.getId() == R.id.button_back) {
            finish();
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
                cameraControl = cameraProvider.bindToLifecycle(
                        this, cameraSelector, preview).getCameraControl();
            } catch (ExecutionException | InterruptedException ignored) {
            }
        }, ContextCompat.getMainExecutor(this));
    }
}
