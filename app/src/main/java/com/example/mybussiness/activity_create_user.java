package com.example.mybussiness;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mybussiness.DAO.UserDAO;
import com.example.mybussiness.model.User;
import com.example.mybussiness.provider.UserProvider;

public class activity_create_user extends AppCompatActivity {

    EditText editUserName, editMensalBalance;
    Button btnCreate;
    private UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        userDAO = new UserDAO(this);
        editUserName = findViewById(R.id.editText_createUser_name);
        editMensalBalance = findViewById(R.id.editText_createUser_balance);

        btnCreate = findViewById(R.id.btn_createNewUser);

        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Float balance = Float.parseFloat(editMensalBalance.getText().toString());
                    User u = new User(editUserName.getText().toString(), balance);
                    long id = userDAO.InsertUser(u);

                    Toast.makeText(activity_create_user.this, "User com ID" + id, Toast.LENGTH_SHORT).show();

                    finish();
                } catch (Exception error) {
                    AlertDialog.Builder cx = new AlertDialog.Builder(activity_create_user.this);
                    cx.setTitle("Erro").setMessage(error.getMessage()).show();
                }

                finish();


            }
        });

    }
}