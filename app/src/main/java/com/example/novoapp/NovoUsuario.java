package com.example.novoapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class NovoUsuario extends AppCompatActivity {
    private Button btnSalvar, btnCancela;
    private EditText etEmail,etSenha,etConfSenha, etNome;
    private FirebaseAuth auth;
    private FirebaseUser usuario;
    private FirebaseAuth.AuthStateListener authStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo);

        etNome = findViewById(R.id.edtNome);
        etConfSenha = findViewById(R.id.edtConfSenha);
        etSenha = findViewById(R.id.edtPassword);
        etEmail = findViewById(R.id.edtEmail);
        btnSalvar = findViewById(R.id.btnConfirmar);
        btnCancela = findViewById(R.id.btnCancelar);

        auth = FirebaseAuth.getInstance();

        //botao salvar
        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = etNome.getText().toString();
                String email = etEmail.getText().toString();
                String senha = etSenha.getText().toString();
                String confSenha = etConfSenha.getText().toString();

                auth = FirebaseAuth.getInstance();

                if(!nome.isEmpty() && !email.isEmpty() && !senha.isEmpty() && !confSenha.isEmpty()){
                    if(confSenha .equals(senha)){
                        auth.createUserWithEmailAndPassword(email,senha).addOnCompleteListener(NovoUsuario.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    usuario = auth.getCurrentUser();
                                    finish();
                                } else {
                                    Toast.makeText(NovoUsuario.this,"Erro ao cadastrar usuário.", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    } else {
                        Toast.makeText(NovoUsuario.this,"As senhas não correspondem", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        //botao cancelar
        btnCancela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
            }
        });
    }
}
