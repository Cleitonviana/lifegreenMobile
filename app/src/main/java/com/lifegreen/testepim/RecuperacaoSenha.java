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

import services.ClienteApiService;

public class RecuperacaoSenha extends AppCompatActivity {
    private EditText editTextCpf, editTextCodigo,editTextNovaSenha;
    private Button buttonVoltarInicio,buttonEnviarCodigo,buttonAlterarSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recuperacao_senha);

        editTextCpf = findViewById(R.id.txtCpf);
        editTextCodigo = findViewById(R.id.txtCodigo);
        editTextNovaSenha = findViewById(R.id.txtNovaSenha);
        buttonEnviarCodigo = findViewById(R.id.btnEnviarCodigo);
        buttonAlterarSenha = findViewById(R.id.btnAlterarSenha);
        buttonVoltarInicio = findViewById(R.id.btnVoltarInicio);

        // Ação do botão "Enviar Código"
        buttonEnviarCodigo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cpf = editTextCpf.getText().toString();

                if (cpf.isEmpty()) {
                    Toast.makeText(RecuperacaoSenha.this, "Por favor, insira seu CPF.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Envia o CPF para a API
                String resposta = ClienteApiService.iniciarRecuperacaoSenha(cpf);
                Toast.makeText(RecuperacaoSenha.this, resposta, Toast.LENGTH_SHORT).show();
            }
        });


        // Ação do botão "Alterar Senha"
        buttonAlterarSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cpf = editTextCpf.getText().toString();
                String codigo = editTextCodigo.getText().toString();
                String novaSenha = editTextNovaSenha.getText().toString();

                if (cpf.isEmpty() || codigo.isEmpty() || novaSenha.isEmpty()) {
                    Toast.makeText(RecuperacaoSenha.this, "Preencha todos os campos.", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Envia os dados para alterar a senha
                String resposta = ClienteApiService.alterarSenha(cpf, codigo, novaSenha);
                Toast.makeText(RecuperacaoSenha.this, resposta, Toast.LENGTH_SHORT).show();
            }
        });


        buttonVoltarInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecuperacaoSenha.this,MainActivity.class);
                startActivity(intent);
            }
        });


    }
}