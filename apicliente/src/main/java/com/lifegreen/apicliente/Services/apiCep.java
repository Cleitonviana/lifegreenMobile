package com.lifegreen.apicliente.Services;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lifegreen.apicliente.Model.Endereco;

public class apiCep extends Endereco{

	public static Endereco buscarEnderecoCep(String cep) {
		Scanner sc = new Scanner(System.in);
		HttpResponse<String> response = null;
		Endereco ender = new Endereco();
		try {
			HttpClient client = HttpClient.newHttpClient();
			HttpRequest request = HttpRequest.newBuilder().uri(URI.create("Http://viacep.com.br/ws/" + cep + "/json/"))
					.build();
			try {
			 response = client.send(request, HttpResponse.BodyHandlers.ofString());
			}
			catch(InterruptedException e) {
				e.printStackTrace();
			}
			catch(Exception e) {
				System.out.println("Erro ao localizar o CEP");
				if (ender.getCep() == null || ender.getLogradouro() == null) {
					ender.setCep(cep);
					System.out.print("Logradouro: ");
					String logradouro = sc.nextLine();
					ender.setLogradouro(logradouro);
					System.out.print("Numero: ");
					String numero = sc.nextLine();
					ender.setNumero(numero);
					System.out.print("Complemento: ");
					String complemento = sc.nextLine();
					ender.setComplemento(complemento);
					System.out.print("Localidade: ");
					String localidade = sc.nextLine();
					ender.setLocalidade(localidade);
					System.out.print("bairro: ");
					String bairro = sc.nextLine();
					ender.setBairro(bairro);
					System.out.print("Estado: ");
					String estado = sc.nextLine();
					ender.setEstado(estado);
					return ender;
				}

			}
			ObjectMapper mapper = new ObjectMapper();
			int retApi = response.statusCode();
			
			if(retApi != 200) {
				if (ender.getCep() == null || ender.getLogradouro() == null) {
					System.out.println("Erro ao localizar o CEP");
					ender.setCep(cep);
					System.out.print("Logradouro: ");
					String logradouro = sc.nextLine();
					ender.setLogradouro(logradouro);
					System.out.print("Numero: ");
					String numero = sc.nextLine();
					ender.setNumero(numero);
					System.out.print("Complemento: ");
					String complemento = sc.nextLine();
					ender.setComplemento(complemento);
					System.out.print("Localidade: ");
					String localidade = sc.nextLine();
					ender.setLocalidade(localidade);
					System.out.print("bairro: ");
					String bairro = sc.nextLine();
					ender.setBairro(bairro);
					System.out.print("Estado: ");
					String estado = sc.nextLine();
					ender.setEstado(estado);
					return ender;
				}
				
			}
			ender = mapper.readValue(response.body(), Endereco.class);
			//definindo o numero e complemento do endereço
			System.out.print("Numero: ");
			String numero = sc.nextLine();
			ender.setNumero(numero);
			System.out.print("Complemento: ");
			String complemento = sc.nextLine();
			ender.setComplemento(complemento);

			return ender;
		} catch (JsonParseException e) {
			System.out.println("ERRO: CEP não localizado ");
			return ender;
		} catch (IOException e) {
			System.out.println(e.getMessage());
			throw new RuntimeException(e);
		}
		finally {
			sc.close();
		}

	}

}
