package com.example.mortuza.radioplayer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chibde.visualizer.BarVisualizer;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Player extends AppCompatActivity {

    Button playBtn;
    CircleImageView circleImageView;
    TextView name;
    ProgressBar progressBar;
    BarVisualizer barVisualizer;

    String streamUrlVal;

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


    }
}
