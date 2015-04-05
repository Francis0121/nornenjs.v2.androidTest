package com.nornenjs.android;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import com.github.nkzawa.socketio.client.Socket;


public class OpenglActivity extends ActionBarActivity {

    private Socket socket;
    private byte[] byteArray;
    
    public static GLGameSurfaceView mGLView;
    public static GLGameRenderer mGLRenderer;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opengl);

        
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
