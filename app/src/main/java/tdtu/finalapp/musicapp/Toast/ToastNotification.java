package tdtu.finalapp.musicapp.Toast;

import android.content.Context;
import android.widget.Toast;

public class ToastNotification {
    public static void makeTextToShow(Context context,String text){
        Toast.makeText(context,text,Toast.LENGTH_SHORT).show();
    }
}
