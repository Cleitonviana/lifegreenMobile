package com.lifegreen.apicliente.Services;

public class validadorCpf {
	
	public static boolean isvalidCPF(String cpf) {

		// Verifica se o CPF tem 11 dígitos e é composto apenas por números
		if (cpf == null || cpf.length() != 11 || !cpf.matches("\\d+")) {
			return false;
		}

		// Cálculo do primeiro dígito verificador
		int soma = 0;
		for (int i = 0; i < 9; i++) {
			soma += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
		}
		int primeiroDigito = (soma * 10) % 11;
		if (primeiroDigito == 10)
			primeiroDigito = 0;

		// Verifica o primeiro dígito verificador
		if (primeiroDigito != Character.getNumericValue(cpf.charAt(9))) {
			return false;
		}

		// Cálculo do segundo dígito verificador
		soma = 0;
		for (int i = 0; i < 10; i++) {
			soma += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
		}
		int segundoDigito = (soma * 10) % 11;
		if (segundoDigito == 10)
			segundoDigito = 0;

		// Verifica o segundo dígito verificador
		return segundoDigito == Character.getNumericValue(cpf.charAt(10));
	}

}
