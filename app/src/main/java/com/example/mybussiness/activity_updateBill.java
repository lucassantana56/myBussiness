package com.example.mybussiness;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.mybussiness.DAO.BillDAO;
import com.example.mybussiness.model.Bill;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class activity_updateBill extends AppCompatActivity {

    BillDAO billDAO;
    EditText editText_updateBillName, editText_UpdateBill_description, editText_UpdateBill_expirationDate, editText_Update_amount;
    Button btnUpdate, btnDelete;
    SwitchMaterial switchRepeat;
    Calendar myCalendar = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_bill);

        editText_updateBillName = findViewById(R.id.editText_UpdateBill_name);
        editText_UpdateBill_description = findViewById(R.id.editText_UpdateBill_description);
        editText_UpdateBill_expirationDate = findViewById(R.id.editText_UpdateBill_expirationDate);
        editText_Update_amount = findViewById(R.id.editText_Update_amount);
        btnUpdate = findViewById(R.id.btn_updateBill);
        switchRepeat = findViewById(R.id.switch_updateBill_repeat);
        btnDelete = findViewById(R.id.btn_deleteBill);

        billDAO = new BillDAO(this);
        final long billId = getIntent().getLongExtra("BILLID", 0);
        final long userId = getIntent().getLongExtra("USERID", 0);


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Bill b = new Bill(billId, editText_updateBillName.getText().toString(),
                            editText_UpdateBill_description.getText().toString(),
                            Double.parseDouble(editText_Update_amount.getText().toString()),
                            dateFormat.parse(editText_UpdateBill_expirationDate.getText().toString()),
                            switchRepeat.isChecked(), Integer.parseInt(String.valueOf(userId)));

                    billDAO.UpdateBill(b);

                    Intent intent = new Intent(v.getContext(), activity_billsList.class);
                    intent.putExtra("USERID", userId);
                    startActivity(intent);
                    finish();
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                billDAO.DeleteBill(billId);

                Intent intent = new Intent(v.getContext(), activity_billsList.class);
                intent.putExtra("USERID", userId);
                startActivity(intent);
                finish();
            }
        });


        try {
            Bill bill = billDAO.getBill(billId);
            editText_updateBillName.setText(bill.get_name());
            editText_UpdateBill_description.setText(bill.get_description());
            editText_UpdateBill_expirationDate.setText(String.valueOf(bill.get_expirationDate()));
            editText_Update_amount.setText(String.valueOf(bill.get_amount()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

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

        editText_UpdateBill_expirationDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(v.getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel() {
        String myFormat = "yyyy-MM-dd";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        editText_UpdateBill_expirationDate.setText(sdf.format(myCalendar.getTime()));
    }
}