package com.example.food;



import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

public class PaymentActivity extends AppCompatActivity implements PaymentResultListener {
    Button btnpay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        btnpay=findViewById(R.id.btnpay);
        Intent intent=getIntent();
        int total=intent.getIntExtra("total",-1);
        int amount=Math.round(Float.parseFloat(""+total)*100);
        btnpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Checkout checkout=new Checkout();
                checkout.setKeyID("rzp_test_hWsjHYcXOd1TI5");
                checkout.setImage(R.drawable.rzp_logo);
                JSONObject object=new JSONObject();
                try {
                    object.put("name","Food private ltd.");
                    object.put("description","sample payment");
                    object.put("theme.color","#FF6600");
                    object.put("currency","INR");
                    object.put("amount",amount);
                    object.put("prefill.contact","7869749225");
                    object.put("prefill.email","agarwalgaurav.student@gmail.com");
                    checkout.open(PaymentActivity.this,object);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public void onPaymentSuccess(String s) {
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle("Payment ID");
        builder.setMessage(s);
        builder.show();
        Intent intent=new Intent(PaymentActivity.this,MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(getApplicationContext(),s, Toast.LENGTH_SHORT).show();

    }
}