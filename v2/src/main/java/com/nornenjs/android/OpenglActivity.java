package com.nornenjs.android;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.WindowManager;

import com.github.nkzawa.socketio.client.Socket;


public class OpenglActivity extends ActionBarActivity {

    private Socket socket;
    private byte[] byteArray;
    
    public static GLGameSurfaceView mGLView;
    public static GLGameRenderer mGLRenderer;

    public boolean isOn = false;
    public float beforeX = 0.0f, beforeY = 0.0f;
    public float rotationX = 0.0f, rotationY = 0.0f;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opengl);

<<<<<<< HEAD
=======
        // ~ socket connection
        try {
            socket = IO.socket("http://112.108.40.166:5000");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        socket.emit("connectMessage");
        
        socket.on("connectMessage", new Emitter.Listener() {

            @Override
            public void call(Object... args) {
                Log.d("socket", "on connectMessage");
                JSONObject message = (JSONObject) args[0];
                
                try {
                    if( ! ((Boolean) message.get("success")) ){
                        return;
                    }
                    socket.emit("init");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                
            }

        });

        socket.on("stream", new Emitter.Listener() { //112.108.40.166
            @Override
            public void call(Object... args) {
                byteArray = (byte[]) args[0];
                
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(mGLView != null) {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                            mGLRenderer.setBitmap(bitmap);
                        }
                    }
                });
            }
        });
        
        socket.connect();
>>>>>>> 826f0dd9c759fb2cc02d7440edff9ee6fa3d2efe
        
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        mGLRenderer = new GLGameRenderer(this);
        mGLView = new GLGameSurfaceView(this);
        setContentView(mGLView);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_opengl, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPause() {
        mGLView.mGameThread.pauseThread();
        super.onPause();
    }
    
    @Override
    public void onResume() {
        mGLView.mGameThread.resumeThread();
        super.onResume();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.v("opengl", "onTouchEvent");

        switch(event.getAction()) {

            case MotionEvent.ACTION_DOWN :
                Log.d("opengl", "onTouchEvent : ACTION_DOWN");
                isOn = true;
                beforeX = event.getX();
                beforeY = event.getY();

                break;
            case MotionEvent.ACTION_MOVE :
                Log.d("opengl", "onTouchEvent : ACTION_MOVE");
                if(isOn) {
                    //calc
                    rotationX += (event.getX() - beforeX) / 10.0;
                    rotationY += (event.getY() - beforeY) / 10.0;

                    beforeX = event.getX();
                    beforeY = event.getY();

                    //mListener.onMyEvent(rotationX, rotationY);
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("rotationX", rotationX);
                        jsonObject.put("rotationY", rotationY);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("error", "Make json object");
                    }
                    socket.emit("event", jsonObject);
                }
                break;
            case MotionEvent.ACTION_UP :
                Log.d("opengl", "onTouchEvent : ACTION_UP");
                isOn = false;
                break;
        }
        return super.onTouchEvent(event);
    }
}
