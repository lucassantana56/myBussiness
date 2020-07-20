package com.example.mybussiness;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mybussiness.DAO.UserDAO;
import com.example.mybussiness.adapter.UserListAdapter;
import com.example.mybussiness.model.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btnCreateNewUser;
    private List<User> users;
    RecyclerView recyclerView;
    UserListAdapter userListAdapter;
    UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userDAO = new UserDAO(this);
        users = userDAO.GetUser();

        btnCreateNewUser = findViewById(R.id.btn_gotoCreate);

        btnCreateNewUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(getApplicationContext(), activity_create_user.class);
                startActivity(intent);
            }
        });


        createRecycleView();

        createNoticationChannel();
    }


    private void createRecycleView() {
        recyclerView = (RecyclerView) findViewById(R.id.listUser);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        layoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(layoutManager);

        userListAdapter = new UserListAdapter(users, this);
        recyclerView.setAdapter(userListAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        users = userDAO.GetUser();
        createRecycleView();

    }

    private void createNoticationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "notify";

            String description = "teste";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel("notify", name, importance);
            channel.setDescription(description);


            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}