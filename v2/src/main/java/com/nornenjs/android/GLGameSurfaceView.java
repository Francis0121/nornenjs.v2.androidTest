package com.nornenjs.android;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;

/**
 * Created by pi on 15. 3. 13.
 */
public class GLGameSurfaceView extends GLSurfaceView{
    
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

