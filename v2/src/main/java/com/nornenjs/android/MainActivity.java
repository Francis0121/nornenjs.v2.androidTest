package com.nornenjs.android;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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

//        socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
//
//            @Override
//            public void call(Object... args) {
//                Log.d("socket", "connect");
////                socket.emit("my other event", "android");// 112.108.40.165
//                socket.emit("stream"); // 112.108.40.166
//            }
//
//        });
        
//        socket.on("news", new Emitter.Listener() { // 112.108.40.165
//
//            @Override
//            public void call(Object... args) {
//                JSONObject obj = (JSONObject) args[0];
//                Log.d("socket", obj.toString());
//            }
//
//        });
//
//        socket.on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
//
//            @Override
//            public void call(Object... args) {
//
//                Log.d("socket", "disconnect");
//            }
//
//        });
        

//        socket.on("image", new Emitter.Listener() {// 112.108.40.165
//            @Override
//
//            public void call(Object... args) {
//                byteArray = (byte[]) args[0];
//                Log.d("socket", "Length : " + byteArray.length);
//
//
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Log.d("socket", "ByteArray " + byteArray);
//                        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
//                        Log.d("socket", "Heigth " + bitmap.getHeight() + " Width " + bitmap.getWidth());
//                        ImageView image = (ImageView) findViewById(R.id.imageView);
//                        image.setImageBitmap(bitmap);
//                        count++;
//                    }
//                });
//
//            }
//
//        });
        
       /* socket.on("jpeg", new Emitter.Listener() { //112.108.40.166
            @Override
            public void call(Object... args) {
                byteArray = (byte[]) args[0];
                //Log.d("socket", "Cuda Length : " + byteArray.length);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Log.d("socket", "Cuda ByteArray " + byteArray);
                        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                        //Log.d("socket", "CUda Heigth " + bitmap.getHeight() + " Width " + bitmap.getWidth());
                        ImageView image = (ImageView) findViewById(R.id.imageView);
                        image.setImageBitmap(bitmap);
                        count++;
                    }
                });

            }
        });

        socket.connect();*/

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
