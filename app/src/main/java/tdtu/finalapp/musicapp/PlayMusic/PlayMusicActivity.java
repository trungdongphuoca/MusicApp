package tdtu.finalapp.musicapp.PlayMusic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import tdtu.finalapp.musicapp.Fragment.SongFragment;
import tdtu.finalapp.musicapp.MainPackage.MainActivity;
import tdtu.finalapp.musicapp.Model.Song;
import tdtu.finalapp.musicapp.R;
import tdtu.finalapp.musicapp.Toast.ToastNotification;

public class PlayMusicActivity extends AppCompatActivity {
    private TextView titleTv,currentTimeTv,totalTimeTv;
    private SeekBar seekBar;
    private ImageView pausePlay,nextBtn,previousBtn,musicIcon,backIcon,menuIcon;
    private ArrayList<Song> songsList;
    private Song currentSong;
    private MediaPlayer mediaPlayer = MyMediaPlayer.getInstance();
    private int x=0;


    private ImageView randomBtn,repeatBtn;
    boolean checkRandom = false;
    boolean checkRepeat = false;
    private Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);

        titleTv = findViewById(R.id.song_title);
        currentTimeTv = findViewById(R.id.current_time);
        totalTimeTv = findViewById(R.id.total_time);
        seekBar = findViewById(R.id.seek_bar);
        pausePlay = findViewById(R.id.pause_play);
        nextBtn = findViewById(R.id.next);
        previousBtn = findViewById(R.id.previous);
        randomBtn = findViewById(R.id.random);
        repeatBtn = findViewById(R.id.repeat);
        musicIcon = findViewById(R.id.music_icon_big);
        backIcon = findViewById(R.id.backInPlayMusic);
        menuIcon= findViewById(R.id.MenuInPlayMusic);





        titleTv.setSelected(true);

        songsList = (ArrayList<Song>) getIntent().getSerializableExtra("LIST_SONG");



        setResourcesWithMusic();


        PlayMusicActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(mediaPlayer!=null){
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    currentTimeTv.setText(convertToMMSS(mediaPlayer.getCurrentPosition()+""));

                    if(mediaPlayer.isPlaying()){
                        pausePlay.setImageResource(R.drawable.ic_baseline_pause_24);
                        musicIcon.setRotation(x+=2);
                    }else{
                        pausePlay.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                        musicIcon.setRotation(x);
                    }


                }
                new Handler().postDelayed(this,100);
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(mediaPlayer!=null && fromUser){
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

        menuIcon.setOnClickListener(v->showMenu());
        backIcon.setOnClickListener(v-> startActivity(new Intent(PlayMusicActivity.this, MainActivity.class)));
    }

    void showMenu(){
        PopupMenu popupMenu = new PopupMenu(PlayMusicActivity.this,menuIcon);
        popupMenu.getMenu().add("add to playlist");
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(menuItem.getTitle().equals("add to playlist")){
                    ToastNotification.makeTextToShow(PlayMusicActivity.this,"add the playlist has been clicked");
                    return true;
                }
                return false;
            }
        });
    }

    ////set name and total time of song, some events play pause next previous
    void setResourcesWithMusic(){
        currentSong = songsList.get(MyMediaPlayer.currentIndex);

        titleTv.setText(currentSong.getTitle());

        totalTimeTv.setText(convertToMMSS(currentSong.getDuration()));

        pausePlay.setOnClickListener(v-> pausePlay());
        nextBtn.setOnClickListener(v-> playNextSong());
        previousBtn.setOnClickListener(v-> playPreviousSong());


        randomBtn.setOnClickListener(v->randomSong());
        repeatBtn.setOnClickListener(v->repeatSong());


        playMusic();


    }


    private void playMusic(){

        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(currentSong.getPath());
            mediaPlayer.prepare();
            mediaPlayer.start();
            seekBar.setProgress(0);
            seekBar.setMax(mediaPlayer.getDuration());
        } catch (IOException e) {
            e.printStackTrace();
        }
        //auto next song when current song finish -> but it has some error
//        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
//            @Override
//            public void onCompletion(MediaPlayer mediaPlayer) {
//                nextBtn.performClick();
//            }
//        });


    }

    private void playNextSong(){
        int tmpIndex =MyMediaPlayer.currentIndex;
        x = 0;
        if(MyMediaPlayer.currentIndex== songsList.size()-1)
            MyMediaPlayer.currentIndex = -1;

        if(checkRandom && !checkRepeat) {
            MyMediaPlayer.currentIndex = random.nextInt(songsList.size());
        }
        else if(!checkRandom && checkRepeat){
            MyMediaPlayer.currentIndex = tmpIndex;
        }
        else
            MyMediaPlayer.currentIndex +=1;

        mediaPlayer.reset();
        setResourcesWithMusic();

    }

    private void playPreviousSong(){
        int tmpIndex =MyMediaPlayer.currentIndex;
        x = 0;
        if(MyMediaPlayer.currentIndex== 0)
            MyMediaPlayer.currentIndex = songsList.size();

        if(checkRandom && !checkRepeat) {
            MyMediaPlayer.currentIndex = random.nextInt(songsList.size());
        }
        else if(!checkRandom && checkRepeat){
            MyMediaPlayer.currentIndex = tmpIndex;
        }else
            MyMediaPlayer.currentIndex -=1;

        mediaPlayer.reset();
        setResourcesWithMusic();
    }

    private void pausePlay(){
        if(mediaPlayer.isPlaying())
            mediaPlayer.pause();
        else
            mediaPlayer.start();
    }

    public static String convertToMMSS(String duration){
        Long millis = Long.parseLong(duration);
        return String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millis) % TimeUnit.HOURS.toMinutes(1),
                TimeUnit.MILLISECONDS.toSeconds(millis) % TimeUnit.MINUTES.toSeconds(1));
    }


    public void repeatSong(){
        checkRepeat = !checkRepeat;
        if(checkRepeat){
            repeatBtn.setImageResource(R.drawable.ic_baseline_repeat_one_edit_24);
        }
        else{
            repeatBtn.setImageResource(R.drawable.ic_baseline_repeat_one_24);
        }

    }

    public void randomSong(){
        checkRandom = !checkRandom;
        if(checkRandom){
            randomBtn.setImageResource(R.drawable.ic_baseline_shuffle_edit_24);
        }
        else{
            randomBtn.setImageResource(R.drawable.ic_baseline_shuffle_24);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        checkRandom = false;
        checkRepeat = false;
    }

}