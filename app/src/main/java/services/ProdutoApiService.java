package services;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import model.Produto;

public class ProdutoApiService {
    private static final String BASE_URL = "http://10.0.2.2:8080/api/produtos/listarprodutos"; // URL da sua API

    // Definindo um listener para passar a lista de produtos de volta
    public interface ProdutoApiListener {
        void onProdutosReceived(List<Produto> produtos);
        void onError(String errorMessage);
    }

    public static void BuscarTodosProdutos(final ProdutoApiListener listener) {
        ExecutorService executor = Executors.newSingleThreadExecutor(); // Executor para rodar a tarefa

        // Tarefa em segundo plano
        executor.execute(new Runnable() {
            @Override
            public void run() {
                List<Produto> produtos = new ArrayList<>();
                try {
                    // Criar a URL
                    URL url = new URL(BASE_URL);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();

                    // Ler a resposta
                    BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    // Converter a resposta JSON para objetos Produto
                    JSONArray jsonArray = new JSONArray(response.toString());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Produto produto = new Produto();
                        produto.setImagem(jsonObject.getString("imagem"));
                        produto.setNome(jsonObject.getString("nome"));
                        produto.setQuantidade(jsonObject.getInt("quantidade"));
                        produto.setPreco(jsonObject.getDouble("preco"));
                        produtos.add(produto);
                    }
                } catch (Exception e) {
                    Log.e("ProdutoService", "Erro ao fazer a requisição", e);
                }

                // Usando o Handler para garantir que a atualização da UI seja feita na thread principal
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        if (produtos != null && !produtos.isEmpty()) {
                            listener.onProdutosReceived(produtos);
                        } else {
                            listener.onError("Nenhum produto encontrado ou erro ao carregar.");
                        }
                    }
                });
            }
        });
    }
}
