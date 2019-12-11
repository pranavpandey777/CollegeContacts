package com.example.rayatuniv;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<User> messages;
    String iname;
    String iid;
    String iemail;
    String iphone;
    String idept;
    String ipic;
    String uid;



    public MyAdapter(Context c, ArrayList<User> p) {
        context = c;
        messages = p;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v= LayoutInflater.from(context).inflate(R.layout.layout_recycle, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(v, context, messages);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        iname=messages.get(position).getName();
        iemail=messages.get(position).getEmail();
        iid=messages.get(position).getId();
        iphone=messages.get(position).getNum();
        idept=messages.get(position).getDept();
        ipic=messages.get(position).getDownload();
        holder.name.setText(messages.get(position).getName());
        holder.id.setText(messages.get(position).getId());
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                iname=messages.get(position).getName();
                iemail=messages.get(position).getEmail();
                iid=messages.get(position).getId();
                iphone=messages.get(position).getNum();
                idept=messages.get(position).getDept();
                ipic=messages.get(position).getDownload();
                uid=messages.get(position).getUid();


                switch (v.getId()) {

                    case R.id.item:
                        // Toast.makeText(context, ""+pos, Toast.LENGTH_SHORT).show();
                        dialog(iname,iemail,iid,iphone,idept,ipic,uid);

                        break;

                }


            }
        });

        Picasso.with(context).load(messages.get(position).getDownload()).into(holder.pic);

    }
    public void filterList(ArrayList<User> filterdNames) {
        this.messages = filterdNames;
        notifyDataSetChanged();
    }



    @Override
    public int getItemCount() {
        return messages.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView name, id;
        ImageView pic;
        Context context;
        ArrayList<User> arrayList;
        LinearLayout item;

        public MyViewHolder(View itemView,Context context, ArrayList<User> arrayList) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            id = itemView.findViewById(R.id.id);
            item = itemView.findViewById(R.id.item);
            pic = itemView.findViewById(R.id.pic);
            this.context = context;
            this.arrayList = arrayList;
//            itemView.setOnClickListener(this);
//            item.setOnClickListener(this);

        }

//        @Override
//        public void onClick(View v) {
//            int pos = getAdapterPosition();
//           User data = arrayList.get(pos);
//           iname=data.getName();
//           iemail=data.getEmail();
//           iid=data.getId();
//           iphone=data.getNum();
//           idept=data.getDept();
//           ipic=data.getDownload();
//
//
//
//
//            switch (v.getId()) {
//
//                case R.id.item:
//                   // Toast.makeText(context, ""+pos, Toast.LENGTH_SHORT).show();
//                    dialog(iname,iemail,iid,iphone,idept,ipic);
//
//                    break;
//
//            }
//
//        }
//
    }

    public void dialog(String name,String email,String id,String phone,String dept, String pic,String uid) {
        this.iname=name;
        this.iemail=email;
        this.iid=id;
        this.iphone=phone;
        this.idept=dept;
        this.ipic=pic;
        this.uid=uid;
        DialogShow log = new DialogShow(name,email,id,phone,dept,pic,uid);
        log.show(((AppCompatActivity)context).getSupportFragmentManager(), "login");


    }
}

