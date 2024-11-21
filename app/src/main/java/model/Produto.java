package model;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Produto implements Serializable {
    private Long id;
    private String nome;
    private int quantidade;
    private double preco;
    private String imagem;


    public Produto(){}

    public Produto(String nome, int quantidade, double preco) {
            this.nome = nome;
            this.quantidade = quantidade;
            this.preco = preco;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }
}
