package com.example.food;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    TextInputLayout email,pass;
    Button btn;
    ProgressBar bar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email=(TextInputLayout)findViewById(R.id.email);
        pass=(TextInputLayout)findViewById(R.id.pass);
        bar=(ProgressBar) findViewById(R.id.progressBar);
        btn=(Button)findViewById(R.id.button);
        Intent intent=getIntent();
        int total=intent.getIntExtra("total",-1);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bar.setVisibility(View.VISIBLE);
                String  Email =email.getEditText().getText().toString();
                String  password =pass.getEditText().getText().toString();


                if(TextUtils.isEmpty(Email))
                {
                    Toast.makeText(LoginActivity.this, "Email required", Toast.LENGTH_SHORT).show();
                    email.getEditText().setError("Email  is required");
                    return;
                }
                if(TextUtils.isEmpty(password))
                {Toast.makeText(LoginActivity.this, "Password is required", Toast.LENGTH_LONG).show();
                    pass.getEditText().setError("Passward  is required");
                    return;
                }


                mAuth = FirebaseAuth.getInstance();

                mAuth.signInWithEmailAndPassword(Email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_LONG).show();
                            Intent intent=new Intent(LoginActivity.this, PaymentActivity.class);
                            // Intent intentSecond=new Intent(MainActivity.this,secondActivity.class);
                            intent.putExtra("total",total);
                            startActivity(intent);
                            email.getEditText().setText("");
                            pass.getEditText().setText("");
                            bar.setVisibility(View.INVISIBLE);
                        }
                        else
                        {
                            Toast.makeText(LoginActivity.this, "Error "+task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            email.getEditText().setText("");
                            pass.getEditText().setText("");
                            bar.setVisibility(View.INVISIBLE);
                        }

                    }
                });
            }
        });




    }
}