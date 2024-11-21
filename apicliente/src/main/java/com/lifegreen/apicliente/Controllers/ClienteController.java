package com.lifegreen.apicliente.Controllers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lifegreen.apicliente.Model.Cliente;
import com.lifegreen.apicliente.Model.Endereco;
import com.lifegreen.apicliente.Services.apiCep;
import com.lifegreen.apicliente.Services.validadorCpf;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
	public static String Connection_string = "jdbc:sqlserver://localhost:1433;databaseName=LifeGreen;user=sa;password=134679;trustServerCertificate=true";
	
	@GetMapping("/listar")
	public ArrayList<Cliente> listar()  {
		ArrayList<Cliente> clientes = new ArrayList<>();
		try(Connection conexao = DriverManager.getConnection(Connection_string);
			ResultSet resultSet = conexao.prepareStatement("SELECT * FROM dbo.cadastro").executeQuery()){
				
			
			while(resultSet.next()) {
				Cliente cliente = new Cliente(
				resultSet.getLong("id"),
				resultSet.getString("nome"),
				resultSet.getString("cpf"),
				resultSet.getString("telefone"),
				resultSet.getString("email"),
				resultSet.getString("senha")
				);
				System.out.println("Cliente encontrado: id=" + cliente.getId() + "Nome=" + cliente.getNome() + "cpf=" + cliente.getCpf() + "telefone=" + cliente.getTelefone() + "email=" + cliente.getEmail());
				clientes.add(cliente);
			}
			return clientes;
			
			}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println("Operação GET Concluida. Total de cliente encontrados: " + clientes.size());
		return clientes;
		
	}
	
	
	
	@PostMapping("/cadastrar")
	public String addCliente(@RequestBody Cliente cliente, Endereco endereco)  {
		 System.out.println("Iniciando operação POST para adicionar cliente.");
	        System.out.println("Parâmetros recebidos: Nome=" + cliente.getNome() + ", cpf=" + cliente.getCpf() + ", telefone=" + cliente.getTelefone() +", Email=" + cliente.getEmail());
	     boolean cpf =  validadorCpf.isvalidCPF(cliente.getCpf());
	     apiCep.buscarEnderecoCep(endereco.getCep());
	     cliente.setEndereco(endereco);
	     if(cpf == true) {
	    	 try(Connection conexao = DriverManager.getConnection(Connection_string);
	    	PreparedStatement preparedstatement = conexao.prepareStatement("INSERT INTO dbo.cadastro (nome, cpf, telefone, email, senha, cep, logradouro, numero, complemento, localidade, bairro, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")){
	    		 preparedstatement.setString(1, cliente.getNome());
	    		 preparedstatement.setString(2, cliente.getCpf());
	    		 preparedstatement.setString(3, cliente.getTelefone());
	    		 preparedstatement.setString(4, cliente.getEmail());
	    		 preparedstatement.setString(5, cliente.getSenha());
	    		 preparedstatement.setString(6, cliente.getEndereco().getCep());
	    		 preparedstatement.setString(7, cliente.getEndereco().getLogradouro());
	    		 preparedstatement.setString(8, cliente.getEndereco().getNumero());
	    		 preparedstatement.setString(9, cliente.getEndereco().getComplemento());
	    		 preparedstatement.setString(10, cliente.getEndereco().getLocalidade());
	    		 preparedstatement.setString(11, cliente.getEndereco().getBairro());
	    		 preparedstatement.setString(12, cliente.getEndereco().getEstado());
	    	   int rowsaffected = preparedstatement.executeUpdate();
	    	   
	    	   System.out.println("Operação POST concluida. linhas afetadas: " + rowsaffected);
	    	   return "Cliente inserido com sucesso";
	    	 }
	    	 catch(SQLException e) {
	    		 e.printStackTrace();
	    		 return "ERRO ao inserir Cliente " + e.getMessage();
	    	 }
	    	 
	     }
	     else {
	    	return "CPF Inválido!";
	    	
	     }
	     
	}
	
	@GetMapping("/login")
	public boolean Login(String cpf, String senha) {
		
		try(Connection conexao = DriverManager.getConnection(Connection_string);
				PreparedStatement stmt = conexao.prepareStatement("SELECT * FROM dbo.cadastro WHERE cpf = ? AND senha = ?")){
			
			stmt.setString(1, cpf);
			stmt.setString(2, senha);
			
			try(ResultSet rs = stmt.executeQuery()){
				if(rs.next()) {
					System.out.println("Login realizado com sucesso");
					return true;
				}
				else {
					System.out.println("Usuário não encontrado ou dados incorretos!");
					return false;
				}
			}
			
		}
		catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Erro ao se conectar no banco de dados " + e.getMessage());
			return false;
		}
		
		
	}

}
