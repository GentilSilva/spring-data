package br.com.alura.spring.data.orm;

import java.math.BigDecimal;

public interface FuncionarioProjecao {

//	tipo e atributos que desejamos projetar
	Long getId();
	String getNome();
	BigDecimal getSalario();
	
}
