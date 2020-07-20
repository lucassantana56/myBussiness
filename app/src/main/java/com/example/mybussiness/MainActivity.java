package com.example.mybussiness;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mybussiness.DAO.UserDAO;
import com.example.mybussiness.adapter.UserListAdapter;
import com.example.mybussiness.model.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btnCreateNewUser, btnLogin;
    private List<User> users;
    RecyclerView recyclerView;
    UserListAdapter userListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UserDAO userDAO = new UserDAO(this);
        users = userDAO.GetUser();

        btnCreateNewUser = findViewById(R.id.btn_gotoCreate);

        btnCreateNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(getApplicationContext(), activity_create_user.class);
                startActivity(intent);
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.listUser);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        layoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(layoutManager);

        userListAdapter = new UserListAdapter(users);
        recyclerView.setAdapter(userListAdapter);
    }
}