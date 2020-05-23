package com.example.novoapp;

import androidx.annotation.NonNull;

public class Imovel {
    public String id, endereco, bairro, tipo, cidade, item;
    public double preco, valorIptu, tamanhoArea;

    @NonNull
    @Override
    public String toString() {
        return  tipo + "- Rua: "+ endereco + ", " + bairro +"\n - Cidade: " + cidade + " \n - Área: "+tamanhoArea +
                "\n - Valor: " + preco + "\n - IPTU: " + valorIptu;
    }
}
