package com.example.novoapp;

import androidx.annotation.NonNull;

public class Imovel {
    public String id, endereco, bairro, tipo, cidade, item;
    public double preco, valorIptu, tamanhoArea;

    @NonNull
    @Override
    public String toString() {
        return  tipo + "- \n Rua: "+ endereco + ", Bairro: " + bairro +", Cidade: " + cidade + " \n - Área: "+tamanhoArea +
                " - Valor: " + preco + " IPTU " + valorIptu;
    }
}
