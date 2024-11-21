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
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import model.Cliente;
import model.Endereco;
import services.ClienteApiService;

public class Cadastro extends AppCompatActivity {
    private EditText editTextNome,editTextCpf,editTextTelefone,editTextEmail,editTextSenha,editTextLogradouro,editTextCep,editTextNumero,editTextComplemento,editTextBairro,editTextLocalidade,editTextEstado;
    private Button buttonVoltarInicio, buttonBuscar, buttonCadastrar;
    private ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        //definindo os id
        editTextNome = findViewById(R.id.txtNome);
        editTextCpf = findViewById(R.id.txtCpf);
        editTextTelefone = findViewById(R.id.txtTelefone);
        editTextEmail = findViewById(R.id.txtEmail);
        editTextSenha = findViewById(R.id.txtSenha);
        editTextLogradouro = findViewById(R.id.txtLogradouro);
        editTextCep = findViewById(R.id.txtCep);
        editTextNumero = findViewById(R.id.txtNumero);
        editTextComplemento = findViewById(R.id.txtComplemento);
        editTextBairro = findViewById(R.id.txtBairro);
        editTextLocalidade = findViewById(R.id.txtLocalidade);
        editTextEstado = findViewById(R.id.txtEstado);

        executorService = Executors.newSingleThreadExecutor();
        buttonCadastrar = findViewById(R.id.btnCadastrar);
        buttonBuscar = findViewById(R.id.btnBuscarCep);
        buttonVoltarInicio = findViewById(R.id.btnVoltarInicio);

        buttonBuscar.setOnClickListener( v -> buscarEndereco());
        //buttonCadastrar.setOnClickListener(v -> addCliente());
        buttonCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCliente();
                Intent intent = new Intent(Cadastro.this, MainActivity.class);
                startActivity(intent);
            }
        });

        buttonVoltarInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Cadastro.this, MainActivity.class);
                startActivity(intent);
            }
        });
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
                runOnUiThread(() -> Toast.makeText(Cadastro.this, "Erro ao buscar endereço", Toast.LENGTH_SHORT).show());
            }
        });
    }

    private void addCliente() {
        Cliente cliente = new Cliente();// Cria um novo cliente
        cliente.setNome(editTextNome.getText().toString()); // Define o nome a partir do campo de texto.
        cliente.setCpf(editTextCpf.getText().toString());
        cliente.setTelefone(editTextTelefone.getText().toString());
        cliente.setEmail(editTextEmail.getText().toString()); // Define o email a partir do campo de texto.
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
                String response = ClienteApiService.addCliente(cliente); // Chama o serviço para adicionar o cliente.
                runOnUiThread(() -> {
                    Toast.makeText(Cadastro.this, response, Toast.LENGTH_SHORT).show(); // Mensagem de sucesso.
                    //loadClientes(); // Atualiza a lista de clientes.
                });
            } catch (Exception e) {
                e.printStackTrace();
                e.getMessage();
                runOnUiThread(() -> Toast.makeText(Cadastro.this, "Erro ao adicionar cliente", Toast.LENGTH_SHORT).show()); // Mensagem de erro.
            }
        }).start();
    }


}