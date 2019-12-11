package com.example.rayatuniv;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

public class Dashboard extends AppCompatActivity {
    android.support.design.widget.BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout;
    FragmentManager fragmentManager;
    Fragment fragment;
    android.support.v4.app.FragmentTransaction transaction;
    String mUID;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            fragmentManager = getSupportFragmentManager();
            transaction = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragment = new Fragment123("home");
                    transaction.replace(R.id.framelayout, fragment);
                    transaction.commit();
                    return true;
//                case R.id.navigation_dashboard:
//                    fragment = new Fragment123("dashboard");
//                    transaction.replace(R.id.framelayout, fragment);
//                    transaction.commit();
//                    return true;
                case R.id.navigation_notifications:
                    fragment = new Fragment123("profile");
                    transaction.replace(R.id.framelayout, fragment);
                    transaction.commit();

                    return true;

            }
            return false;
        }
    };





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.dashboard);

        mUID= FirebaseAuth.getInstance().getCurrentUser().getUid();

        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        fragment = new Fragment123("home");
        transaction.add(R.id.framelayout, fragment);
        transaction.commit();

        BottomNavigationView navView = findViewById(R.id.nav);


        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);



    }

}
