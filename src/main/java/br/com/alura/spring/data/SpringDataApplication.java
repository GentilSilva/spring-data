package br.com.alura.spring.data;

import java.util.Scanner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.alura.spring.data.service.CrudCargoService;
import br.com.alura.spring.data.service.CrudFuncionarioService;
import br.com.alura.spring.data.service.CrudUnidadeDeTrabalhoService;
import br.com.alura.spring.data.service.RelatorioFuncionarioDinamico;
import br.com.alura.spring.data.service.RelatorioService;

// Essa anotação varre toda a aplicação e verifica onde tem anotações para poder rodar
@SpringBootApplication
public class SpringDataApplication implements CommandLineRunner{
	
	private Boolean system = true;

	private final CrudCargoService cargoService;
	private final CrudFuncionarioService funcionarioService;
	private final CrudUnidadeDeTrabalhoService unidadeDeTrabalhoService;
	private final RelatorioService relatorioService;
	private final RelatorioFuncionarioDinamico relatorioFuncionarioDinamico;
	
	public SpringDataApplication(CrudCargoService cargoService, CrudFuncionarioService funcionarioService, 
			CrudUnidadeDeTrabalhoService unidadeDeTrabalhoService, RelatorioService relatorioService,
			RelatorioFuncionarioDinamico relatorioFuncionarioDinamico) {
		this.cargoService = cargoService;
		this.funcionarioService = funcionarioService;
		this.unidadeDeTrabalhoService = unidadeDeTrabalhoService;
		this.relatorioService = relatorioService;
		this.relatorioFuncionarioDinamico = relatorioFuncionarioDinamico;
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringDataApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Scanner scanner = new Scanner(System.in);
		
		while(system) {
			System.out.println("Qual ação você deseja executar");
			System.out.println("0 -> Sair");
			System.out.println("1 -> Cargo");
			System.out.println("2 -> Funcionario");
			System.out.println("3 -> Unidade De Trabalho");
			System.out.println("4 -> Relatório");
			System.out.println("5 -> Relatorio dinâmico de funcionarios");
			int action = scanner.nextInt();
			
			switch (action) {
			case 1:
				cargoService.inicial(scanner);
				break;
			case 2:
				funcionarioService.inicial(scanner);
				break;
			case 3:
				unidadeDeTrabalhoService.inicial(scanner);
				break;
			case 4:
				relatorioService.inicial(scanner);
				break;
			case 5:
				relatorioFuncionarioDinamico.inicial(scanner);
				break;
			default:
				System.out.println("Finalizando");
				system = false;
				break;
			} 
		}
	}

}
