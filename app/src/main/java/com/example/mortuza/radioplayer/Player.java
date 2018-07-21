package com.example.mortuza.radioplayer;


import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.chibde.visualizer.BarVisualizer;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Player extends AppCompatActivity {

    private Button startButton;
    private Button stopButton;

    private SimpleExoPlayer player;
    private BandwidthMeter bandwidthMeter;
    private ExtractorsFactory extractorsFactory;
    private TrackSelection.Factory trackSelectionFactory;
    private TrackSelector trackSelector;
    private DefaultBandwidthMeter defaultBandwidthMeter;
    private DataSource.Factory dataSourceFactory;
    private MediaSource mediaSource;
    boolean flag = true;

    public Player() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        CircleImageView circleImageView = findViewById(R.id.image);
        Button playBtn = findViewById(R.id.btnPlay);
        TextView name = findViewById(R.id.name);

        // Receiving Data From Intent
        String nameVal = getIntent().getStringExtra("NAME");
        String imageUrlVal = getIntent().getStringExtra("IMAGE");
        String streamUrlVal = getIntent().getStringExtra("STREAMURL");

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

        bandwidthMeter = new DefaultBandwidthMeter();
        extractorsFactory = new DefaultExtractorsFactory();
        trackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        trackSelector = new DefaultTrackSelector(trackSelectionFactory);

        defaultBandwidthMeter = new DefaultBandwidthMeter();
        dataSourceFactory = new DefaultDataSourceFactory(this,
                Util.getUserAgent(this, "mediaPlayerSample"), defaultBandwidthMeter);

        mediaSource = new ExtractorMediaSource(Uri.parse(streamUrlVal), dataSourceFactory, extractorsFactory, null, null);

        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
        player.prepare(mediaSource);


        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag) {
                    stopPlayer();
                    flag = false;
                } else {
                    startPlayer();
                    flag = true;
                }
            }
        });

        startPlayer();

    }

    private void stopPlayer() {
        player.setPlayWhenReady(false);
    }

    private void startPlayer() {
        player.setPlayWhenReady(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.setPlayWhenReady(false);
    }
}
