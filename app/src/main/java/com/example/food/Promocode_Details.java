package com.example.food;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class Promocode_Details extends AppCompatActivity {
RecyclerView rcView;
TextView totalAmount;
EditText promocode;
Button add,makePayment;
Cart_Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promocode_details);
        rcView=findViewById(R.id.promocodeRecyclerView);
        promocode=findViewById(R.id.promocodeText);
        totalAmount=findViewById(R.id.totalAmount);
        add=findViewById(R.id.addPromocode);
        makePayment=findViewById(R.id.makePayment);
        rcView.setLayoutManager(new LinearLayoutManager(this));
        Intent intent=getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        ArrayList<OrderModal> arr= (ArrayList<OrderModal>) args.getSerializable("array");
        adapter=new Cart_Adapter(this,arr);
        rcView.setAdapter(adapter);
        int Total=intent.getIntExtra("cartTotal",-1);
        totalAmount.setText("Total amout to pay : "+Total);
    }
}