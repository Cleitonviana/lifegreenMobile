package com.lifegreen.testepim;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import model.Cliente;
import model.Endereco;
import services.ClienteApiService;

public class perfil extends AppCompatActivity {
    String cpf;
    Button buttonVoltarInicio, buttonSalvarAlteracoes, buttonExcluir, buttonBuscarCep;
    private ExecutorService executorService;
    EditText editTextNome, editTextTelefone, editTextEmail, editTextSenha, editTextCep, editTextLogradouro, editTextNumero, editTextComplemento, editTextLocalidade, editTextBairro, editTextEstado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_perfil);

        //recuperando cpf
        cpf = getIntent().getStringExtra("cpf");

        if (cpf != null) {
            buscarCliente(cpf); // Buscar dados do cliente
        } else {
            Toast.makeText(this, "Erro: CPF não encontrado!", Toast.LENGTH_SHORT).show();
        }


        editTextNome = findViewById(R.id.etNome);
        editTextTelefone = findViewById(R.id.etTelefone);
        editTextEmail = findViewById(R.id.etEmail);
        editTextSenha = findViewById(R.id.etSenha);
        editTextCep = findViewById(R.id.etCep);
        editTextLogradouro = findViewById(R.id.etLogradouro);
        editTextNumero = findViewById(R.id.etNumero);
        editTextComplemento = findViewById(R.id.etComplemento);
        editTextLocalidade = findViewById(R.id.etLocalidade);
        editTextBairro = findViewById(R.id.etBairro);
        editTextEstado = findViewById(R.id.etEstado);

        executorService = Executors.newSingleThreadExecutor();
        buttonBuscarCep = findViewById(R.id.btnBuscarEnd);
        buttonExcluir = findViewById(R.id.btnExcluir);
        buttonSalvarAlteracoes = findViewById(R.id.btnSalvarAlteracoes);
        buttonVoltarInicio = findViewById(R.id.btnVoltarInicio);

        buttonBuscarCep.setOnClickListener(v -> buscarEndereco());

        buttonExcluir.setOnClickListener(v -> deleteCliente());
        buttonVoltarInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(perfil.this, home.class);
                intent.putExtra("cpf", cpf);
                startActivity(intent);
            }
        });

        buttonSalvarAlteracoes.setOnClickListener(v -> updateCliente());


    }

    private void buscarCliente(String cpf) {
        new Thread(() -> {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                // URL do endpoint com o CPF como parâmetro
                URL url = new URL("http://10.0.2.2:8080/api/clientes/listarCliente?cpf=" + cpf);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                // Verifica o código de resposta da API
                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    // Ler a resposta
                    InputStream inputStream = connection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    // Converter a resposta JSON em um objeto Cliente
                    String jsonResponse = response.toString();
                    Cliente cliente = parseClienteFromJson(jsonResponse);

                    // Atualizar a UI com os dados do cliente
                    runOnUiThread(() -> atualizarCamposComCliente(cliente));
                } else {
                    runOnUiThread(() -> Toast.makeText(perfil.this, "Erro ao buscar cliente", Toast.LENGTH_SHORT).show());
                }
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(perfil.this, "Erro na conexão: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private static Cliente parseClienteFromJson(String jsonResponse) {
        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            Cliente cliente = new Cliente();
            cliente.setId(jsonObject.getLong("id"));
            cliente.setNome(jsonObject.getString("nome"));
            cliente.setCpf(jsonObject.getString("cpf"));
            cliente.setTelefone(jsonObject.getString("telefone"));
            cliente.setEmail(jsonObject.getString("email"));
            cliente.setSenha(jsonObject.getString("senha"));
            cliente.setCep(jsonObject.getString("cep"));
            cliente.setLogradouro(jsonObject.getString("logradouro"));
            cliente.setNumero(jsonObject.getString("numero"));
            cliente.setComplemento(jsonObject.getString("complemento"));
            cliente.setLocalidade(jsonObject.getString("localidade"));
            cliente.setBairro(jsonObject.getString("bairro"));
            cliente.setEstado(jsonObject.getString("estado"));
            if (cliente != null) {
                return cliente;
            } else {
                throw new RuntimeException("vazio");
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }


    private void atualizarCamposComCliente(Cliente cliente) {
        if (cliente != null) {

            editTextNome.setText(cliente.getNome());
            editTextTelefone.setText(cliente.getTelefone());
            editTextEmail.setText(cliente.getEmail());
            editTextSenha.setText(cliente.getSenha());
            editTextCep.setText(cliente.getCep());
            editTextLogradouro.setText(cliente.getLogradouro());
            editTextNumero.setText(cliente.getNumero());
            editTextComplemento.setText(cliente.getComplemento());
            editTextLocalidade.setText(cliente.getLocalidade());
            editTextBairro.setText(cliente.getBairro());
            editTextEstado.setText(cliente.getEstado());
        } else {
            Toast.makeText(this, "Cliente não encontrado", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateCliente() {
        Cliente cliente = new Cliente(); // Cria um novo cliente com dados atualizados.
        cliente.setNome(editTextNome.getText().toString());
        cliente.setCpf(cpf);
        cliente.setEmail(editTextEmail.getText().toString());
        cliente.setTelefone(editTextTelefone.getText().toString());
        cliente.setSenha(editTextSenha.getText().toString());
        cliente.setCep(editTextCep.getText().toString());
        cliente.setLogradouro(editTextLogradouro.getText().toString());
        cliente.setNumero(editTextNumero.getText().toString());
        cliente.setComplemento(editTextComplemento.getText().toString());
        cliente.setLocalidade(editTextLocalidade.getText().toString());
        cliente.setBairro(editTextBairro.getText().toString());
        cliente.setEstado(editTextEstado.getText().toString());


        new Thread(() -> {
            try {
                String response = ClienteApiService.updateCliente(cpf, cliente); // Chama o serviço para atualizar o cliente.
                runOnUiThread(() -> {
                    Toast.makeText(perfil.this, response, Toast.LENGTH_SHORT).show(); // Mensagem de sucesso.
                });
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(perfil.this, "Erro ao atualizar cliente", Toast.LENGTH_SHORT).show()); // Mensagem de erro.
            }
        }).start();
    }

    private void deleteCliente() {
        new Thread(() -> {
            try {
                String response = ClienteApiService.deleteCliente(cpf); // Chama o serviço para deletar o cliente.
                runOnUiThread(() -> {
                    Toast.makeText(perfil.this, response, Toast.LENGTH_SHORT).show(); // Mensagem de sucesso.
                    Intent intent = new Intent(perfil.this, MainActivity.class);
                    startActivity(intent);

                });
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(perfil.this, "Erro ao deletar cliente", Toast.LENGTH_SHORT).show()); // Mensagem de erro.
            }
        }).start();
    }

    private void buscarEndereco() {
        String cep = editTextCep.getText().toString();
        Future<?> future = executorService.submit(() -> {
            try {
                URL url = new URL("https://viacep.com.br/ws/" + cep + "/json/");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                reader.close();
                JSONObject jsonObject = new JSONObject(result.toString());
                runOnUiThread(() -> {
                    try {
                        editTextLogradouro.setText(jsonObject.getString("logradouro"));
                        editTextBairro.setText(jsonObject.getString("bairro"));
                        editTextLocalidade.setText(jsonObject.getString("localidade"));
                        editTextEstado.setText(jsonObject.getString("uf"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(perfil.this, "Erro ao buscar endereço", Toast.LENGTH_SHORT).show());
            }
        });
    }


}