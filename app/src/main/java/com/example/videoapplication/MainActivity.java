package com.example.videoapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;


public class MainActivity extends Activity implements MediaPlayer.OnPreparedListener, SurfaceHolder.Callback {

    final static String RTSP_URL = "rtsp://192.168.43.206:8554/";


    private MediaPlayer _mediaPlayer;
    private SurfaceHolder _surfaceHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set up a full-screen black window.
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = getWindow();
        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        window.setBackgroundDrawableResource(android.R.color.white);

        setContentView(R.layout.activity_main);

        // Configure the view that renders live video.
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        _surfaceHolder = surfaceView.getHolder();
        _surfaceHolder.addCallback(this);
        _surfaceHolder.setFixedSize(320, 240);
    }

    // More to come...

    @Override
    public void surfaceChanged(
            SurfaceHolder sh, int f, int w, int h) {}

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder sh) {
        _mediaPlayer = new MediaPlayer();
        _mediaPlayer.setDisplay(_surfaceHolder);

        Context context = getApplicationContext();
       // Map<String, String> headers = getRtspHeaders();
        Uri source = Uri.parse(RTSP_URL);

        try {
            // Specify the IP camera's URL and auth headers.
            _mediaPlayer.setDataSource(context, source);

            // Begin the process of setting up a video stream.
            _mediaPlayer.setOnPreparedListener(this);
            _mediaPlayer.prepareAsync();
        }
        catch (Exception e) {}
    }

   /* private Map<String, String> getRtspHeaders() {
        Map<String, String> headers = new HashMap<String, String>();
       // String basicAuthValue = getBasicAuthValue(USERNAME, PASSWORD);
      //  headers.put("Authorization", basicAuthValue);
        return headers;
    }
*/
    private String getBasicAuthValue(String usr, String pwd) {
        String credentials = usr + ":" + pwd;
        int flags = Base64.URL_SAFE | Base64.NO_WRAP;
        byte[] bytes = credentials.getBytes();
        return "Basic " + Base64.encodeToString(bytes, flags);
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        _mediaPlayer.start();
    }
}
