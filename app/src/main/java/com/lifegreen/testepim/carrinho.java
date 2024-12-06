package com.lifegreen.testepim;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

import model.Produto;

public class carrinho extends AppCompatActivity {

    // Variáveis para os componentes do layout
    private ImageView imagemProduto1, imagemProduto2, imagemProduto3, imagemProduto4;
    private TextView nomeProduto1, nomeProduto2, nomeProduto3, nomeProduto4;
    private TextView quantidadeProduto1, quantidadeProduto2, quantidadeProduto3, quantidadeProduto4;
    private TextView precoProduto1, precoProduto2, precoProduto3, precoProduto4;
   private ImageView botaoRemover1, botaoRemover2, botaoRemover3, botaoRemover4;
    private Button botaoDecremento1,botaoIncremento1, botaoDecremento2, botaoIncremento2, botaoDecremento3, botaoIncremento3, botaoDecremento4, botaoIncremento4, buttonEditarPedido,buttonFinalizarPedido;
    private TextView totalPedido;
    private ArrayList<Produto> carr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrinho);

        // Inicializando os componentes do layout
        imagemProduto1 = findViewById(R.id.imagemProduto1);
        imagemProduto2 = findViewById(R.id.imagemProduto2);
        imagemProduto3 = findViewById(R.id.imagemProduto3);
        imagemProduto4 = findViewById(R.id.imagemProduto4);
        nomeProduto1 = findViewById(R.id.nomeProduto1);
        nomeProduto2 = findViewById(R.id.nomeProduto2);
        nomeProduto3 = findViewById(R.id.nomeProduto3);
        nomeProduto4 = findViewById(R.id.nomeProduto4);
        quantidadeProduto1 = findViewById(R.id.quantidadeProduto1);
        quantidadeProduto2 = findViewById(R.id.quantidadeProduto2);
        quantidadeProduto3 = findViewById(R.id.quantidadeProduto3);
        quantidadeProduto4 = findViewById(R.id.quantidadeProduto4);
        precoProduto1 = findViewById(R.id.precoProduto1);
        precoProduto2 = findViewById(R.id.precoProduto2);
        precoProduto3 = findViewById(R.id.precoProduto3);
        precoProduto4 = findViewById(R.id.precoProduto4);
        botaoRemover1 = findViewById(R.id.botaoRemoverProduto1);
        botaoRemover2 = findViewById(R.id.botaoRemoverProduto2);
        botaoRemover3 = findViewById(R.id.botaoRemoverProduto3);
        botaoRemover4 = findViewById(R.id.botaoRemoverProduto4);
        botaoDecremento1 = findViewById(R.id.botaoDecrementarProduto1);
        botaoDecremento1.setEnabled(false);
        botaoIncremento1 = findViewById(R.id.botaoIncrementarProduto1);
        botaoDecremento2 = findViewById(R.id.botaoDecrementarProduto2);
        botaoDecremento2.setEnabled(false);
        botaoIncremento2 = findViewById(R.id.botaoIncrementarProduto2);
        botaoDecremento3 = findViewById(R.id.botaoDecrementarProduto3);
        botaoDecremento3.setEnabled(false);
        botaoIncremento3 = findViewById(R.id.botaoIncrementarProduto3);
        botaoDecremento4 = findViewById(R.id.botaoDecrementarProduto4);
        botaoDecremento4.setEnabled(false);
        botaoIncremento4 = findViewById(R.id.botaoIncrementarProduto4);
        buttonEditarPedido = findViewById(R.id.botaoEditarPedido);
        buttonFinalizarPedido = findViewById(R.id.botaoFinalizarPedido);
        totalPedido = findViewById(R.id.totalPedido);

        // Obtendo os dados passados pela tela anterior (Home)
        Intent intent = getIntent();
        carr = (ArrayList<Produto>) intent.getSerializableExtra("carrinho");
        //botão editar pedido
        buttonEditarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        String cpf = getIntent().getStringExtra("cpf");

        buttonFinalizarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(carrinho.this, FinalizarPedido.class);
                intent1.putExtra("Carrinhofinal", carr);
                intent1.putExtra("cpf", cpf);
                intent1.putExtra("total" , CalcularTotal());
                startActivity(intent1);
            }
        });

        botaoRemover1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(Produto p : carr){
                    if(nomeProduto1.getText() == p.getNome()){
                        removerDoCarrinho(p, 1);
                        break;
                    }
                }

            }
        });
        botaoRemover2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(Produto p : carr){
                    if(nomeProduto2.getText() == p.getNome()){
                        removerDoCarrinho(p, 2);
                        break;
                    }
                }
            }
        });

        botaoRemover3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(Produto p : carr){
                    if(nomeProduto3.getText() == p.getNome()){
                        removerDoCarrinho(p, 3);
                        break;
                    }
                }
            }
        });

        botaoRemover4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               for(Produto p : carr){
                   if(nomeProduto4.getText() == p.getNome()){
                       removerDoCarrinho(p, 4);
                       break;
                   }
               }
            }
        });

        botaoDecremento1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Produto p : carr) {
                    if (nomeProduto1.getText() == p.getNome()) {
                        // Produto já existe no carrinho, só atualizar a quantidade
                        p.setQuantidade(p.getQuantidade() - 1);
                        quantidadeProduto1.setText(String.valueOf(p.getQuantidade()));
                        if(p.getQuantidade() == 1){
                            botaoDecremento1.setEnabled(false);
                        }
                        atualizarInterface();
                        break;
                    }
                }

                }

        });

        botaoIncremento1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Produto p : carr) {
                    if (nomeProduto1.getText() == p.getNome()) {
                        // Produto já existe no carrinho, só atualizar a quantidade
                        p.setQuantidade(p.getQuantidade() + 1);
                        quantidadeProduto1.setText(String.valueOf( p.getQuantidade()));
                        botaoDecremento1.setEnabled(true);
                        atualizarInterface();
                        break;
                    }
                }
            }
        });



        botaoDecremento2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Produto p : carr) {
                    if (nomeProduto2.getText() == p.getNome()) {
                        // Produto já existe no carrinho, só atualizar a quantidade
                        p.setQuantidade(p.getQuantidade() - 1);
                        quantidadeProduto2.setText(String.valueOf( p.getQuantidade()));
                        if(p.getQuantidade() == 1){
                            botaoDecremento2.setEnabled(false);
                        }
                        atualizarInterface();
                        break;
                    }
                }
            }

        });


        botaoIncremento2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Produto p : carr) {
                    if (nomeProduto2.getText() == p.getNome()) {
                        // Produto já existe no carrinho, só atualizar a quantidade
                        p.setQuantidade(p.getQuantidade() + 1);
                        quantidadeProduto2.setText(String.valueOf( p.getQuantidade()));
                        botaoDecremento2.setEnabled(true);
                        atualizarInterface();
                        break;
                    }
                }
            }
        });


        botaoDecremento3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(Produto p : carr){
                    if (nomeProduto3.getText() ==  p.getNome()) {
                        p.setQuantidade(p.getQuantidade() - 1);
                        quantidadeProduto3.setText(String.valueOf(p.getQuantidade()));
                        if(p.getQuantidade() == 1){
                            botaoDecremento3.setEnabled(false);
                        }
                        atualizarInterface();
                        break;
                    }
                }
            }

        });

        botaoIncremento3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Produto p : carr){
                    if (nomeProduto3.getText() ==  p.getNome()){
                        p.setQuantidade(p.getQuantidade() + 1);
                        quantidadeProduto3.setText(String.valueOf(p.getQuantidade()));
                        botaoDecremento3.setEnabled(true);
                        atualizarInterface();
                        break;
                    }

                }
            }
        });

        botaoDecremento4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(Produto p : carr){
                    if (nomeProduto4.getText() == p.getNome()) {
                        p.setQuantidade(p.getQuantidade() - 1);
                        quantidadeProduto4.setText(String.valueOf( p.getQuantidade()));
                        if(p.getQuantidade() == 1){
                            botaoDecremento4.setEnabled(false);
                        }
                        atualizarInterface();
                        break;
                    }
                }
            }

        });

        botaoIncremento4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (Produto p : carr){
                    if (nomeProduto4.getText() == p.getNome()){
                        p.setQuantidade(p.getQuantidade() + 1);
                        quantidadeProduto4.setText(String.valueOf( p.getQuantidade()));
                        botaoDecremento4.setEnabled(true);
                        atualizarInterface();
                        break;
                    }
                }
            }
        });


        totalPedido.setText(String.format("Total: R$%.2f" ,CalcularTotal()));



        preencherCarrinho();
    }

    private void preencherCarrinho() {
        int index = 0;

        // Percorre o ArrayList de produtos
        for (Produto produto : carr) {
            switch (index) {
                case 0:
                    nomeProduto1.setText(produto.getNome());
                    quantidadeProduto1.setText(String.valueOf(produto.getQuantidade()));
                    precoProduto1.setText(String.format("Preço: R$ %.2f", produto.getPreco()));
                    DefinirImagens(produto, imagemProduto1);
                    break;
                case 1:
                    nomeProduto2.setText(produto.getNome());
                    quantidadeProduto2.setText(String.valueOf(produto.getQuantidade()));
                    precoProduto2.setText(String.format("Preço: R$ %.2f", produto.getPreco()));
                    DefinirImagens(produto,imagemProduto2);
                    break;
                case 2:
                    nomeProduto3.setText(produto.getNome());
                    quantidadeProduto3.setText(String.valueOf(produto.getQuantidade()));
                    precoProduto3.setText(String.format("Preço: R$ %.2f", produto.getPreco()));
                    DefinirImagens(produto,imagemProduto3);
                    break;
                case 3:
                    nomeProduto4.setText(produto.getNome());
                    quantidadeProduto4.setText(String.valueOf(produto.getQuantidade()));
                    precoProduto4.setText(String.format("Preço: R$ %.2f", produto.getPreco()));
                    DefinirImagens(produto,imagemProduto4);
                    break;
            }
            index++;
        }
    }
    private void DefinirImagens(Produto produto, ImageView imageView){
        if(produto.getNome().equals( "Alface")){
            Glide.with(carrinho.this)
                    .load(R.drawable.alface)
                    .into(imageView);
        }
        else if (produto.getNome().equals("Couve")){
            Glide.with(carrinho.this)
                    .load(R.drawable.couve)
                    .into(imageView);
        } else if (produto.getNome().equals("Cebolinha")) {
            Glide.with(carrinho.this)
                    .load(R.drawable.cebolinha)
                    .into(imageView);
        }
        else if (produto.getNome().equals("Cenoura")){
            Glide.with(carrinho.this)
                    .load(R.drawable.cenoura)
                    .into(imageView);
        }
        else {
            Toast.makeText(carrinho.this, "produto nao localizado", Toast.LENGTH_SHORT).show();
        }

    }

    private double CalcularTotal(){
        double soma = 0;

        for(Produto p : carr){
            soma +=p.getQuantidade() * p.getPreco();
        }
        return soma;
    }


    private void removerDoCarrinho(Produto produto, int slot) {
        for (int i = 0; i < carr.size(); i++) {
            Produto p = carr.get(i);
            if (p.getNome().equalsIgnoreCase(produto.getNome())) {
                if (p.getQuantidade() >= 1) {
                     if (p.getNome().equals("Alface")) {
                         carr.remove(i);
                         resetarSlot(slot);
                     } else if (p.getNome().equals("Couve")) {
                        carr.remove(i);
                        resetarSlot(slot);
                    } else if (p.getNome().equals("Cebolinha")) {
                        carr.remove(i);
                        resetarSlot(slot);
                    } else if (p.getNome().equals("Cenoura")) {
                        carr.remove(i);
                        resetarSlot(slot);
                    }
                }
                break;
            }
        }
        atualizarInterface();
    }

    private void resetarSlot(int slot) {
        switch (slot) {
            case 1:
                nomeProduto1.setText("-");
                quantidadeProduto1.setText("-");
                precoProduto1.setText("-");
                imagemProduto1.setImageResource(0); // Remove a imagem
                break;
            case 2:
                nomeProduto2.setText("-");
                quantidadeProduto2.setText("-");
                precoProduto2.setText("-");
                imagemProduto2.setImageResource(0); // Remove a imagem
                break;
            case 3:
                nomeProduto3.setText("-");
                quantidadeProduto3.setText("-");
                precoProduto3.setText("-");
                imagemProduto3.setImageResource(0); // Remove a imagem
                break;
            case 4:
                nomeProduto4.setText("-");
                quantidadeProduto4.setText("-");
                precoProduto4.setText("-");
                imagemProduto4.setImageResource(0); // Remove a imagem
                break;
        }
    }

    private void atualizarInterface() {
        // Aqui você pode atualizar o total do pedido ou outras informações necessárias.
        double total = 0.0;
        for (Produto p : carr) {
            total += p.getPreco() * p.getQuantidade();
        }
        totalPedido.setText(String.format("Total: R$ %.2f", total));
    }



}