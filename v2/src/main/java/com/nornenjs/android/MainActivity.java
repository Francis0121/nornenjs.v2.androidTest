package com.nornenjs.android;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.Button;
import android.widget.ImageView;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;
import com.github.nkzawa.socketio.client.IO;

import java.net.URISyntaxException;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends ActionBarActivity {
    
    private Socket socket;
    private byte[] byteArray;
    private int count = 0;
    private TimerTask mTask;
    private Timer mTimer;

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(mTimer != null){
            mTask.cancel();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        
        switch(event.getAction()) {

            case MotionEvent.ACTION_DOWN :
                Log.v(null,"onTouchEvent : ACTION_DOWN on Activity");
                break;
            case MotionEvent.ACTION_MOVE :
                Log.v(null,"onTouchEvent : ACTION_MOVE  on Activity");
                break;
            case MotionEvent.ACTION_UP :
                Log.v(null,"onTouchEvent : ACTION_UP on Activity");
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main);

        /**
         * Add Task
         */
        mTask = new TimerTask() {
            @Override
            public void run() {
                Log.d("FPS", "FPS : " + count);
                count = 0;
            }
        };

        mTimer = new Timer();
        mTimer.schedule(mTask, 1000, 1000);
        
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

//        try {
//            socket = IO.socket("http://112.108.40.166:5000");
//        } catch (URISyntaxException e) {
//            e.printStackTrace();
//        }
//
//        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
//
//            @Override
//            public void call(Object... args) {
//                Log.d("socket", "connect");
//                socket.emit("stream"); // 112.108.40.166
//            }
//
//        });
//
//
//       socket.on("jpeg", new Emitter.Listener() { //112.108.40.166
//            @Override
//            public void call(Object... args) {
//                byteArray = (byte[]) args[0];
//
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
//                        ImageView image = (ImageView) findViewById(R.id.imageView);
//                        image.setImageBitmap(bitmap);
//                        count++;
//                    }
//                });
//
//            }
//        });
//
//        socket.connect();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {


        Button button;
        Context mContext;

        public PlaceholderFragment() {
        }
        
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_main, container, false);
           
            mContext = getActivity();
            
            button = (Button) rootView.findViewById(R.id.openglBtn);
            button.setOnClickListener(new Button.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, OpenglActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            });
            
            
            return rootView;
        }
        
        
    }
}
