package com.example.mybussiness;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Switch;

public class activity_add_bill extends AppCompatActivity {

    EditText editName, editDesc, editExpirationDate, editAmount;
    Switch switchRepeat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill);

        editName = findViewById(R.id.editText_addBill_name);
        editDesc = findViewById(R.id.editText_addBill_description);
        editExpirationDate = findViewById(R.id.editText_addBill_expirationDate);
        editAmount = findViewById(R.id.editText_addBill_amount);
        switchRepeat = findViewById(R.id.switch_addBill_repeat);


    }
}