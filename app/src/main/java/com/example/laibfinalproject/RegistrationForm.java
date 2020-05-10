package com.example.laibfinalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegistrationForm extends AppCompatActivity {
    EditText etName,etEmail,etPass,etConfirmPass;
   Button btn_register;
   FirebaseAuth auth;
   DatabaseReference references;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_form);
        etName=findViewById(R.id.etNameR);
        etEmail=findViewById(R.id.EtEmailR);
        etPass=findViewById(R.id.etPassR);
        etConfirmPass=findViewById(R.id.etConfirmPassR);
        btn_register=findViewById(R.id.RegisterR);


        auth=FirebaseAuth.getInstance();



        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name, email, password,confirmPass;
                name=etName.getText().toString();
                email=etEmail.getText().toString();
                password=etPass.getText().toString();
                confirmPass=etConfirmPass.getText().toString();
                if (name.equals("")) {
                    Toast.makeText(RegistrationForm.this, "Name Required", Toast.LENGTH_SHORT).show();
                }
                else if (email.equals("")) {
                    Toast.makeText(RegistrationForm.this, "Emails Required", Toast.LENGTH_SHORT).show();
                }
                else if (password.equals("")) {
                    Toast.makeText(RegistrationForm.this, "Password Required", Toast.LENGTH_SHORT).show();
                }

                else if (confirmPass.equals("")) {
                    Toast.makeText(RegistrationForm.this, "Password Required", Toast.LENGTH_SHORT).show();
                }
                else if (password.length()<6) {
                    Toast.makeText(RegistrationForm.this, "Password must be at least 6 characters ", Toast.LENGTH_SHORT).show();
                }


                else {
                        register(name,email,password,confirmPass);
                }
            }
        });


    }
    private void register( final String etName,String etEmail,String etPass,String etConfirmPass){
        auth.createUserWithEmailAndPassword(etEmail, etPass)
                .addOnCompleteListener(RegistrationForm.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {

                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            assert firebaseUser!=null;
                            String userId =firebaseUser.getUid();
                            references = FirebaseDatabase.getInstance().getReference().child(userId);
                            HashMap<String,String> hashMap= new HashMap<>();
                            hashMap.put("id", userId);
                            hashMap.put("username",etName);
                            hashMap.put("imageURL","default");
                            references.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(RegistrationForm.this, MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                    }
                            });
                                }
else {
                            Toast.makeText(RegistrationForm.this, "You can't register with this email or password", Toast.LENGTH_SHORT).show();
                        }
                                }
                });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent IntentClick=new Intent(RegistrationForm.this,MainActivity.class);
                startActivity(IntentClick);
                finish();
            }
        });

                }


    }

