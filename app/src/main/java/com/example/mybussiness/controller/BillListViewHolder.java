package com.example.mybussiness.controller;

import android.content.ContentResolver;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybussiness.R;

public class BillListViewHolder extends RecyclerView.ViewHolder {

    public TextView textName, textDescription, textExpirationDate;
    public Button btnDelete;

    public BillListViewHolder(@NonNull View itemView) {
        super(itemView);
        textName = itemView.findViewById(R.id.textView_name);
        textDescription = itemView.findViewById(R.id.textView_description);
        textExpirationDate = itemView.findViewById(R.id.textView_expirationDate);
        btnDelete = itemView.findViewById(R.id.btnDelete);
    }


}
