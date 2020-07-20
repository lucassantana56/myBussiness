package com.example.mybussiness;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mybussiness.DAO.BillDAO;
import com.example.mybussiness.adapter.BillListAdapter;
import com.example.mybussiness.model.Bill;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.util.List;

public class activity_billsList extends AppCompatActivity {

    TextView textView_UserName, textView_Balance, textViewUserId;
    private List<Bill> bills;
    private BillDAO billDAO;
    BillListAdapter billListAdapter;
    RecyclerView recyclerView;
    BottomAppBar bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bills_list);


        final String userName = getIntent().getStringExtra("USERNAME");
        final Float balance = getIntent().getFloatExtra("BALANCE", 0);
        final long userId = getIntent().getIntExtra("USERID", 0);

        if (userId == 0) {
            Toast.makeText(this, "id é 0", Toast.LENGTH_SHORT).show();
        }

        billDAO = new BillDAO(this);

        try {
            bills = billDAO.getBills(userId);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        textView_UserName = findViewById(R.id.textView_UserName);
        textView_Balance = findViewById(R.id.textView_balance);
        bottomNavigationView = findViewById(R.id.bottomAppBar);
        textView_UserName.setText(userName);
        textView_Balance.setText(balance + " EUROS");

        FloatingActionButton floatingActionButton =
                (FloatingActionButton) findViewById(R.id.floating_action_button);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), activity_add_bill.class);
                intent.putExtra("USERID", userId);
                intent.putExtra("USERNAME", userName);
                intent.putExtra("BALANCE", balance);
                startActivity(intent);
            }
        });

        bottomNavigationView.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.item_person:
                        Intent intent = new Intent(getApplicationContext(), activity_UpdateUser.class);
                        intent.putExtra("USERID", userId);
                        startActivity(intent);
                        return true;
                    default:
                        return false;


                }
            }
        });

        createRecycleView();

    }


    private void createRecycleView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewBills);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        recyclerView.setLayoutManager(layoutManager);

        billListAdapter = new BillListAdapter(bills, this);
        recyclerView.setAdapter(billListAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();

        final long userId = getIntent().getIntExtra("USERID", 0);
        String userName = getIntent().getStringExtra("USERNAME");
        final Float balance = getIntent().getFloatExtra("BALANCE", 0);

        textView_UserName.setText(userName);
        textView_Balance.setText(balance + " EUROS");

        Toast.makeText(this, "Hello " + userId, Toast.LENGTH_SHORT).show();
        try {
            bills = billDAO.getBills(userId);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        createRecycleView();

    }
}