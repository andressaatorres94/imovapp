package com.example.novoapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Inicio extends AppCompatActivity {
    private ListView lvImoveis;
    private List<Imovel> listaImoveis;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private ChildEventListener childEventListener;
    private Query query;
    private ArrayAdapter<Imovel> adapter;
    private FirebaseAuth auth;

    private Button btnSair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnSair = findViewById(R.id.btnSair);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Inicio.this, Formulario.class);
                startActivity(intent);
            }
        });

        lvImoveis = findViewById(R.id.lvImoveis);
        listaImoveis = new ArrayList<>();
        adapter = new ArrayAdapter<Imovel>(Inicio.this,android.R.layout.simple_list_item_1, listaImoveis);
        lvImoveis.setAdapter(adapter);

        lvImoveis.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                deletar(position);

                return true;
            }
        });

        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sair();
            }
        });

    }

    //Fazer o logout
    private void sair(){
        AlertDialog.Builder alerta = new AlertDialog.Builder(Inicio.this);
        alerta.setMessage("Deseja sair do ImovApp?");
        alerta.setNeutralButton("Não", null);
        alerta.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                auth.signOut();
                finish();
            }
        });
        alerta.show();
    }

    //Deletar imovel
    private void deletar(final int posicao){
        final Imovel imovelSelecionado = listaImoveis.get(posicao);

        AlertDialog.Builder alerta = new AlertDialog.Builder(Inicio.this);
        alerta.setTitle("Excluir Imóvel");
        alerta.setIcon( android.R.drawable.ic_delete );
        alerta.setMessage("Confirma a exclusão do produto " + imovelSelecionado.endereco + "?");
        alerta.setNeutralButton("Cancelar", null);
        alerta.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                reference.child("imoveis").child(imovelSelecionado.id).removeValue();
                listaImoveis.remove(posicao);
                adapter.notifyDataSetChanged();
            }
        });
        alerta.show();
    }


    @Override
    protected void onStart() {
        super.onStart();
        listaImoveis.clear();

        database =FirebaseDatabase.getInstance();
        reference = database.getReference();
        query = reference.child("imoveis").orderByChild("tipo");

        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Imovel im = new Imovel();

                im.id = dataSnapshot.getKey();
                im.tipo = dataSnapshot.child("tipo").getValue(String.class);
                im.endereco = dataSnapshot.child("endereco").getValue(String.class);
                im.bairro = dataSnapshot.child("bairro").getValue(String.class);
                im.cidade = dataSnapshot.child("cidade").getValue(String.class);
                im.tamanhoArea = dataSnapshot.child("tamanhoArea").getValue(Double.class);
                im.preco = dataSnapshot.child("preco").getValue(Double.class);
                im.valorIptu = dataSnapshot.child("valorIptu").getValue(Double.class);

                listaImoveis.add(im);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        query.addChildEventListener(childEventListener);
    }


    @Override
    protected void onStop() {
        super.onStop();
        query.removeEventListener(childEventListener);
    }
}
