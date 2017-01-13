package com.example.billy.jumpit;

import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;


public class MainActivity extends Activity {
    boolean musicaOn = true;
// Metodos para crear musica que inicie con la app en primer plano y que pare en segundo plano
    Audio audio = new Audio();
    @Override
    protected void onPause() {
        super.onPause();
        audio.stopMusic();
        audio.unload();
    }

    @Override
    protected void onResume() {
        super.onResume();
        audio.load(this);
        audio.startMusic();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

// comando para que no aparezca el titulo
        requestWindowFeature(Window.FEATURE_NO_TITLE);

// comando para que sea en pantalla completa
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);//int flag, int mask

        setContentView(R.layout.activity_main);

//crear animacion slideout
        final Animation slideout;
        slideout = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);
        slideout.setDuration(500);

//crear animacion fadeout
        final Animation fadeout;
        fadeout = AnimationUtils.loadAnimation(this, android.R.anim.fade_out);
        fadeout.setDuration(500);

//crear variables del menu
        final ImageButton play = (ImageButton)findViewById(R.id.play);
        final ImageButton volume = (ImageButton)findViewById(R.id.volumeButton);
        final TextView title = (TextView)findViewById(R.id.Title);
        final TextView coins = (TextView)findViewById(R.id.coins);
        final TextView diamonds = (TextView)findViewById(R.id.diamonds);
        final ImageView coins_image = (ImageView)findViewById(R.id.coin_image);
        final ImageView diamonds_image = (ImageView)findViewById(R.id.diamonds_image);

//crear listener del play
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //usar animacion fadeout para los elementos del menu
                play.startAnimation(fadeout);
                play.setVisibility(View.INVISIBLE);
                title.startAnimation(fadeout);
                title.setVisibility(View.INVISIBLE);
                coins.startAnimation(fadeout);
                coins.setVisibility(View.INVISIBLE);
                coins_image.startAnimation(fadeout);
                coins_image.setVisibility(View.INVISIBLE);
                diamonds.startAnimation(fadeout);
                diamonds.setVisibility(View.INVISIBLE);
                diamonds_image.startAnimation(fadeout);
                diamonds_image.setVisibility(View.INVISIBLE);
                volume.startAnimation(fadeout);
                volume.setVisibility(View.INVISIBLE);
            }
        });

//crear listener del volume para mute o play again
        volume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (musicaOn==true){
                    audio.stopMusic();
                    musicaOn=false;
                }else {
                    audio.startMusic();
                    musicaOn=true;
                }
            }
        });
    }
}

