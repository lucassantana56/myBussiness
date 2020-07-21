package com.example.mybussiness;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.mybussiness.DAO.UserDAO;
import com.example.mybussiness.model.User;

public class activity_UpdateUser extends AppCompatActivity {


    private static final int GETCONTENT = 1;
    ImageView imageViewBackGroundUser;
    EditText editTextMensalBalance, editTextUserName;
    Button btnUpdateUser;

    UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__update_user);

        imageViewBackGroundUser = findViewById(R.id.imageView_updateUser);
        editTextMensalBalance = findViewById(R.id.editText_updateUser_balance);
        editTextUserName = findViewById(R.id.editText_updateUser_name);
        btnUpdateUser = findViewById(R.id.btn_UpdateUser);

        userDAO = new UserDAO(this);
        final long userId = getIntent().getLongExtra("USERID", 0);

        if (userId != 0) {

            User user = userDAO.GetUser((int) userId);

            imageViewBackGroundUser.setImageBitmap(user.getBitmap());
            editTextMensalBalance.setText(String.valueOf(user.get_balance()));
            editTextUserName.setText(String.valueOf(user.get_balance()));
        }
        btnUpdateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Float balance = Float.parseFloat(editTextMensalBalance.getText().toString());
                BitmapDrawable draw = (BitmapDrawable) imageViewBackGroundUser.getDrawable();
                Bitmap bmp = draw.getBitmap();
                User user = new User(userId, editTextUserName.getText().toString(), balance, bmp);

                userDAO.UpdateUser(user);

                Intent intent = new Intent(v.getContext(), activity_billsList.class);
                intent.putExtra("USERID", userId);
                intent.putExtra("USERNAME", user.get_name());
                intent.putExtra("BALANCE", user.get_balance());
                startActivity(intent);
                finish();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && requestCode == GETCONTENT) {
            Uri uri = Uri.parse(data.getData().toString());
            imageViewBackGroundUser.setImageURI(uri);
        }
    }
}