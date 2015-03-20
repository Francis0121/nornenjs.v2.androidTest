package com.nornenjs.android;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;

/**
 * Created by pi on 15. 3. 13.
 */
public class GLGameSurfaceView extends GLSurfaceView {
    public static GameThread mGameThread;

    public static boolean isRun;
    public static boolean isWait;

    public GLGameSurfaceView(Context context) {
        super(context);
        setRenderer(OpenglActivity.mGLRenderer);
        setRenderMode(RENDERMODE_WHEN_DIRTY); // 한번만 그려줌
        setFocusableInTouchMode(true);
        mGameThread = new GameThread(context);
        mGameThread.start();
        Log.d("render", "surface view");
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.v(null, "onKeyDown");
        return super.onKeyDown(keyCode, event);
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.v(null,"onTouchEvent");
        
        switch(event.getAction()) {
        
            case MotionEvent.ACTION_DOWN :
                Log.v(null,"onTouchEvent : ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE :
                Log.v(null,"onTouchEvent : ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP :
                Log.v(null,"onTouchEvent : ACTION_UP");
                break;
        }
        return super.onTouchEvent(event);
    }
    
    //Thread
    public class GameThread extends Thread {
        public GameThread(Context context) {
            isRun = true;
            isWait = false;
        }
        
        public void run() {
            while(isRun) {
                
                try {
                    OpenglActivity.mGLView.requestRender(); // onDrawFrame
                    mGameThread.sleep(10);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                
                synchronized (this) {
                    if (isWait) {
                        try { wait(); } catch (Exception e) {
                        }
                    }
                }//syn
            }
        }
        
        /* Thread pause */
        public void pauseThread() {
            isWait = true;
            synchronized (this) {
                this.notify();
            }
        }
        
        /* Thread Resume */
        public void resumeThread() {
            isWait = false;
            synchronized (this) {
                this.notify();
            }
        }
    }
}
