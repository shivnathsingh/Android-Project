package com.example.food;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivityNew extends AppCompatActivity {

    TextInputLayout t1,t2,name,mobile,dob;
    ProgressBar bar;
    private FirebaseAuth mAuth;
    FirebaseFirestore firestore;
    String Currentuserid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_new);
        t1=(TextInputLayout)findViewById(R.id.email);
        t2=(TextInputLayout)findViewById(R.id.pass);
        name=(TextInputLayout)findViewById(R.id.name);
        mobile=(TextInputLayout)findViewById(R.id.mobile);
        bar=(ProgressBar) findViewById(R.id.progressBar);
        dob=(TextInputLayout) findViewById(R.id.dob);
    }

    public void signuphere(View view) {
        bar.setVisibility(View.VISIBLE);
        String  email =t1.getEditText().getText().toString().trim();
        String  password =t2.getEditText().getText().toString().trim();
        String  name1 =name.getEditText().getText().toString().trim();
        String  mobile1 =mobile.getEditText().getText().toString().trim();
        String  dob1=dob.getEditText().getText().toString().trim();

        Log.i("ID ", email);
        Log.i("Pass", password);
        Log.i("Name", name1);
        Log.i("Mobile", mobile1);
        if(TextUtils.isEmpty(email))
        {
            Toast.makeText(this, "Email required", Toast.LENGTH_SHORT).show();
            t1.getEditText().setError("Email  is required");
            return;
        }
        if(TextUtils.isEmpty(dob1))
        {
            Toast.makeText(this, "Date oF birth empty", Toast.LENGTH_SHORT).show();
            dob.getEditText().setError("DOB is required");
            return;
        }
        if(TextUtils.isEmpty(password))
        {Toast.makeText(this, "Password is required", Toast.LENGTH_LONG).show();
            t2.getEditText().setError("Passward  is required");
            return;
        }
        if(TextUtils.isEmpty(name1))
        {
            Toast.makeText(this, "Name is required", Toast.LENGTH_LONG).show();
            name.getEditText().setError("Name is required");
            return;
        }
        if(TextUtils.isEmpty(mobile1))
        {
            Toast.makeText(this, "Mobile No is required", Toast.LENGTH_SHORT).show();
            mobile.getEditText().setError("Mobile No  is required");
            return;
        }
        if(mobile1.length()!=10)
        {
            Toast.makeText(this, "Mobile No must be of 10 digit", Toast.LENGTH_SHORT).show();
            mobile.getEditText().setError("Mobile No must be of 10 digit");
            return;
        }
        if(password.length()<=5)
        {
            Toast.makeText(this,"Pass must be atlest 6 length",Toast.LENGTH_LONG).show();
            t2.getEditText().setError("Pass must be atlest 6 length");
            return;
        }

        mAuth = FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(MainActivityNew.this, "Register Successfully", Toast.LENGTH_LONG).show();
                    Currentuserid = mAuth.getCurrentUser().getUid();
                    DocumentReference dr =firestore.collection("User").document(Currentuserid);
                    Map<String ,Object> user=new HashMap<>();
                    user.put("Name", name1);
                    user.put("Mobile",mobile1);
                    user.put("Dob", dob1);
                    user.put("Email",email);
                    user.put("Pass", password);
                    dr.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(MainActivityNew.this, "User profile created", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivityNew.this, "Profile not created "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                    Intent intent=new Intent(MainActivityNew.this, LoginActivity.class);
                    // Intent intentSecond=new Intent(MainActivity.this,secondActivity.class);
                    startActivity(intent);
                    t1.getEditText().setText("");
                    t2.getEditText().setText("");
                    name.getEditText().setText("");
                    mobile.getEditText().setText("");
                    dob.getEditText().setText("");
                    bar.setVisibility(View.INVISIBLE);
                }
                else
                {
                    Toast.makeText(MainActivityNew.this, "      error "+task.getException().getMessage(), Toast.LENGTH_LONG).show();
                   /* t1.getEditText().setText("");
                    t2.getEditText().setText("");
                    name.getEditText().setText("");
                    mobile.getEditText().setText("");*/
                    bar.setVisibility(View.INVISIBLE);
                }

            }
        });




    }
}