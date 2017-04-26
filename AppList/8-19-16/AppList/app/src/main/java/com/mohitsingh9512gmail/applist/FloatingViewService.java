package com.mohitsingh9512gmail.applist;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

public class FloatingViewService extends Service {

    private WindowManager mWindowManager;
    private ImageView mImgFloatingView;
    private boolean mIsFloatingViewAttached = false;
    private boolean mIsFloatingListAttached=false;
    @Override
    public IBinder onBind(Intent intent) {
        //Not use this method
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(!mIsFloatingViewAttached){
            mWindowManager.addView(mImgFloatingView, mImgFloatingView.getLayoutParams());
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);

        mImgFloatingView = new ImageView(this);
        mImgFloatingView.setImageResource(R.mipmap.float_icon);

        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.TOP | Gravity.LEFT;

        mWindowManager.addView(mImgFloatingView, params);

        mImgFloatingView.setOnTouchListener(new View.OnTouchListener() {
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialX = params.x;
                        initialY = params.y;
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        return true;
                    case MotionEvent.ACTION_UP:
                        if( (Math.abs(initialTouchX - event.getRawX())<5) && (Math.abs(initialTouchY - event.getRawY())<5) )
                        {

                                Log.e("tag","It's a click . List Attached! ");
                                //startService(new Intent(getApplicationContext(), FloatingViewList.class));
                                mIsFloatingListAttached=true;

                        }
                        else Log.e("tag","you moved the head");
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        params.x = initialX + (int) (event.getRawX() - initialTouchX);
                        params.y = initialY + (int) (event.getRawY() - initialTouchY);
                        mWindowManager.updateViewLayout(mImgFloatingView, params);
                        return true;
                }


                return false;
            }
        });

        mIsFloatingViewAttached = true;
    }

    public void removeView() {
        if (mImgFloatingView != null){
            mWindowManager.removeView(mImgFloatingView);
            mIsFloatingViewAttached = false;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        removeView();
    }


}
