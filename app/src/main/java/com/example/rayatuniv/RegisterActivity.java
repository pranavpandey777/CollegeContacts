package com.example.rayatuniv;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Random;

public class RegisterActivity extends AppCompatActivity {

    EditText username, password, name, num, dept,iid;
    Button reg;
    Spinner spinner,deg;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    ImageView image;
    StorageReference storageReference;
    Uri downloaded;
    String designation;
    String type;

    TextWatcher textWatcher = null;
    int id;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_register);
        final String[] spinnerArray = {"Rayat-Bahra University"};
        final String[] array = {"Teacher","Student"};

        firebaseAuth = FirebaseAuth.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        username = findViewById(R.id.signUpEmail);
        password = findViewById(R.id.signUpPassword);
        image = findViewById(R.id.signUpImage);
        iid = findViewById(R.id.id);
        dept = findViewById(R.id.dept);
        name = findViewById(R.id.signUpName);
        num = findViewById(R.id.signUpPhone);
        reg = findViewById(R.id.btnRegister);
        spinner = findViewById(R.id.signUpSpinner);
        deg = findViewById(R.id.spinner);
        progressDialog = new ProgressDialog(this,R.style.AppCompatAlertDialogStyle);
        progressDialog.setMessage("Please Wait...");
        downloaded = Uri.parse("android.resource://com.example.rayatuniv/drawable/cam");
        Random r = new Random();
        id = 59;


        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType("image/*");
                startActivityForResult(i, 100);
            }
        });


        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, spinnerArray);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                designation = spinnerArray[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        ArrayAdapter adapter1 = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, array);
        deg.setAdapter(adapter1);
        deg.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                type = array[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                final String iname=name.getText().toString();
                final String iemail=username.getText().toString();
                final String inum=num.getText().toString();
                final String roll=iid.getText().toString();
                final String idept=dept.getText().toString();


                final String icollege=designation;


                String ipassword=password.getText().toString();

                firebaseAuth.createUserWithEmailAndPassword(iemail,ipassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                           String uid= FirebaseAuth.getInstance().getCurrentUser().getUid();

                            User user = new User(iname, iemail, inum,icollege,downloaded.toString(),type,roll,idept,uid);
                            FirebaseDatabase.getInstance()
                                    .getReference(type).child(FirebaseAuth.getInstance()
                                    .getCurrentUser().getUid()).setValue(user);
                            progressDialog.dismiss();
                            UserProfileChangeRequest profile=new UserProfileChangeRequest.Builder()
                                    .setDisplayName(type)
                                    .build();
                            FirebaseUser user1=firebaseAuth.getCurrentUser();
                            user1.updateProfile(profile);

                            Toast.makeText(RegisterActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            startActivity(new Intent(RegisterActivity.this, MainActivity.class));



                        }else{

                            Toast.makeText(RegisterActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
                        }



                    }
                });

            }
        });



    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            progressDialog.show();
            Uri uri = data.getData();
            StorageReference child = storageReference.child("MyOne").child(String.valueOf(id));

            child.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();

                    downloaded = taskSnapshot.getDownloadUrl();

                    Picasso.with(RegisterActivity.this).load(downloaded).into(image);

                }
            });

        }
    }



}
