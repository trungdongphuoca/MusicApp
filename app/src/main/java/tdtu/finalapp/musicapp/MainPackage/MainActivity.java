package tdtu.finalapp.musicapp.MainPackage;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.PopupMenu;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import tdtu.finalapp.musicapp.Fragment.LibraryFragment;
import tdtu.finalapp.musicapp.Fragment.SongFragment;
import tdtu.finalapp.musicapp.R;
import tdtu.finalapp.musicapp.Toast.ToastNotification;
import tdtu.finalapp.musicapp.loginAndRegis.LoginActivity;

public class MainActivity extends AppCompatActivity {
    private ImageView userImg;
    private boolean isBackPressedOnce =false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!checkPermission()){
            requestPermission();
//            if (Build.VERSION.SDK_INT >= 11) {
//                recreate();
//                break;
//            } else {
//                Intent intent = getIntent();
//                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//                finish();
//                overridePendingTransition(0, 0);
//
//                startActivity(intent);
////                overridePendingTransition(0, 0);
//                break;
//            }

        }


        setContentView(R.layout.activity_main);
        userImg = findViewById(R.id.UserBtn);
        initViewPage();

        userImg.setOnClickListener(v->showUser());
    }
    public static void restartActivity(Activity activity){
        if (Build.VERSION.SDK_INT >= 11) {
            activity.recreate();
        } else {
            activity.finish();
            activity.startActivity(activity.getIntent());
        }
    }
    void showUser(){
        PopupMenu popupMenu = new PopupMenu(MainActivity.this,userImg);
        popupMenu.getMenu().add("See your profile");
        popupMenu.getMenu().add("Sign out");
        popupMenu.getMenu().add("Exit");
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(menuItem -> {
            CharSequence title = menuItem.getTitle();
            if ("Sign out".contentEquals(title)) {
                FirebaseAuth.getInstance().signOut();
                ToastNotification.makeTextToShow(MainActivity.this, "Sign out successful");
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
                return true;
            } else if ("See your profile".contentEquals(title)) {
                ToastNotification.makeTextToShow(MainActivity.this, "Your profile will present now!!");
//                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
//                    finish();
                return true;
            } else if ("Exit".contentEquals(title)) {
                ToastNotification.makeTextToShow(MainActivity.this, "Exit app");
//                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
                System.exit(0);
                return true;
            }
            return false;
        });
    }

    boolean checkPermission(){
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }
    void requestPermission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE)){
            ToastNotification.makeTextToShow(getApplicationContext(),"READ PERMISSION IS REQUIRED, PLS ALLOW FROM SETTINGS");

        }
        else
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    101);
        restartActivity(MainActivity.this);
    }
    private void initViewPage() {
        ViewPager viewPager = findViewById(R.id.viewPage);
        TabLayout tabLayout = findViewById(R.id.tab_layout);

        ViewPageAdapter viewPageAdapter = new ViewPageAdapter(getSupportFragmentManager());
//        viewPageAdapter.addFragments(new HomeFragment(),"Home");
        viewPageAdapter.addFragments(new SongFragment(),"Song");
        viewPageAdapter.addFragments(new LibraryFragment(),"Library");
        viewPager.setAdapter(viewPageAdapter);
        tabLayout.setupWithViewPager(viewPager);


    }


    public static class ViewPageAdapter extends FragmentPagerAdapter{

        private final ArrayList<Fragment> fragments;
        private final ArrayList<String> titles;
        public ViewPageAdapter(@NonNull FragmentManager fm) {
            super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
            this.fragments = new ArrayList<>();
            this.titles = new ArrayList<>();
        }
        void addFragments(Fragment fragment, String title){
            fragments.add(fragment);
            titles.add(title);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
        @Nullable
        @Override
        public CharSequence getPageTitle(int position){
            return titles.get(position);
        }
    }

    @Override
    public void onBackPressed() {
        if(isBackPressedOnce){
            super.onBackPressed();
            return;
        }
        ToastNotification.makeTextToShow(MainActivity.this, "Press again to exist!");
        isBackPressedOnce = true;

        new Handler().postDelayed(() -> isBackPressedOnce = false,3000);
    }
}