package com.lifegreen.testepim;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import model.Cliente;
import services.ClienteApiService;

public class MainActivity extends AppCompatActivity {
public static final String TAG ="NetworkCheck";
private EditText editTextCpf, editTextSenha;
private Button  buttonLogin, buttonCadastrar, buttonEsqueciSenha;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

                editTextCpf = findViewById(R.id.txtCpf);
                editTextSenha = findViewById(R.id.txtSenha);
                //definindo os botões pelo id
                buttonLogin = findViewById(R.id.btnLogin);
                buttonCadastrar = findViewById(R.id.btnRealizarCadastro);
                buttonEsqueciSenha = findViewById(R.id.btnEsqueciSenha);



                //botão login
                buttonLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String cpf = editTextCpf.getText().toString();
                        String senha = editTextSenha.getText().toString();

                        if (cpf.isEmpty() || senha.isEmpty()) {
                            Toast.makeText(MainActivity.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        // Fazer login na API
                        new Thread(() -> {
                            try {
                                URL url = new URL("http://10.0.2.2:8080/api/clientes/login");
                                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                conn.setRequestMethod("POST");
                                conn.setDoOutput(true);
                                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                                String postData = "cpf=" + cpf + "&senha=" + senha;

                                OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
                                writer.write(postData);
                                writer.flush();

                                int responseCode = conn.getResponseCode();
                                if (responseCode == HttpURLConnection.HTTP_OK) {
                                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                                    StringBuilder response = new StringBuilder();
                                    String line;
                                    while ((line = reader.readLine()) != null) {
                                        response.append(line);
                                    }
                                    runOnUiThread(() -> {
                                        Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(MainActivity.this, home.class);
                                        intent.putExtra("cpf",cpf);
                                        startActivity(intent);
                                        finish();
                                    });
                                } else {
                                    runOnUiThread(() -> Toast.makeText(MainActivity.this, "CPF ou senha inválidos!", Toast.LENGTH_SHORT).show());
                                }
                            } catch (Exception e) {
                                runOnUiThread(() -> Toast.makeText(MainActivity.this, "Erro ao conectar com a API", Toast.LENGTH_SHORT).show());
                            }
                        }).start();
                    }
                });
                //botão cadastrar
                buttonCadastrar.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        Intent intent = new Intent(MainActivity.this, Cadastro.class);
                        startActivity(intent);
                    }
                });
                //botão esqueci minha senha
                buttonEsqueciSenha.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent =  new Intent(MainActivity.this, RecuperacaoSenha.class);
                        startActivity(intent);
                    }
                });
    }

}