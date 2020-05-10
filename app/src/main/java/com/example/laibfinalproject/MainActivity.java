package com.example.laibfinalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    EditText etEmail, etPassword;
    Button bt_login;
    TextView tvRegister;
    private FirebaseAuth objectFirebaseAuth;
//LOGIN SCREEN
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etEmail = findViewById(R.id.etEmailL);
        objectFirebaseAuth = FirebaseAuth.getInstance();
        etPassword = findViewById(R.id.etPasswordL);
        bt_login = findViewById(R.id.bt_loginL);
        tvRegister = findViewById(R.id.tvRegister);
        
        bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
         signInUser ();

            }
        });
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent IntentClick=new Intent(MainActivity.this,RegistrationForm.class);
                startActivity(IntentClick);
                finish();
            }
        });

    }
    public void signInUser() {
        try {

            if (!etEmail.getText().toString().isEmpty() && !etPassword.getText().toString().isEmpty()) {
                if (objectFirebaseAuth.getCurrentUser() != null) {
                    objectFirebaseAuth.signOut();
                    //.setVisibility(View.INVISIBLE);
                    Toast.makeText(this, "User Logged Out Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, NavigationDrawer.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    objectFirebaseAuth.signInWithEmailAndPassword(etEmail.getText().toString(),
                            etPassword.getText().toString())
                            .addOnSuccessListener(new OnSuccessListener<AuthResult> () {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                   // bar.setVisibility(View.INVISIBLE);
                                    Toast.makeText(MainActivity.this, "User Logged In", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(MainActivity.this, MainActivity.class));
                                    finish();
                                }
                            }).addOnFailureListener(new OnFailureListener () {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            //signin.setEnabled(true);
                            etEmail.requestFocus();

                          //  bar.setVisibility(View.INVISIBLE);
                            Toast.makeText(MainActivity.this, "Fails To Sig-in User: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            } else if (etEmail.getText().toString().isEmpty()) {
                etEmail.requestFocus();
                //bar.setVisibility(View.INVISIBLE);
                Toast.makeText(this, "Please Enter The Email", Toast.LENGTH_SHORT).show();
            } else if (etPassword.getText().toString().isEmpty()) {
                etPassword.requestFocus();
              //  bar.setVisibility(View.INVISIBLE);
                Toast.makeText(this, "Please Enter The Password", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception ex) {

            etEmail.requestFocus();
            //bar.setVisibility(View.INVISIBLE);
            Toast.makeText(this, "Logging In Error" + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}