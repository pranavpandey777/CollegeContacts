package com.example.rayatuniv;

import android.animation.Animator;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    EditText username, password;
    Button login;
    TextView register;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    private SharedPreferences.Editor editor;
    private SharedPreferences sp;
    private ImageView bookIconImageView;

    private RelativeLayout rootView, afterAnimationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);


        firebaseAuth = FirebaseAuth.getInstance();
        bookIconImageView=findViewById(R.id.bookIconImageView);

        rootView=findViewById(R.id.rootView);
        afterAnimationView=findViewById(R.id.afterAnimationView);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        progressDialog = new ProgressDialog(MainActivity.this,R.style.AppCompatAlertDialogStyle);
        progressDialog.setMessage("loading");

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               progressDialog.show();
                String iusername = username.getText().toString();
                String ipassword = password.getText().toString();


                firebaseAuth.signInWithEmailAndPassword(iusername, ipassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                         progressDialog.dismiss();
                            sp=getSharedPreferences("my_key",MODE_PRIVATE);//it returns two parameters one string and another status flag
                            editor=sp.edit();
                            editor.putString("username",iusername);
                            editor.putString("password",ipassword);
                            editor.commit();
                            // Toast.makeText(LoginActivity.this, "okkk", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this, Dashboard.class));
                            finish();
                        } else {
                         progressDialog.dismiss();
                            Toast.makeText(MainActivity.this, "Invalid Username Or Password", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });



        new CountDownTimer(5000, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {

                rootView.setBackgroundColor(ContextCompat.getColor(MainActivity.this, R.color.colorSplashText));
                bookIconImageView.setImageResource(R.drawable.white_book_icon);
                startAnimation();
            }

            @Override
            public void onFinish() {

            }
        }.start();

    }


    private void startAnimation() {
        ViewPropertyAnimator viewPropertyAnimator = bookIconImageView.animate();
        viewPropertyAnimator.x(50f);
        viewPropertyAnimator.y(100f);
        viewPropertyAnimator.setDuration(1000);
        viewPropertyAnimator.setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                afterAnimationView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }





    @Override
    protected void onStart() {
        super.onStart();
        sp = getSharedPreferences("my_key", MODE_PRIVATE);
        String na=sp.getString("username",null);
        //String pass=sp.getString("password",null);
        if (na!=null){
            startActivity(new Intent(MainActivity.this, Dashboard.class));

        }

    }
}
