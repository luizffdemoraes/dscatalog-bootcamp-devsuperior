package com.devsuperior.dscatalog.services.exceptions;

public class ResourceNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	/*
	 * Ao definir uma exceção podemos herdar de RuntimeException ou Exception. Caso
	 * herde de Exception ela deverá ser obrigatoriamente tratada o compilador não
	 * vai deixar vc não tratar com try catch ou não propagar para o metodo. Exceção
	 * flexivel caso trate ou não devemos herdar de RuntimeException.
	 * 
	 * Quando fazemos um construtor e queremos repassar um argumento para o
	 * construtor da super classe usamos o super(msg); conforme abaixo
	 */

	public ResourceNotFoundException(String msg) {
		super(msg);
	}

}
