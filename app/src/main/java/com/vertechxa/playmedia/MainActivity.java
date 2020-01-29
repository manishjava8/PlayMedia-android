package com.vertechxa.playmedia;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private Button playButton;
    private SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playButton = findViewById(R.id.playbutton);
        seekBar= findViewById(R.id.seekBarId);

        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource("http://buildappswithpaulo.com/music/watch_me.mp3");
        } catch (IOException e) {
            e.printStackTrace();
        }

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                int duration = mp.getDuration();
                Toast.makeText(MainActivity.this, String.valueOf((duration / 1000) / 60), Toast.LENGTH_LONG).show();

            }
        });


        final MediaPlayer.OnPreparedListener preparedListener = new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(final MediaPlayer mp) {


                seekBar.setMax(mp.getDuration());
                Log.e("MP", "Du "+mp.getDuration());
                playButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mp.isPlaying()) {
                            // stop and give users the option to start agai

                            mp.pause();
                            playButton.setText("Play");

                        } else {

                            mp.start();
                            playButton.setText("Pause");

                        }

                    }
                });
            }
        };
        mediaPlayer.setOnPreparedListener(preparedListener);
        mediaPlayer.prepareAsync();



        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if(fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

//        playButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mediaPlayer.isPlaying()) {
//                    // stop and give users the option to start agai
//
//                    mediaPlayer.pause();
//                    playButton.setText("Play");
//
//                } else {
//
//                    mediaPlayer.start();
//                    playButton.setText("Pause");
//
//                }
//            }
//        });


//        mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.listem_me);


            }

//    public void pauseMusic() {
//
//        if (mediaPlayer != null) {
//            mediaPlayer.pause();
//            playButton.setText("Play");
//        }
//    }
//
//    public void playMusic() {
//
//        if (mediaPlayer != null) {
//            mediaPlayer.start();
//            playButton.setText("Pause");
//        }
//    }

            @Override
            protected void onDestroy() {
                super.onDestroy();
                if (mediaPlayer != null) {
                    mediaPlayer.pause();
                    mediaPlayer.release();
                }
            }
        }
