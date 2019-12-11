package com.example.rayatuniv;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.ArrayList;
import java.util.Date;

public class MyAdapterChat extends RecyclerView.Adapter<MyAdapterChat.MyViewHolder> {

    Context context;
    ArrayList<ChatLayout> messages;
    FirebaseAuth firebaseAuth;

    DatabaseReference databaseReference;
    String iname;


    public MyAdapterChat(Context c, ArrayList<ChatLayout> p) {
        context = c;
        messages = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.chat_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        databaseReference = FirebaseDatabase.getInstance().getReference(user.getDisplayName()).child(user.getUid());


        databaseReference.child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                iname = dataSnapshot.getValue(String.class);
                // Toast.makeText(context,"2"+ iname, Toast.LENGTH_SHORT).show();
                if (iname.equals(messages.get(position).getFrom())) {

                    holder.two.setVisibility(View.VISIBLE);

                    holder.ifrom.setText("You");
                    holder.imessage.setText(messages.get(position).getMessage());
                    long time = messages.get(position).getTime();
                    String itime = DateFormat.format("dd/MM/yyy hh:mm", new Date(time)).toString();
                    holder.itime.setText(itime);
                } else {
                    holder.one.setVisibility(View.VISIBLE);

                    holder.from.setText(messages.get(position).getFrom());
                    holder.message.setText(messages.get(position).getMessage());
                    long time = messages.get(position).getTime();
                    String itime = DateFormat.format("dd/MM/yyy hh:mm", new Date(time)).toString();
                    holder.time.setText(itime);

                }
                // holder.from.setText(messages.get(position).getFrom());
//                holder.message.setText(messages.get(position).getMessage());
//                long time = messages.get(position).getTime();
//                String itime = DateFormat.format("dd/MM/yyy hh:mm:ss", new Date(time)).toString();
//                holder.time.setText(itime);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView from, message, time;
        TextView ifrom, imessage, itime;
        LinearLayout one, two;

        public MyViewHolder(View itemView) {
            super(itemView);
            from = itemView.findViewById(R.id.from);
            message = itemView.findViewById(R.id.message);
            time = itemView.findViewById(R.id.time);
            ifrom = itemView.findViewById(R.id.ifrom);
            imessage = itemView.findViewById(R.id.imessage);
            itime = itemView.findViewById(R.id.itime);

            one=itemView.findViewById(R.id.one);
            two=itemView.findViewById(R.id.two);


        }

    }
}
