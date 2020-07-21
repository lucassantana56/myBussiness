package com.example.mybussiness.controller;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybussiness.R;
import com.example.mybussiness.activity_create_user;
import com.example.mybussiness.model.User;

public class UserListViewHolder extends RecyclerView.ViewHolder {

    public TextView userName;
    public TextView userBalance;
    public Button login, delete;
    public ImageView UserBackImage;

    public UserListViewHolder(@NonNull View itemView) {
        super(itemView);
        userName = itemView.findViewById(R.id.textView_listUserName);
        userBalance = itemView.findViewById(R.id.textView_listBalance);
        login = itemView.findViewById(R.id.btn_login);
        UserBackImage = itemView.findViewById(R.id.imageView_ListUser);
        delete = itemView.findViewById(R.id.btn_delete);
    }
}

