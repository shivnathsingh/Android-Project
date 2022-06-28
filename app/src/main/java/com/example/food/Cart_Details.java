package com.example.food;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Cart_Details extends AppCompatActivity {
    RecyclerView cartRecyclerView;
    Cart_Adapter adapter;
    TextView cartTotal;
    Button orderNow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_details);
        cartRecyclerView=findViewById(R.id.cartRecyclerView);
        cartTotal=findViewById(R.id.cartPrice);
        orderNow=findViewById(R.id.orderNow);
        cartRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        Intent intent=getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        ArrayList<OrderModal>  arr= (ArrayList<OrderModal>) args.getSerializable("array");
        adapter=new Cart_Adapter(this,arr);
        cartRecyclerView.setAdapter(adapter);
        int Total=intent.getIntExtra("cartTotal",-1);
        cartTotal.setText("Total amout to pay : "+Total);
        orderNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Cart_Details.this,HomeActivity.class);
                intent.putExtra("total",Total);
                startActivity(intent);
                Toast.makeText(Cart_Details.this, "go to login", Toast.LENGTH_SHORT).show();
            }
        });
    }
}