package com.example.food;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

public class Menu_Page extends AppCompatActivity {
    RecyclerView rcView;
    Button cart,order,lowtohigh;
    Menu_Adapter adapter;
    Button lth,htl,range;
    EditText m1,m2;

    ArrayList<MenuModal> options=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_page);
        rcView=findViewById(R.id.recycler_menu);
        cart=findViewById(R.id.cart);
        order=findViewById(R.id.order);
        lth=findViewById(R.id.lth);
        htl=findViewById(R.id.htl);
        range=findViewById(R.id.range);
        m1=findViewById(R.id.min);
        m2=findViewById(R.id.max);
       // lowtohigh=findViewById(R.id.lth);
        Intent intent=getIntent();
        String ID=intent.getStringExtra("ID").toString();

        /*options=new FirebaseRecyclerOptions.Builder<MenuModal>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("menu").orderByChild("restro_id").equalTo(ID), MenuModal.class)
                .build();*/

        Query query =FirebaseDatabase.getInstance().getReference("menu").orderByChild("restro_id").equalTo(ID);
        query.addValueEventListener(mValueEventListener);

        rcView.setLayoutManager(new LinearLayoutManager(this));
         lth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Query q1= FirebaseDatabase.getInstance().getReference("menu").orderByChild("restro_id").equalTo(ID);
               // q1.orderByChild("price");
               // Toast.makeText(MainActivity2.this, "class to listener ", Toast.LENGTH_SHORT).show();
                q1.addValueEventListener(mValueEventListener);
            }
        });
        htl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Query query= FirebaseDatabase.getInstance().getReference("menu").orderByChild("restro_id").equalTo(ID);
                query.addValueEventListener(mValueEventListenererev);
                Toast.makeText(Menu_Page.this, "inside hight to low ", Toast.LENGTH_SHORT).show();
            }
        });
        range.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!m1.getText().toString().equals("") && !m2.getText().toString().equals(""))
                {

                    Query query = FirebaseDatabase.getInstance().getReference("menu").orderByChild("restro_id");
                    //query.orderByChild("price");
                    //query=query.startAt(m1.getText().toString()).endAt(m2.getText().toString());
                    Toast.makeText(Menu_Page.this, "inside range button ", Toast.LENGTH_SHORT).show();
                    query.addValueEventListener(mValueEventListener);
                }
                else {
                    //Toast.makeText(MainActivity2.this, "Enter minimum value ", Toast.LENGTH_SHORT).show();
                    if(m1.getText().toString().equals(""))
                    {
                        Toast.makeText(Menu_Page.this, "Enter minimum value ", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(Menu_Page.this, "Enter maximum Value ", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });



        cart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                int cartTotal=0;
                ArrayList<OrderModal> arr=new ArrayList();
                for(String keys:Menu_Adapter.map.keySet()){
                    arr.add(Menu_Adapter.map.get(keys));
                    cartTotal+=Menu_Adapter.map.get(keys).getTotalPrice();
                }
//                for(int i=0;i<arr.size();i++){
//                    Log.i("data",""+arr.get(i));
//                }
                Intent intent=new Intent(Menu_Page.this,Cart_Details.class);
                Bundle args = new Bundle();
                args.putSerializable("array",(Serializable)arr);
                intent.putExtra("BUNDLE",args);
                intent.putExtra("cartTotal",cartTotal);
                startActivity(intent);
            }

        });

        order.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                int cartTotal=0;
                ArrayList<OrderModal> arr=new ArrayList();
                for(String keys:Menu_Adapter.map.keySet()){
                    arr.add(Menu_Adapter.map.get(keys));
                    cartTotal+=Menu_Adapter.map.get(keys).getTotalPrice();
                }
                Intent intent=new Intent(Menu_Page.this,HomeActivity.class);
                intent.putExtra("total",cartTotal);
                startActivity(intent);
                Toast.makeText(Menu_Page.this, "go to login", Toast.LENGTH_SHORT).show();
            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        //adapter.startListening();
     }

    @Override
    protected void onStop() {
        super.onStop();
        //adapter.stopListening();
    }

    ValueEventListener mValueEventListener=new ValueEventListener() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if(snapshot.exists()) {
                options.clear();
                Toast.makeText(Menu_Page.this, "Exist ", Toast.LENGTH_SHORT).show();
                Toast.makeText(Menu_Page.this, "True", Toast.LENGTH_SHORT).show();
                int i=0;
                for(DataSnapshot snp: snapshot.getChildren())
                {
                    Toast.makeText(Menu_Page.this, " outt put "+snp.child("name").getValue().toString(), Toast.LENGTH_SHORT).show();
                    //discount,description,restro_id,price,url
                   // options.add(snp.getValue().toString());
                    options.add(new MenuModal(snp.child("name").getValue().toString(),snp.child("discount").getValue().toString(),snp.child("description").getValue().toString(),snp.child("restro_id").getValue().toString(),snp.child("price").getValue().toString(),snp.child("url").getValue().toString()));
                    String name1= options.get(i).name;
                    String dis1= options.get(i).discount;
                    String price1= options.get(i).price;
                    i++;

//Toast.makeText(Menu_Page.this, "name "+name1+"  dist "+dis1+"  price "+price1, Toast.LENGTH_SHORT).show();



                }
                ArrayList<MenuModal> sortedDates = (ArrayList<MenuModal>) options
                        .stream().sorted(Comparator.comparing(MenuModal::getPrice))
                        .collect(Collectors.toList());


                //Apater3 adpt = new Apater3(Menu_Page.this,options);
                Adapter2 adpt= new Adapter2(Menu_Page.this, sortedDates);
                rcView.setAdapter(adpt);

            }
            else
            {
                Toast.makeText(Menu_Page.this, "No output available for this input  ", Toast.LENGTH_SHORT).show();
                options.clear();

                Adapter2 adpt = new Adapter2(Menu_Page.this,options);
                rcView.setAdapter(adpt);

            }

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };



    ValueEventListener mValueEventListenererev=new ValueEventListener() {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @SuppressLint("NotifyDataSetChanged")
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            if(snapshot.exists()) {
                options.clear();
                Toast.makeText(Menu_Page.this, "Mlistener in rev", Toast.LENGTH_SHORT).show();
                for(DataSnapshot snp: snapshot.getChildren())
                {
                    //Toast.makeText(MainActivity.this, " outt put "+snp.child("name").getValue().toString(), Toast.LENGTH_SHORT).show();
                    options.add(new MenuModal(snp.child("name").getValue().toString(),snp.child("discount").getValue().toString(),snp.child("description").getValue().toString(),snp.child("restro_id").getValue().toString(),snp.child("price").getValue().toString(),snp.child("url").getValue().toString()));

                    /*Adapter2 adpt = new Adapter2(Menu_Page.this,options);
                    rcView.setAdapter(adpt);*/

                }
                //Collections.reverse(options);
               // Collections.sort(options, dateComparator);
               // Collections.reverse(options);

               ArrayList<MenuModal> sortedDatesDescending = (ArrayList<MenuModal>) options
                        .stream().sorted(Comparator.comparing(MenuModal::getPrice).reversed())
                        .collect(Collectors.toList());


                Adapter2 adpt = new Adapter2(Menu_Page.this,sortedDatesDescending);
                rcView.setAdapter(adpt);
                //sortedDatesDescending.clear();



            }
            else
            {
                //Toast.makeText(MainActivity2.this, "in activity ", Toast.LENGTH_SHORT).show();
                options.clear();
                Adapter2 adpt = new Adapter2(Menu_Page.this,options);
                rcView.setAdapter(adpt);

            }

        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };



}