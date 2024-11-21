package com.lifegreen.testepim;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import model.Produto;

public class FinalizarPedido extends AppCompatActivity {
    private ImageView imagemProd1, imagemProd2, imagemProd3, imagemProd4;
    private TextView nomeProd1, nomeProd2, nomeProd3, nomeProd4;
    private TextView quantidadeProd1, quantidadeProd2, quantidadeProd3, quantidadeProd4;
    private TextView precoProd1, precoProd2, precoProd3, precoProd4, totalPedido1;
    private Button buttonEditarPedido,buttonConfirmar;
    private ArrayList<Produto> pedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_finalizar_pedido);

        // Inicializando os componentes do layout
        imagemProd1 = findViewById(R.id.imagemprod1);
        imagemProd2 = findViewById(R.id.imagemprod2);
        imagemProd3 = findViewById(R.id.imagemprod3);
        imagemProd4 = findViewById(R.id.imagemprod4);
        nomeProd1 = findViewById(R.id.nomeprod1);
        nomeProd2 = findViewById(R.id.nomeprod2);
        nomeProd3 = findViewById(R.id.nomeprod3);
        nomeProd4 = findViewById(R.id.nomeprod4);
        quantidadeProd1 = findViewById(R.id.quantidadeprod1);
        quantidadeProd2 = findViewById(R.id.quantidadeprod2);
        quantidadeProd3 = findViewById(R.id.quantidadeprod3);
        quantidadeProd4 = findViewById(R.id.quantidadeprod4);
        precoProd1 = findViewById(R.id.precoprod1);
        precoProd2 = findViewById(R.id.precoprod2);
        precoProd3 = findViewById(R.id.precoprod3);
        precoProd4 = findViewById(R.id.precoprod4);
        buttonConfirmar = findViewById(R.id.botaoconfirmar);
        buttonEditarPedido = findViewById(R.id.botaoEditarPedido1);
        totalPedido1 = findViewById(R.id.totalPedido1);

        Intent intent = getIntent();
        pedido = (ArrayList<Produto>)intent.getSerializableExtra("Carrinhofinal");
        preencherCarrinho();
        totalPedido1.setText(String.format("Total: R$%.2f",CalcularTotal()));


        buttonEditarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(FinalizarPedido.this, carrinho.class);
                startActivity(intent1);
            }
        });
        String cpf = getIntent().getStringExtra("cpf");

        buttonConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(FinalizarPedido.this, home.class);
                intent2.putExtra("cpf", cpf);
                Toast.makeText(FinalizarPedido.this, "PEDIDO REALIZADO COM SUCESSO!",Toast.LENGTH_SHORT).show();

                startActivity(intent2);
            }
        });
    }


    private void preencherCarrinho() {
        int index = 0;

        // Percorre o ArrayList de produtos
        for (Produto produto : pedido) {
            switch (index) {
                case 0:
                    nomeProd1.setText(produto.getNome());
                    quantidadeProd1.setText(String.valueOf("Quantidade: " +produto.getQuantidade()));
                    precoProd1.setText(String.format("R$ %.2f", produto.getPreco()));
                    DefinirImagens(produto, imagemProd1);
                    break;
                case 1:
                    nomeProd2.setText(produto.getNome());
                    quantidadeProd2.setText(String.valueOf("Quantidade: " +produto.getQuantidade()));
                    precoProd2.setText(String.format("R$ %.2f", produto.getPreco()));
                    DefinirImagens(produto,imagemProd2);
                    break;
                case 2:
                    nomeProd3.setText(produto.getNome());
                    quantidadeProd3.setText(String.valueOf("Quantidade: " +produto.getQuantidade()));
                    precoProd3.setText(String.format("R$ %.2f", produto.getPreco()));
                    DefinirImagens(produto,imagemProd3);
                    break;
                case 3:
                    nomeProd4.setText(produto.getNome());
                    quantidadeProd4.setText(String.valueOf("Quantidade: " +produto.getQuantidade()));
                    precoProd4.setText(String.format("R$ %.2f", produto.getPreco()));
                    DefinirImagens(produto,imagemProd4);
                    break;
            }
            index++;
        }
    }
    private void DefinirImagens(Produto produto, ImageView imageView){
        if(produto.getNome().equals( "Alface")){
            Glide.with(FinalizarPedido.this)
                    .load(R.drawable.alface)
                    .into(imageView);
        }
        else if (produto.getNome().equals("Couve")){
            Glide.with(FinalizarPedido.this)
                    .load(R.drawable.couve)
                    .into(imageView);
        } else if (produto.getNome().equals("Cebolinha")) {
            Glide.with(FinalizarPedido.this)
                    .load(R.drawable.cebolinha)
                    .into(imageView);
        }
        else if (produto.getNome().equals("Cenoura")){
            Glide.with(FinalizarPedido.this)
                    .load(R.drawable.cenoura)
                    .into(imageView);
        }
        else {
            Toast.makeText(FinalizarPedido.this, "produto nao localizado", Toast.LENGTH_SHORT).show();
        }

    }

    private double CalcularTotal(){
        double soma = 0;

        for(Produto p : pedido){
            soma +=p.getQuantidade() * p.getPreco();
        }
        return soma;
    }
}