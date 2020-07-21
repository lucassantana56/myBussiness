package com.example.mybussiness;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;

import com.example.mybussiness.DAO.BillDAO;
import com.example.mybussiness.DAO.UserDAO;
import com.example.mybussiness.controller.ReminderBroadCast;
import com.example.mybussiness.model.Bill;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class activity_add_bill extends AppCompatActivity {

    EditText editName, editDesc, editExpirationDate, editAmount;
    SwitchMaterial switchRepeat;
    Button btnAddNewBill;
    Calendar myCalendar = Calendar.getInstance();


    private UserDAO userDAO;
    private BillDAO billDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_bill);

        final Float balance = getIntent().getFloatExtra("BALANCE", 0);
        final long userId = getIntent().getLongExtra("USERID", 0);
        final String userName = getIntent().getStringExtra("USERNAME");

        userDAO = new UserDAO(this);
        billDAO = new BillDAO(this);

        editName = findViewById(R.id.editText_addBill_name);
        editDesc = findViewById(R.id.editText_addBill_description);
        editExpirationDate = findViewById(R.id.editText_addBill_expirationDate);
        editAmount = findViewById(R.id.editText_addBill_amount);
        switchRepeat = findViewById(R.id.switch_addBill_repeat);
        btnAddNewBill = findViewById(R.id.btn_addNewBill);

        btnAddNewBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Bill b = new Bill(editName.getText().toString(),
                            editDesc.getText().toString(),
                            Double.parseDouble(editAmount.getText().toString()),
                            dateFormat.parse(editExpirationDate.getText().toString()),
                            switchRepeat.isChecked(), Integer.parseInt(String.valueOf(userId)));

                    billDAO.InsertBill(b);

                    userDAO.UpdateUserBalance(userId, (float) (balance - b.get_amount()));


                    Intent intent = new Intent(v.getContext(), activity_billsList.class);

                    intent.putExtra("USERID", userId);
                    intent.putExtra("USERNAME", userName);
                    intent.putExtra("BALANCE", balance);
                    startActivity(intent);
                    createNoticationEvent();
                    finish();

                } catch (Exception error) {
                    AlertDialog.Builder cx = new AlertDialog.Builder(v.getContext());
                    cx.setTitle("Erro").setMessage(error.getMessage()).show();
                }
            }
        });

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        editExpirationDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(v.getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


    }


    private void createNoticationEvent() {

        Intent intent = new Intent(this, ReminderBroadCast.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        long timeAtButtonClicked = System.currentTimeMillis();

        long tenSecodsInMilis = 100 * 10;

        myCalendar.set(Calendar.SECOND, 0);
        myCalendar.set(Calendar.MINUTE, 8);

        alarmManager.set(AlarmManager.RTC_WAKEUP,
                tenSecodsInMilis,
                pendingIntent
        );
    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editExpirationDate.setText(sdf.format(myCalendar.getTime()));
    }
}