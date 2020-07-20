package com.example.mybussiness.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybussiness.R;
import com.example.mybussiness.controller.UserListViewHolder;
import com.example.mybussiness.model.User;

import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListViewHolder> {
    List<User> users;

    public UserListAdapter(List<User> users) {
        this.users = users;
    }


    @NonNull
    @Override
    public UserListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.clickable_column, parent, false);
        return new UserListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserListViewHolder holder, int position) {
        User u = users.get(position);
        holder.userBalance.setText(u.get_balance().toString());
        holder.userName.setText(u.get_name());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    @Override
    public void onBindViewHolder(@NonNull UserListViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);

        User u = users.get(position);

    }
}
