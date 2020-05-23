package com.example.novoapp;

import androidx.annotation.NonNull;

public class Imovel {
    public String endereco, bairro, tipo, cidade, item;
    public double preco, valorIptu, tamanhoArea;

    @NonNull
    @Override
    public String toString() {
        return  tipo + "Rua: "+ endereco + ", Bairro: " + bairro +", Cidade: " + cidade + " - Area: "+tamanhoArea +
                " - Valor: " + preco + " IPTU " + valorIptu;
    }
}
