package com.example.logintest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActiviy extends AppCompatActivity {

    EditText emailId, paswdId;
    Button btn;
    TextView txts;
    FirebaseAuth miAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activiy);
        miAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.email);
        paswdId = findViewById(R.id.pswd);
        btn = findViewById(R.id.button);
        txts = findViewById(R.id.txtregistrar);

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = miAuth.getCurrentUser();
                if (mFirebaseUser != null){
                    Toast.makeText(LoginActiviy.this, "Ingresaste...! Congrats!", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LoginActiviy.this, Main2Activity.class);
                    startActivity(i);
                } else {
                    Toast.makeText(LoginActiviy.this, "Ingrese por favor.!", Toast.LENGTH_SHORT).show();
                }
            }
        };
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailId.getText().toString();
                String pwd = paswdId.getText().toString();
                if (email.isEmpty()) {
                    Toast.makeText(LoginActiviy.this, "Ingrese un correo!", Toast.LENGTH_SHORT).show();
                    emailId.setError("Ingrese Email");
                    emailId.requestFocus();
                }
                else if (pwd.isEmpty()){
                    Toast.makeText(LoginActiviy.this, "Ingrese una contraseña!", Toast.LENGTH_SHORT).show();
                    paswdId.setError("Ingrese Contraseña");
                    paswdId.requestFocus();
                }
                else if (email.isEmpty() && pwd.isEmpty()){
                    Toast.makeText(LoginActiviy.this, "Todo está vacío.", Toast.LENGTH_SHORT).show();
                }
                else if (!(email.isEmpty() && pwd.isEmpty())){
                    miAuth.signInWithEmailAndPassword(email,pwd).addOnCompleteListener(LoginActiviy.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(LoginActiviy.this, "Inválido. Verifique los datos.", Toast.LENGTH_SHORT).show();
                            } else {
                                Intent home = new Intent(LoginActiviy.this, HomeActivity.class);
                                startActivity(home);
                            }
                        }
                    });
                }
                else {
                    Toast.makeText(LoginActiviy.this, "GRAVE ERROR :(", Toast.LENGTH_SHORT).show();
                }
            }
        });
        txts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent lo = new Intent(LoginActiviy.this, MainActivity.class);
                startActivity(lo);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        miAuth.addAuthStateListener(mAuthStateListener);
    }
}
