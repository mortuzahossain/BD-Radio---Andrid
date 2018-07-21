package com.example.mortuza.radioplayer;

import android.media.MediaPlayer;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chibde.visualizer.BarVisualizer;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class Player extends AppCompatActivity {

    Button playBtn;
    CircleImageView circleImageView;
    TextView name;
    ProgressBar progressBar;
    BarVisualizer barVisualizer;

    String streamUrlVal;

    // For Media Player
    static MediaPlayer player;
    boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        circleImageView = findViewById(R.id.image);
        playBtn = findViewById(R.id.btnPlay);
        name = findViewById(R.id.name);
        progressBar = findViewById(R.id.progressBar1);
        barVisualizer = findViewById(R.id.barVisualizer);

        // Receiving Data From Intent
        String nameVal = getIntent().getStringExtra("NAME");
        String imageUrlVal = getIntent().getStringExtra("IMAGE");
        streamUrlVal = getIntent().getStringExtra("STREAMURL");

        // Setting The Name in the text view
        name.setText(nameVal);

        // Setting the image view
        Picasso.get()
                .load(imageUrlVal)
                .into(circleImageView);

        // Adding animation to the circular image view
        RotateAnimation rotateAnimation = new RotateAnimation(0, 360f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);

        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setDuration(1500);
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        circleImageView.startAnimation(rotateAnimation);

        // Change The Button Visualizer
        barVisualizer.setColor(ContextCompat.getColor(this, R.color.colorAccent));
        barVisualizer.setDensity(30);

        // Change The Progress Bar
        progressBar.setMax(100);
        progressBar.setVisibility(View.INVISIBLE);
        progressBar.setIndeterminate(true);

        if (player != null){
            player.stop();
            player.release();
        }

        // TODO: Play The Audio

        // Initialize music player
        initializeMediaPlayer();
        startPlaying();


        // Player Control For Player
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag) {
                    stopPlaying();
                    flag = false;
                } else {
                    startPlaying();
                    flag = true;
                }
            }
        });


    }

    private void initializeMediaPlayer() {
        player = new MediaPlayer();
        try {
            player.setDataSource(streamUrlVal);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        player.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                progressBar.setIndeterminate(false);
                progressBar.setSecondaryProgress(100);
            }
        });
    }

    private void startPlaying() {
        playBtn.setBackgroundResource(R.drawable.stop);
        try {
            barVisualizer.setPlayer(player.getAudioSessionId());
            progressBar.setVisibility(View.VISIBLE);
            player.prepareAsync();
            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                public void onPrepared(MediaPlayer mp) {
                    player.start();
                }
            });
        } catch (Exception e) {
            Log.d("vg", "Visualization Error.");
        }
    }

    private void stopPlaying() {
        playBtn.setBackgroundResource(R.drawable.play);
        if (player.isPlaying()) {
            player.stop();
            player.release();
            initializeMediaPlayer();
            progressBar.setVisibility(View.INVISIBLE);
            progressBar.setIndeterminate(true);
        }
    }

}
