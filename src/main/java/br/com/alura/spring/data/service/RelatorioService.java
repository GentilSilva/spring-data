package br.com.alura.spring.data.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.orm.FuncionarioProjecao;
import br.com.alura.spring.data.repository.FuncionarioRepository;

@Service
public class RelatorioService {

	private Boolean system = true;
	
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	private final FuncionarioRepository funcionarioRepository;
	
	public RelatorioService(FuncionarioRepository funcionarioRepository) {
		this.funcionarioRepository = funcionarioRepository;
	}
	
	public void inicial(Scanner scanner) {
		while(system) {
			System.out.println("Qual ação de busca deseja executar? ");
			System.out.println("0 -> Sair");
			System.out.println("1 -> Buscar funcionario por nome");
			System.out.println("2 -> Buscar funcionario por nome, salario maior que, e data de contratação");
			System.out.println("3 -> Buscar funcionario por data de contratação");
			System.out.println("4 -> Buscar funcionario por salario");
			
			int action = scanner.nextInt();
			
			switch (action) {
			case 1: 
				buscaFuncionarioPorNome(scanner);
				break;
			case 2:
				buscaFuncionarioPorNomeSalarioMaiorDataContratacao(scanner);
				break;
			case 3:
				buscaFuncionarioPorDataContratacao(scanner);
				break;
			case 4:
				buscaFuncionarioSalario();
				break;
			default:
				system = false;
				break;
			}
		}
	}
	
	private void buscaFuncionarioPorNome(Scanner scanner) {
		System.out.println("Digite o nome que deseja pesquisar: ");
		String nome = scanner.next();
		
		List<Funcionario> lista = funcionarioRepository.findByNome(nome);
		lista.forEach(System.out::println);
	}
	
	private void buscaFuncionarioPorNomeSalarioMaiorDataContratacao(Scanner scanner) {
		System.out.println("Digite o nome que deseja pesquisar: ");
		String nome = scanner.next();
		
		System.out.println("Digite o valor mínimo do salario: ");
		BigDecimal salario = scanner.nextBigDecimal();
		
		System.out.println("Digite a data de contratação: ");
		String dataContratacao = scanner.next();
		LocalDate localDate = LocalDate.parse(dataContratacao, formatter);
		
		List<Funcionario> lista = funcionarioRepository.findNomeSalarioMaiorDataContratacao(nome, salario, localDate);
		lista.forEach(System.out::println);
	}
	
	private void buscaFuncionarioPorDataContratacao(Scanner scanner) {
		System.out.println("Digite a data de contratação: ");
		String dataContratacao = scanner.next();
		LocalDate localDate = LocalDate.parse(dataContratacao, formatter);
		
		List<Funcionario> lista = funcionarioRepository.findPorDataContratacaoMaior(localDate);
		lista.forEach(System.out::println);
	}
	
	private void buscaFuncionarioSalario() {
		List<FuncionarioProjecao> list = funcionarioRepository.findFuncionarioSalario();
		list.forEach(f -> System.out.println("[Funcionario: id: " + f.getId() + "]"
		+ " [Nome: " + f.getNome() + "] [salario: " + f.getSalario() + "]]"));
	}
}