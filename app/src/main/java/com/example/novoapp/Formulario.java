package com.example.novoapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Formulario extends AppCompatActivity {
    Button  btnSave, btnCancelar;
    Spinner spTipo;
    TextView tvCodigo;
    EditText etEndereco, etBairro, etCidade, etArea, etValor, etIptu;
    RadioButton rbAluguel, rbVenda;

    private FirebaseDatabase database;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);

        btnSave = (Button) findViewById(R.id.btnSalvar);
        btnCancelar = (Button) findViewById(R.id.btnVoltar);
        spTipo =  (Spinner) findViewById(R.id.spTipoImovel);
        tvCodigo =  (TextView) findViewById(R.id.tvCodigoImovel);
        etEndereco = (EditText) findViewById(R.id.etEndImovel);
        etBairro = (EditText) findViewById(R.id.etBairroImovel);
        etCidade = (EditText) findViewById(R.id.etCidadeImovel);
        etArea = (EditText) findViewById(R.id.etAreaImovel);
        etValor = (EditText) findViewById(R.id.etValorImovel);
        etIptu = (EditText)  findViewById(R.id.etIptuImovel);
        rbAluguel = (RadioButton)  findViewById(R.id.rbAlugarImovel);
        rbVenda = (RadioButton)  findViewById(R.id.rbVenderImovel);

        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.spTipoImovel, android.R.layout.simple_spinner_item);
        spTipo.setAdapter(adapter);

        //Botao salvar
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                salvar();
                Toast.makeText(Formulario.this, "Imóvel cadastrado com sucesso.",Toast.LENGTH_LONG).show();

            }
        });

        //Botao voltar
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Formulario.this, Inicio.class);
                startActivity( intent );
                finish();
            }
        });

    }

    private void salvar(){
        String tipo = spTipo.getSelectedItem().toString();
        String endereco = etEndereco.getText().toString();
        String cidade = etCidade.getText().toString();
        String bairro = etBairro.getText().toString();
        String area = etArea.getText().toString();
        String valor = etValor.getText().toString();
        String iptu = etIptu.getText().toString();
        String item = null;

        if(rbAluguel.isChecked()){
            item = getResources().getString(R.string.lblAlugar);
        } else if(rbVenda.isChecked()){
            item = rbVenda.getResources().getString(R.string.lblVender);
        } else{
            Toast.makeText(Formulario.this, "Selecione: "+"Alugar"+" ou "+"Vender",Toast.LENGTH_LONG).show();
        }

        if(!tipo.isEmpty() && !endereco.isEmpty() && !bairro.isEmpty() && !cidade.isEmpty()&& !area.isEmpty()&& !valor.isEmpty()
                && !iptu.isEmpty()&& !item.isEmpty()) {
            valor = valor.replace(",", ".");
            iptu = iptu.replace(",", ".");
            area = area.replace(",", ".");

            double preco = Double.valueOf(valor);
            double valorIptu = Double.valueOf(iptu);
            double tamanhoArea = Double.valueOf(area);

            Imovel imovel = new Imovel();

            imovel.tipo = tipo;
            imovel.endereco = endereco;
            imovel.bairro = bairro;
            imovel.cidade = cidade;
            imovel.tamanhoArea = tamanhoArea;
            imovel.item = item;
            imovel.preco = preco;
            imovel.valorIptu = valorIptu;

            database = FirebaseDatabase.getInstance();
            reference = database.getReference();
            reference.child("imoveis").push().setValue(imovel);

            finish();

        }
    }
}
