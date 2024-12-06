package com.lifegreen.testepim;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import model.Produto;
import services.ProdutoApiService;
import util.Utils;

public class home extends AppCompatActivity {
    private RecyclerView recyclerViewProdutos;
    private EditText editTextPesquisar;
    private Button buttonFiltro, buttonComprar1, buttonComprar2, buttonComprar3, buttonComprar4;
    private ImageView iconPerfil, iconCarrinho;
    private ArrayList<Produto> carrinho = new ArrayList<>();

    private ImageView ivProduto1, ivProduto2, ivProduto3, ivProduto4;
    private TextView tvNome1, tvNome2, tvNome3, tvNome4;
    private TextView tvQuantidade1, tvQuantidade2, tvQuantidade3, tvQuantidade4;
    private TextView tvPreco1, tvPreco2, tvPreco3, tvPreco4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        // Inicializando componentes
        // recyclerViewProdutos = findViewById(R.id.recyclerViewProdutos);
        editTextPesquisar = findViewById(R.id.etSearch);
        buttonFiltro = findViewById(R.id.btnFilter);
        iconPerfil = findViewById(R.id.iconPerfil);
        iconCarrinho = findViewById(R.id.iconCarrinho);
        buttonComprar1 = findViewById(R.id.btnComprar1);
        buttonComprar2  =findViewById(R.id.btnComprar2);
        buttonComprar3 = findViewById(R.id.btnComprar3);
        buttonComprar4 = findViewById(R.id.btnComprar4);

        // Inicializar as Views
        ivProduto1 = findViewById(R.id.ivProductImage1);
        ivProduto2 = findViewById(R.id.ivProductImage2);
        ivProduto3 = findViewById(R.id.ivProductImage3);
        ivProduto4 = findViewById(R.id.ivProductImage4);

        tvNome1 = findViewById(R.id.tvProductName1);
        tvNome2 = findViewById(R.id.tvProductName2);
        tvNome3 = findViewById(R.id.tvProductName3);
        tvNome4 = findViewById(R.id.tvProductName4);

        tvQuantidade1 = findViewById(R.id.tvProductQuantity1);
        tvQuantidade2 = findViewById(R.id.tvProductQuantity2);
        tvQuantidade3 = findViewById(R.id.tvProductQuantity3);
        tvQuantidade4 = findViewById(R.id.tvProductQuantity4);

        tvPreco1 = findViewById(R.id.tvProductPrice1);
        tvPreco2 = findViewById(R.id.tvProductPrice2);
        tvPreco3 = findViewById(R.id.tvProductPrice3);
        tvPreco4 = findViewById(R.id.tvProductPrice4);
        //chamando a api
        buscarProdutos();
        //criando produtos
        Produto alface = new Produto("Alface",1, 3.00);
        Produto couve = new Produto("Couve", 1, 4.00);
        Produto cebolinha = new Produto("Cebolinha",1, 2.50);
        Produto cenoura = new Produto("Cenoura",1, 5.50);


