package com.example.mybussiness.controller;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybussiness.R;
import com.example.mybussiness.activity_create_user;
import com.example.mybussiness.model.User;

public class UserListViewHolder extends RecyclerView.ViewHolder {

    public TextView userName;
    public TextView userBalance;

    public UserListViewHolder(@NonNull View itemView) {
        super(itemView);
        userName = itemView.findViewById(R.id.textView_listUserName);
        userBalance = itemView.findViewById(R.id.textView_listBalance);
    }
}

