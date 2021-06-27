package com.example.signup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Guideline;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Adapter  extends RecyclerView.Adapter<Adapter.ViewHolder> {
    Context context;
    ArrayList<User>arrayList;
    DashboardActivity dashboardActivity;


    public Adapter(DashboardActivity dashboardActivity, Context context, ArrayList<User> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        this.dashboardActivity  =  dashboardActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_tile,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.username.setText(arrayList.get(position).getUsername());
        holder.email.setText(arrayList.get(position).getEmail());
        Glide.with(context).load(arrayList.get(position).getImageUri()).placeholder(R.drawable.ic_baseline_person_24).into(holder.circleImageView);

       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent i = new Intent(context,ProfileActivity.class);
               i.putExtra("uid",arrayList.get(position).getUid());
               dashboardActivity.startActivity(i);
           }
       });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView username, email;
        CircleImageView circleImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.rowUsername);
            email = itemView.findViewById(R.id.rowImage);
            circleImageView = itemView.findViewById(R.id.circleimageView);

        }
    }
}
