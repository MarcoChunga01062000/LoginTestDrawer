package com.example.logintest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    EditText emailId, paswdId;
    Button btn;
    TextView txts;
    FirebaseAuth miAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        miAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.email);
        paswdId = findViewById(R.id.pswd);
        btn = findViewById(R.id.button);
        txts = findViewById(R.id.txtregistrar);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailId.getText().toString();
                String pwd = paswdId.getText().toString();
                if (email.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Ingrese un correo!", Toast.LENGTH_SHORT).show();
                    emailId.setError("Ingrese Email");
                    emailId.requestFocus();
                }
                else if (pwd.isEmpty()){
                    Toast.makeText(MainActivity.this, "Ingrese una contraseña!", Toast.LENGTH_SHORT).show();
                    paswdId.setError("Ingrese Contraseña");
                    paswdId.requestFocus();
                }
                else if (email.isEmpty() && pwd.isEmpty()){
                    Toast.makeText(MainActivity.this, "Todo está vacío.", Toast.LENGTH_SHORT).show();
                }
                else if (!(email.isEmpty() && pwd.isEmpty())){
                    miAuth.createUserWithEmailAndPassword(email,pwd).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()){
                                Toast.makeText(MainActivity.this, "Error, inténtelo de nuevo", Toast.LENGTH_SHORT).show();
                            } else {
                                startActivity(new Intent(MainActivity.this, LoginActiviy.class));
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(MainActivity.this, "Inválido! Verifique los datos.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        txts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent log = new Intent(MainActivity.this, LoginActiviy.class);
                startActivity(log);
            }
        });
    }

}
