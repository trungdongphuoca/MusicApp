package tdtu.finalapp.musicapp.PlaylistInLibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import tdtu.finalapp.musicapp.R;
import tdtu.finalapp.musicapp.Toast.ToastNotification;

public class PlaylistActivity extends AppCompatActivity {
    private LinearLayout playlistLinear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);


        playlistLinear = findViewById(R.id.playlistLinear);
        playlistLinear.setOnClickListener(v->showCustomDialogAddPlaylist());



    }

    void showCustomDialogAddPlaylist(){
        Dialog dialog  = new Dialog(PlaylistActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);

        dialog.setContentView(R.layout.custom_dialog_addplaylist);

        final EditText titlePlaylist = dialog.findViewById(R.id.name_playlist);
        final Button submitBtn = dialog.findViewById(R.id.confirmBtn);
        final Button cancelBtn = dialog.findViewById(R.id.cancelBtn);

        submitBtn.setOnClickListener(v->{
            String title  = titlePlaylist.getText().toString();
            if(!title.equals("")){
                ToastNotification.makeTextToShow(this,"Add success "+title + " playlist");
                dialog.dismiss();
            }
            else{
                ToastNotification.makeTextToShow(this,"Pls fill name Playlist or cancel add playlist");

            }

        });


        cancelBtn.setOnClickListener(v->{
            dialog.dismiss();
        });

        dialog.show();
    }
}