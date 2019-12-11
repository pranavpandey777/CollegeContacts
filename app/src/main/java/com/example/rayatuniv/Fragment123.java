package com.example.rayatuniv;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;


@SuppressLint("ValidFragment")
public class Fragment123 extends Fragment {
    String choice;
    ImageView profilepic;
    RecyclerView recyclerView;
    MyAdapter adapter;
    TextView name, email, phone, college, id;
    Button logout, remove;
    ArrayList<User> list;
    StorageReference storageReference;
    LinearLayout profile, home;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    RecyclerView.LayoutManager layoutManager;
    EditText search;
    TextWatcher watcher = null;
    Uri download;
    DatabaseReference databaseReference;
    String type;
    String rev;
    CardView sel;


    @SuppressLint("ValidFragment")
    public Fragment123(String choice) {
        this.choice = choice;
    }

    @SuppressLint("RestrictedApi")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentt, container, false);
        progressDialog = new ProgressDialog(getActivity(), R.style.AppCompatAlertDialogStyle);
        progressDialog.setMessage("Please Wait.....");
        profile = view.findViewById(R.id.profile);
        home = view.findViewById(R.id.home);
        profilepic = view.findViewById(R.id.profilepic);
        name = view.findViewById(R.id.name);
        email = view.findViewById(R.id.email);
        sel = view.findViewById(R.id.sel);
        recyclerView = view.findViewById(R.id.list);
        phone = view.findViewById(R.id.phone);
        id = view.findViewById(R.id.id);
        college = view.findViewById(R.id.college);
        logout = view.findViewById(R.id.logout);

        search = view.findViewById(R.id.search);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        storageReference = FirebaseStorage.getInstance().getReference();


        if (user != null) {
            // Name, email address, and profile photo Url
            type = user.getDisplayName();
            // Toast.makeText(getActivity(), type, Toast.LENGTH_SHORT).show();

        }

        if (type.equals("Student")) {
            rev = "Teacher";
        } else {
            rev = "Student";
        }

        if (choice.equals("home")) {
            profile.setVisibility(View.GONE);
            progressDialog.show();


            search.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {


                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (search.getText().toString().isEmpty()) {
                        //   recyclerView.setVisibility(View.INVISIBLE);

                    } else {

                        //  recyclerView.setVisibility(View.VISIBLE);
                    }

                }

                @Override
                public void afterTextChanged(Editable s) {
                    filter(s.toString());
                }
            });

            DatabaseReference two = databaseReference.child(rev);
            two.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    list = new ArrayList<>();


                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        User p = dataSnapshot1.getValue(User.class);
                        list.add(p);
                    }


                    adapter = new MyAdapter(getActivity(), list);
                    recyclerView.setAdapter(adapter);
//                    int position = recyclerView.getAdapter().getItemCount() - 1;
//                    recyclerView.scrollToPosition(position);
                    progressDialog.dismiss();
                    adapter.notifyDataSetChanged();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Toast.makeText(Chat.this, "Opsss.... Something is wrong", Toast.LENGTH_SHORT).show();
                }
            });

        } else if (choice.equals("dashboard")) {
            profile.setVisibility(View.GONE);
            home.setVisibility(View.GONE);


        } else if (choice.equals("profile")) {

            home.setVisibility(View.GONE);
            profile.setVisibility(View.VISIBLE);
            sel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent i = new Intent(Intent.ACTION_PICK);
                    i.setType("image/*");
                    startActivityForResult(i, 100);


                }
            });


            databaseReference = FirebaseDatabase.getInstance().getReference(type).child(FirebaseAuth.getInstance()
                    .getCurrentUser().getUid());

            progressDialog.show();


            databaseReference.child("id").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String iid = dataSnapshot.getValue(String.class);
                    id.setText(iid);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            databaseReference.child("name").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String iname = dataSnapshot.getValue(String.class);
                    name.setText(iname);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            databaseReference.child("email").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String iemail = dataSnapshot.getValue(String.class);
                    email.setText(iemail);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            databaseReference.child("num").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String iphone = dataSnapshot.getValue(String.class);
                    phone.setText(iphone);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            databaseReference.child("college").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String icollege = dataSnapshot.getValue(String.class);
                    college.setText(icollege);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            databaseReference.child("download").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String name = dataSnapshot.getValue(String.class);
                    Picasso.with(getActivity()).load(name).into(profilepic);
                    progressDialog.dismiss();


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences sharedpreferences = getActivity().getSharedPreferences("my_key", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.clear();
                    editor.commit();
                    firebaseAuth.signOut();
                    startActivity(new Intent(getActivity(), MainActivity.class));
                    getActivity().finish();
                }
            });




        }
        return view;
    }


    private void filter(String text) {
        //new array list that will hold the filtered data
        ArrayList<User> filterdNames = new ArrayList<>();
        //looping through existing elements
        for (User s : list) {
            //if the existing elements contains the search input
            if (s.getName().toLowerCase().contains(text.toLowerCase())) {
                //adding the element to filtered list
                filterdNames.add(s);
            }else if(s.getId().contains(text)){

                filterdNames.add(s);
            }
        }
        //calling a method of the adapter class and passing the filtered list
        adapter.filterList(filterdNames);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            progressDialog.show();
            Uri uri = data.getData();
            StorageReference child = storageReference.child("MyOne").child(FirebaseAuth.getInstance()
                    .getCurrentUser().getUid());
            //  Toast.makeText(getActivity(), "one", Toast.LENGTH_SHORT).show();

            child.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //  Toast.makeText(getActivity(), "two", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                    download = taskSnapshot.getDownloadUrl();
                    String link = download.toString();
                    databaseReference.child("download").setValue(link);

                    Picasso.with(getActivity()).load(download).into(profilepic);


                }
            });

        }
    }

}