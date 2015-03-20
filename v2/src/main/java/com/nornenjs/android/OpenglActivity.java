package com.nornenjs.android;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;


public class OpenglActivity extends ActionBarActivity {

    private Socket socket;
    private byte[] byteArray;
    
    public static GLGameSurfaceView mGLView;
    public static GLGameRenderer mGLRenderer;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opengl);

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
}
