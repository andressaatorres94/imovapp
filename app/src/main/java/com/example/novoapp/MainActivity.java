package com.example.novoapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private EditText edtEmail, edtPassword;
    private Button btnLogin, btnCadastrar;

    private FirebaseAuth auth;
    private FirebaseUser usuario;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtEmail = (EditText) findViewById(R.id.edtLogin);
        edtPassword = (EditText) findViewById(R.id.edtSenha);
        btnLogin = (Button) findViewById(R.id.btnEntrar);
        btnCadastrar = (Button) findViewById(R.id.btnCadastro);

        auth = FirebaseAuth.getInstance();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                usuario = auth.getCurrentUser();
                if (usuario != null){
                    Intent intent = new Intent(MainActivity.this, Inicio.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "ERRO.",Toast.LENGTH_LONG).show();
                }
            }
        };
        //Click botao entrar.
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString();
                String senha = edtPassword.getText().toString();

                if (!email.isEmpty() && !senha.isEmpty()) {

                    auth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Usuário ou senha incorreto.", Toast.LENGTH_LONG).show();
                                edtPassword.setBackgroundColor(Color.argb(50, 255, 0, 0));
                            } else {
                                Intent intent = new Intent(MainActivity.this, Inicio.class);
                                startActivity( intent );
                                finish();
                            }
                        }
                    });
                }
            }
        });

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NovoUsuario.class);
                startActivity( intent );
            }
        });
    }
}
