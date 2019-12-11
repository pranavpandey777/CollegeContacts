package com.example.rayatuniv;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.RecyclerView;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

@SuppressLint("ValidFragment")
public class DialogShow extends AppCompatDialogFragment {
    String iname;
    String iid;
    String iemail;
    String iphone;
    String idept;
    String ipic;
    String uid;
    String chatuid;
    String currentname;


    @SuppressLint("ValidFragment")
    public DialogShow(String name,String email,String id,String phone,String dept, String pic,String uid) {
        this.iname=name;
        this.iemail=email;
        this.iid=id;
        this.iphone=phone;
        this.idept=dept;
        this.ipic=pic;
        this.uid=uid;
    }

    ImageView pic;
    TextView name,email, phone,department,id;
    ProgressDialog progressDialog;
    Button chat;

    FirebaseAuth firebaseAuth;

    DatabaseReference databaseReference;

    @Override
    public android.app.Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog, null);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait.....");
        progressDialog.show();
        pic=view.findViewById(R.id.pic);
        name=view.findViewById(R.id.name);
        id=view.findViewById(R.id.id);
        email=view.findViewById(R.id.email);
        phone=view.findViewById(R.id.phone);
        chat=view.findViewById(R.id.chat);
        department=view.findViewById(R.id.department);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference(user.getDisplayName()).child(FirebaseAuth.getInstance()
                .getCurrentUser().getUid());

        Picasso.with(getContext()).load(ipic).into(pic);
        name.setText(iname);
        id.setText(iid);
        email.setText(iemail);
        phone.setText(iphone);
        department.setText(idept);
        progressDialog.dismiss();

        databaseReference.child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                currentname = dataSnapshot.getValue(String.class);
             //   Toast.makeText(getActivity(), currentname, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        String currentuid=FirebaseAuth.getInstance().getCurrentUser().getUid();

        int uid1=currentuid .hashCode();

        int uid2=uid.hashCode();

        if(uid1>uid2){

            chatuid=currentuid+uid;
        }else{

            chatuid=uid+currentuid;
        }



        //  img.setImageResource(name);

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent intent=new Intent(getActivity(), Chat.class);
                intent.putExtra("id",chatuid);
                intent.putExtra("name",currentname);
                intent.putExtra("my",currentuid);
                intent.putExtra("hisUid",uid);
                startActivity(intent);

                dismiss();
            }
        });


        builder.setView(view);

        return builder.create();


    }
}
