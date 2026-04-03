// HelloJni.java - 添加触摸捕获
package com.mycompany.myndkapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.view.View;
public class HelloJni extends Activity
{
    }
/*
public class HelloJni extends Activity
{
	static final String TAG = "TouchCapture";
	private SurfaceView surfaceView=null;
	private Surface surface;
	
	// 触摸点状态
	private boolean[] touchActive = new boolean[10];
	
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // 全屏设置
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                             WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // 隐藏状态栏和导航栏（沉浸式）
        getWindow().getDecorView().setSystemUiVisibility(
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_FULLSCREEN
            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

    
        

        
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE); 
		
		if(surfaceView==null){
			Log.v(TAG, "创建surfaceView");
			surfaceView = new SurfaceView(this);
			surfaceView.setFocusable(true);
			surfaceView.setFocusableInTouchMode(true); 
			surfaceView.requestFocus();
			
			// 设置触摸监听
			surfaceView.setOnTouchListener(new View.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					return onTouchEvent(event);
				}
			});
			
			SurfaceHolder surfaceHolder = surfaceView.getHolder();
			surface = surfaceHolder.getSurface(); 
			surfaceHolder.addCallback(new SurfaceHolder.Callback() {
				@Override
				public void surfaceCreated(SurfaceHolder holder) {
					Log.v(TAG, "surface created.");
					startPreview(surface);
				}
				@Override
				public void surfaceDestroyed(SurfaceHolder holder) {}
				@Override
				public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
					Log.v(TAG, "format=" + format + " w/h : (" + width + ", " + height + ")");
				}
			});
			setContentView(surfaceView);
		}
    }
    
    // 捕获触摸事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getActionMasked();
        int pointerCount = event.getPointerCount();
        
        switch (action) {
            case MotionEvent.ACTION_DOWN:        // 第一个手指按下
            case MotionEvent.ACTION_POINTER_DOWN: // 额外手指按下
                int downIndex = event.getActionIndex();
                int downId = event.getPointerId(downIndex);
                int downX = (int) event.getX(downIndex);
                int downY = (int) event.getY(downIndex);
                
                touchActive[downId] = true;
                Log.d(TAG, String.format("按下: id=%d, x=%d, y=%d", downId, downX, downY));
                
                // 调用 JNI 发送触摸事件 (action=0 按下)
                keytoch(downId, downX, downY, 0);
                break;
                
            case MotionEvent.ACTION_MOVE:        // 移动
                for (int i = 0; i < pointerCount; i++) {
                    int moveId = event.getPointerId(i);
                    int moveX = (int) event.getX(i);
                    int moveY = (int) event.getY(i);
                    
                    if (touchActive[moveId]) {
                        // action=1 移动
                        keytoch(moveId, moveX, moveY, 1);
                    }
                }
                break;
                
            case MotionEvent.ACTION_UP:          // 最后一个手指抬起
            case MotionEvent.ACTION_POINTER_UP:  // 额外手指抬起
                int upIndex = event.getActionIndex();
                int upId = event.getPointerId(upIndex);
                int upX = (int) event.getX(upIndex);
                int upY = (int) event.getY(upIndex);
                
                touchActive[upId] = false;
                Log.d(TAG, String.format("抬起: id=%d, x=%d, y=%d", upId, upX, upY));
                
                // action=2 抬起
                keytoch(upId, upX, upY, 2);
                break;
                
            case MotionEvent.ACTION_CANCEL:      // 取消
                for (int i = 0; i < pointerCount; i++) {
                    int cancelId = event.getPointerId(i);
                    touchActive[cancelId] = false;
                    keytoch(cancelId, 0, 0, 2);
                }
                break;
        }
        return true;
    }

    // JNI 接口
    public static native void startPreview(Surface surface);
    public static native void keydown(int a);
    public static native void keyup(int a);
    public static native void statusplay(int a);
    public static native void keytoch(int id, int x, int y, int action);  // action: 0=按下, 1=移动, 2=抬起
    
    static {
        System.loadLibrary("hello-jni");
    }
    
    @Override
    protected void onPause() {
        statusplay(-1); 
        super.onPause();
    }
    
    @Override
    protected void onDestroy() {
        statusplay(-1);
        super.onDestroy();
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        keydown(keyCode); 
        return true;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        keyup(keyCode); 
        return true;
    }
}

*/
