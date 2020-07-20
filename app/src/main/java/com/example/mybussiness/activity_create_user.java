package com.example.mybussiness;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mybussiness.DAO.UserDAO;
import com.example.mybussiness.model.User;
import com.example.mybussiness.provider.UserProvider;

public class activity_create_user extends AppCompatActivity {

    private static final int GETCONTENT = 1;
    EditText editUserName, editMensalBalance;
    Button btnCreate;
    ImageView imageView_createUSer;
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
                    BitmapDrawable draw = (BitmapDrawable) imageView_createUSer.getDrawable();
                    Bitmap bmp = draw.getBitmap();
                    User u = new User(editUserName.getText().toString(), balance, bmp);
                    long id = userDAO.InsertUser(u);

                    Toast.makeText(activity_create_user.this, "User created com ID" + id, Toast.LENGTH_SHORT).show();
                    finish();
                } catch (Exception error) {
                    AlertDialog.Builder cx = new AlertDialog.Builder(activity_create_user.this);
                    cx.setTitle("Erro").setMessage(error.getMessage()).show();
                }

                finish();
            }
        });

        imageView_createUSer = findViewById(R.id.imageView_CreateUser);
        imageView_createUSer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(Intent.ACTION_GET_CONTENT);
                it.setType("image/*");
                startActivityForResult(it, GETCONTENT);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && requestCode == GETCONTENT) {
            Uri uri = Uri.parse(data.getData().toString());
            imageView_createUSer.setImageURI(uri);
        }
    }
}