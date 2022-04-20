package br.com.alura.spring.data.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.Cargo;
import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.orm.UnidadeDeTrabalho;
import br.com.alura.spring.data.repository.CargoRepository;
import br.com.alura.spring.data.repository.FuncionarioRepository;
import br.com.alura.spring.data.repository.UnidadeDeTrabalhoRepository;

@Service
public class CrudFuncionarioService {

	private Boolean system = true;
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	private final CargoRepository cargoRepository;
	private final FuncionarioRepository funcionarioRepository;
	private final UnidadeDeTrabalhoRepository unidadeDeTrabalhoRepository;
	
	public CrudFuncionarioService(FuncionarioRepository funcionarioRepository, CargoRepository cargoRepository, UnidadeDeTrabalhoRepository unidadeDeTrabalhoRepository) {
		this.funcionarioRepository = funcionarioRepository;
		this.cargoRepository = cargoRepository;
		this.unidadeDeTrabalhoRepository = unidadeDeTrabalhoRepository;
	}
	
	public void inicial(Scanner scanner) {
		while(system) {
			System.out.println("Qual ação de funcionario deseja executar? ");
			System.out.println("0 -> Sair");
			System.out.println("1 -> Cadastrar");
			System.out.println("2 -> Atualizar");
			System.out.println("3 -> Visualizar");
			System.out.println("4 -> Deletar");
			
			int action = scanner.nextInt();
			
			switch (action) {
			case 1: 
				cadastrar(scanner);
				break;
			case 2:
				atualizar(scanner);
				break;
			case 3:
				visualizar(scanner);
				break;
			case 4:
				deletar(scanner);
				break;
			default:
				system = false;
				break;
			}
		}
	}
	
	private void cadastrar(Scanner scanner) {
		System.out.println("Digite o nome: ");
		String nome = scanner.next();
		
		System.out.println("Digite o CPF: ");
		String cpf = scanner.next();
		
		System.out.println("Digite o salario: ");
		BigDecimal salario = scanner.nextBigDecimal();
		
		System.out.println("Digite a data de contratação: ");
		String dataDeContratacao = scanner.next();
		
		System.out.println("Digite o cargoId: ");
		Long cargoId = scanner.nextLong();
		
		List<UnidadeDeTrabalho> unidades = unidade(scanner);
		
		Funcionario funcionario = new Funcionario();
		funcionario.setNome(nome);
		funcionario.setCpf(cpf);
		funcionario.setSalario(salario);
		funcionario.setDataContratacao(LocalDate.parse(dataDeContratacao, formatter));
		Optional<Cargo> cargo = cargoRepository.findById(cargoId);
		funcionario.setCargo(cargo.get());
		funcionario.setUnidadeTrabalhos(unidades);
		
		funcionarioRepository.save(funcionario);
		System.out.println("Funcionario cadastrado!");
	}
	
	private List<UnidadeDeTrabalho> unidade(Scanner scanner) {
		Boolean isTrue= true;
		List<UnidadeDeTrabalho> unidades = new ArrayList<>();
		
		while(isTrue) {
			System.out.println("Digite a unidadeId (Para sair digite 0)");
			Long unidadeId = scanner.nextLong();
			
			if(unidadeId != 0) {
				Optional<UnidadeDeTrabalho> unidade = unidadeDeTrabalhoRepository.findById(unidadeId);
				unidades.add(unidade.get());
			} else {
				isTrue = false;
			}
		}
		return unidades;
	}
	
	private void atualizar(Scanner scanner) {
		System.out.println("Informe o Id para atualizar");
		Long id = scanner.nextLong();
		
		System.out.println("Digite o nome: ");
		String nome = scanner.next();
		
		System.out.println("Digite o CPF: ");
		String cpf = scanner.next();
		
		System.out.println("Digite o salario: ");
		BigDecimal salario = scanner.nextBigDecimal();
		
		System.out.println("Digite a data de contratação: ");
		String dataDeContratacao = scanner.next();
		
		System.out.println("Digite o cargoId: ");
		Long cargoId = scanner.nextLong();
		
		Funcionario funcionario = new Funcionario();
		funcionario.setId(id);
		funcionario.setNome(nome);
		funcionario.setCpf(cpf);
		funcionario.setSalario(salario);
		funcionario.setDataContratacao(LocalDate.parse(dataDeContratacao, formatter));
		Optional<Cargo> cargo = cargoRepository.findById(cargoId);
		funcionario.setCargo(cargo.get());
		
		funcionarioRepository.save(funcionario);
		System.out.println("Funcionario Atualizado!");
		
	}
	
	private void visualizar(Scanner scanner) {
		System.out.println("Qual página você deseja visualizar? ");
		int pagina = scanner.nextInt();
		
//		Faz a pesquisa atraves de páginas determinada pelo usuário   // faz ordenação decrescente
		Pageable pageable = PageRequest.of(pagina, 5, Sort.by(Sort.Direction.ASC, "nome"));
		Page<Funcionario> funcionarios = funcionarioRepository.findAll(pageable);
		
		System.out.println(funcionarios);
		System.out.println("Página atual: " + funcionarios.getNumber());
		System.out.println("Quantidade de elementos: " + funcionarios.getTotalElements());
		
		funcionarios.forEach(funcionario -> System.out.println(funcionario));
	}
	
	private void deletar(Scanner scanner) {
		System.out.println("Informe o id a ser deletado: ");
		Long id = scanner.nextLong();
		
		cargoRepository.deleteById(id);
		System.out.println("Funcionario deletado!");
	}
	

}
