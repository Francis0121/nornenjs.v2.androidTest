package com.nornenjs.android;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by pi on 15. 3. 13.
 */
public class GLGameRenderer implements GLSurfaceView.Renderer {

    private Quad mQuad;
    private byte[] byteArray;
    private Context context;
    
    private int count = 0;
    private TimerTask mTask;
    private Timer mTimer;
    
    
    public void setByteArray(byte[] byteArray) {
        this.byteArray = byteArray;
    }

    public GLGameRenderer(Context context) {
        mQuad = new Quad();
        this.context = context;

        mTask = new TimerTask() {
            @Override
            public void run() {
                Log.d("FPS", "FPS : " + count);
                count = 0;
            }
        };

        mTimer = new Timer();
        mTimer.schedule(mTask, 1000, 1000);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT|GL10.GL_DEPTH_BUFFER_BIT);
        if(this.byteArray != null) {
            mQuad.InitTexture(gl, this.byteArray);
        }else{
            Log.d("render", "null");
        }
        mQuad.draw(gl);
        count++;
    }
    
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        
    }
    
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig arg1) {
        gl.glClearColor(0, 1, 0, 0.5f);										// RGBA
        gl.glEnable(GL10.GL_TEXTURE_2D);									// 텍스쳐 활성
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);	// ??
    }
}