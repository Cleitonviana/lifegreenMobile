package com.lifegreen.apicliente.Services;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Random;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class EmailService {
	 private static final String API_KEY = "06ea80cbb04329e15280b8826955dd1a-79295dd0-7957c859";
	    private static final String DOMAIN_NAME = "sandbox53cb0da3ab174341adbd4d5cbdc5fc52.mailgun.org";
	    private static final String FROM_EMAIL = "no-reply@" + DOMAIN_NAME;
	    private static final String Connection_string = "jdbc:sqlserver://localhost:1433;databaseName=LifeGreen;user=sa;password=134679;trustServerCertificate=true";
	    
	    
	    // Método para enviar o código de recuperação para o e-mail associado ao CPF
	    public static String enviarCodigoRecuperacao(String cpf) {
	        // Busca o e-mail no banco de dados usando o CPF
	        String email = buscarEmailPorCpf(cpf);
	        
	        if (email == null) {
	            System.out.println("E-mail não encontrado para o CPF fornecido.");
	            return null; // Retorna null se não encontrar o e-mail
	        }

	        String codigo = gerarCodigo();
	        String assunto = "Recuperação de Senha";
	        String mensagem = "Seu código de recuperação é: " + codigo;
	        
	        try (CloseableHttpClient client = HttpClients.createDefault()) {
	            HttpPost post = new HttpPost("https://api.mailgun.net/v3/" + DOMAIN_NAME + "/messages");
	            post.setHeader("Authorization", "Basic " + 
	                    Base64.getEncoder().encodeToString(("api:" + API_KEY).getBytes()));

	            StringEntity entity = new StringEntity("from=" + FROM_EMAIL 
	                    + "&to=" + email + "&subject=" + assunto + "&text=" + mensagem, "UTF-8");
	            post.setEntity(entity);
	            post.setHeader("Content-Type", "application/x-www-form-urlencoded");

	            try (CloseableHttpResponse response = client.execute(post)) {
	                String responseString = EntityUtils.toString(response.getEntity());
	                System.out.println("Resposta do Mailgun: " + responseString);
	            }

	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        
	        return codigo; // Retorna o código para salvar no sistema temporariamente
	    }

	    // Gera um código de 6 dígitos aleatório
	    private static String gerarCodigo() {
	        Random random = new Random();
	        int codigo = 100000 + random.nextInt(900000);
	        return String.valueOf(codigo);
	    }
	

	    
	    
	    // Método para buscar o e-mail pelo CPF
	    public static String buscarEmailPorCpf(String cpf) {
	        String email = null;
	        String query = "SELECT email FROM dbo.cadastro WHERE cpf = ?";

	        try (Connection conexao = DriverManager.getConnection(Connection_string);
	             PreparedStatement stmt = conexao.prepareStatement(query)) {
	            stmt.setString(1, cpf);
	            ResultSet rs = stmt.executeQuery();
	            
	            if (rs.next()) {
	                email = rs.getString("email");
	            }
	          
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return email;
	    }
	    

	    // Método para atualizar a senha
	    public static boolean atualizarSenha(String cpf, String novaSenha) {
	        String query = "UPDATE dbo.cadastro SET senha = ? WHERE cpf = ?";
	        try (Connection conn = DriverManager.getConnection(Connection_string);
	             PreparedStatement stmt = conn.prepareStatement(query)) {
	            stmt.setString(1, novaSenha);
	            stmt.setString(2, cpf);
	            int rowsAffected = stmt.executeUpdate();
	            return rowsAffected > 0;
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return false;
	    }
	}