        buttonComprar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Criar a Intent para abrir a CarrinhoActivity
                Intent intent = new Intent(home.this, carrinho.class);
                adicionarAoCarrinho(alface);
                Toast.makeText(home.this, "Produto adicionado ao carrinho",Toast.LENGTH_SHORT).show();

            }
        });

        buttonComprar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Criar a Intent para abrir a CarrinhoActivity
                Intent intent = new Intent(home.this, carrinho.class);
                adicionarAoCarrinho(couve);
                Toast.makeText(home.this, "Produto adicionado ao carrinho",Toast.LENGTH_SHORT).show();

            }
        });

        buttonComprar3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Criar a Intent para abrir a CarrinhoActivity
                Intent intent = new Intent(home.this, carrinho.class);
                adicionarAoCarrinho(cebolinha);

                Toast.makeText(home.this, "Produto adicionado ao carrinho",Toast.LENGTH_SHORT).show();

            }
        });
        buttonComprar4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Criar a Intent para abrir a CarrinhoActivity
                Intent intent = new Intent(home.this, carrinho.class);
                adicionarAoCarrinho(cenoura);
                Toast.makeText(home.this, "Produto adicionado ao carrinho",Toast.LENGTH_SHORT).show();

            }
        });




        String cpf = getIntent().getStringExtra("cpf");


        iconPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(home.this, perfil.class);
                intent.putExtra("cpf", cpf);
                startActivity(intent);

            }
        });

        iconCarrinho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(home.this, carrinho.class);
                intent.putExtra("carrinho", carrinho);// Envia o carrinho atual para a tela "Carrinho"
                intent.putExtra("cpf", cpf);
                startActivity(intent);
            }
        });
    }


    private void buscarProdutos() {
        ProdutoApiService.BuscarTodosProdutos(new ProdutoApiService.ProdutoApiListener() {
            @Override
            public void onProdutosReceived(List<Produto> produtos) {
                // Exibir os produtos na UI
                // Iterando sobre os produtos e carregando as imagens
                for (int i = 0; i < produtos.size(); i++) {
                    Produto produto = produtos.get(i);

                    String imagemBase64 = produto.getImagem();  // A string Base64 recebida da API
                    Bitmap imagemBitmap = Utils.base64ToBitmap(imagemBase64);  // Convertendo Base64 para Bitmap

                    if (imagemBitmap != null) {
                        // Usando Picasso para carregar o Bitmap
                        switch (i) {
                            case 0:
                                ivProduto1.setImageBitmap(imagemBitmap);
                               /* Glide.with(home.this)
                                        .load(R.drawable.alface)
                                        .into(ivProduto1);*/
                                break;
                            case 1:
                                ivProduto2.setImageBitmap(imagemBitmap);
                                /*Glide.with(home.this)
                                        .load(R.drawable.couve)
                                        .into(ivProduto2);*/
                                break;
                            case 2:
                                ivProduto3.setImageBitmap(imagemBitmap);
                               /* Glide.with(home.this)
                                        .load(R.drawable.cebolinha)
                                        .into(ivProduto3);*/
                                break;
                            case 3:
                                ivProduto4.setImageBitmap(imagemBitmap);
                                /*Glide.with(home.this)
                                        .load(R.drawable.cenoura)
                                        .into(ivProduto4);*/
                                break;
                        }
                    }

                    // Atualizar os outros campos (nome, quantidade, preço)
                    switch (i) {
                        case 0:
                            tvNome1.setText(produto.getNome());
                            tvQuantidade1.setText("Quantidade: " + produto.getQuantidade());
                            tvPreco1.setText(String.format("Preço: R$ %.2f", produto.getPreco()));
                            break;
                        case 1:
                            tvNome2.setText(produto.getNome());
                            tvQuantidade2.setText("Quantidade: " + produto.getQuantidade());
                            tvPreco2.setText(String.format("Preço: R$ %.2f", produto.getPreco()));
                            break;
                        case 2:
                            tvNome3.setText(produto.getNome());
                            tvQuantidade3.setText("Quantidade: " + produto.getQuantidade());
                            tvPreco3.setText(String.format("Preço: R$ %.2f", produto.getPreco()));
                            break;
                        case 3:
                            tvNome4.setText(produto.getNome());
                            tvQuantidade4.setText("Quantidade: " + produto.getQuantidade());
                            tvPreco4.setText(String.format("Preço: R$ %.2f", produto.getPreco()));
                            break;
                    }
                }
            }

            @Override
            public void onError(String errorMessage) {
                // Exibir mensagem de erro
                Toast.makeText(home.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void adicionarAoCarrinho(Produto produto) {
        // Verifica se o carrinho já contém o produto
        boolean produtoExistente = false;

        for (Produto p : carrinho) {
            if (p.getNome().equals(produto.getNome())) {
                // Produto já existe no carrinho, só atualizar a quantidade
                p.setQuantidade(p.getQuantidade() + 1);
                produtoExistente = true;
                break;
            }
        }

        // Se o produto não existia no carrinho, adiciona ele
        if (!produtoExistente) {
            carrinho.add(produto);

        }
    }


    }


