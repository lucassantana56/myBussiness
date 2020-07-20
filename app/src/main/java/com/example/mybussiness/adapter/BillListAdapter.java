package com.example.mybussiness.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mybussiness.DAO.BillDAO;
import com.example.mybussiness.R;
import com.example.mybussiness.activity_billsList;
import com.example.mybussiness.activity_updateBill;
import com.example.mybussiness.controller.BillListViewHolder;
import com.example.mybussiness.controller.UserListViewHolder;
import com.example.mybussiness.model.Bill;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class BillListAdapter extends RecyclerView.Adapter<BillListViewHolder> {
    List<Bill> bills;
    private Context _context;


    public BillListAdapter(List<Bill> bills, Context _context) {
        this.bills = bills;
        this._context = _context;
    }


    @NonNull
    @Override
    public BillListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bill, parent, false);
        return new BillListViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BillListViewHolder holder, int position) {
        Bill b = bills.get(position);

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        holder.textName.setText(b.get_name());
        holder.textDescription.setText(b.get_description());
        try {
            holder.textExpirationDate.setText(format.parse(b.get_expirationDate().toString()).toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.btnDelete.setTag(b);
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bill b = (Bill) v.getTag();

                try {
                    Intent intent = new Intent(v.getContext(), activity_updateBill.class);
                    intent.putExtra("BillId", b.get_iD());
                    _context.startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(_context, e.getMessage(), Toast.LENGTH_SHORT).show();
                }


            }
        });
    }


    @Override
    public int getItemCount() {
        return bills.size();
    }
}
