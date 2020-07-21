package com.example.mybussiness.adapter;

import android.content.Context;
import android.content.Intent;
import android.service.autofill.UserData;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybussiness.DAO.UserDAO;
import com.example.mybussiness.R;
import com.example.mybussiness.activity_billsList;
import com.example.mybussiness.activity_updateBill;
import com.example.mybussiness.controller.UserListViewHolder;
import com.example.mybussiness.model.Bill;
import com.example.mybussiness.model.User;

import java.util.List;

import static androidx.core.content.ContextCompat.startActivity;

public class UserListAdapter extends RecyclerView.Adapter<UserListViewHolder> {
    List<User> users;
    private Context _context;
    UserDAO userDao;

    public UserListAdapter(List<User> users, Context context) {
        this.users = users;
        _context = context;
        userDao = new UserDAO(_context);
    }


    @NonNull
    @Override
    public UserListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.clickable_column, parent, false);
        return new UserListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserListViewHolder holder, int position) {
        final User u = users.get(position);
        holder.userBalance.setText(u.get_balance().toString() + " EUROS");
        holder.userName.setText(u.get_name());
        holder.UserBackImage.setImageBitmap(u.getBitmap());
        holder.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), activity_billsList.class);
                intent.putExtra("USERID", u.get_iD());
                intent.putExtra("USERNAME", u.get_name());
                intent.putExtra("BALANCE", u.get_balance());
                _context.startActivity(intent);
            }
        });

        holder.delete.setTag(u);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User u = (User) v.getTag();

                try {
                    userDao.DeleteUser(u.get_iD());
                    users.remove(u);
                } catch (Exception e) {
                    Toast.makeText(_context, e.getMessage(), Toast.LENGTH_SHORT).show();
                }


            }
        });
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
