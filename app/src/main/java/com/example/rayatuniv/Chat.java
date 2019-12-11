package com.example.rayatuniv;

import android.content.Context;
import android.os.Bundle;
import android.service.autofill.UserData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Chat extends AppCompatActivity {

    private static final int id = 100;
    FloatingActionButton send;
    Context context;
    EditText edtsend;
    DatabaseReference databaseReference,username;
    FirebaseAuth firebaseAuth;
    String from;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<ChatLayout> list;
    MyAdapterChat adapter;
    String ititle;
    String name;
    String current;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        username = FirebaseDatabase.getInstance().getReference(user.getDisplayName()).child(FirebaseAuth.getInstance()
                .getCurrentUser().getUid());

        send = findViewById(R.id.send);
        edtsend = findViewById(R.id.edtsend);
        recyclerView = findViewById(R.id.rv);
        context = this;
        ititle = getIntent().getStringExtra("id");
        name = getIntent().getStringExtra("name");
        current = getIntent().getStringExtra("hisUid");






        username.child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String iname = dataSnapshot.getValue(String.class);
                from = iname;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
       // Toast.makeText(context, from, Toast.LENGTH_SHORT).show();


        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String itext = edtsend.getText().toString();
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (itext.isEmpty()) {
                    edtsend.setError("Type Message");


                } else {

                    FirebaseUser user1 = firebaseAuth.getCurrentUser();

                    databaseReference = FirebaseDatabase.getInstance().getReference();


                    long time = System.currentTimeMillis();
                    DatabaseReference database = databaseReference.child("Chat").child(ititle).push();
                    database.child("message").setValue(itext);
                    database.child("from").setValue(name);
                    database.child("time").setValue(time);

                    edtsend.setText("");


                }
            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference("Chat").child(ititle);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<>();


                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                    ChatLayout a = dataSnapshot1.getValue(ChatLayout.class);
                    list.add(a);
                }


                adapter = new MyAdapterChat(Chat.this, list);
                recyclerView.setAdapter(adapter);
                int position = recyclerView.getAdapter().getItemCount() - 1;
                recyclerView.scrollToPosition(position);



                adapter.notifyDataSetChanged();



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Toast.makeText(Chat.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
            }
        });


    }



}
