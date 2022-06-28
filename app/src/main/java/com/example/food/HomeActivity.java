package com.example.food;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class HomeActivity extends AppCompatActivity {

    Button reg,login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        reg=findViewById(R.id.register);
        login=findViewById(R.id.login);
        Intent intent=getIntent();
        int total=intent.getIntExtra("total",-1);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, "Registration start", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(HomeActivity.this, MainActivityNew.class);
                // Intent intentSecond=new Intent(MainActivity.this,secondActivity.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, "Login Precess start", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(HomeActivity.this, LoginActivity.class);
                // Intent intentSecond=new Intent(MainActivity.this,secondActivity.class);
                intent.putExtra("total",total);
                startActivity(intent);

            }
        });

    }
}