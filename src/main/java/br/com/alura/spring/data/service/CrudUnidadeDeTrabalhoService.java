package br.com.alura.spring.data.service;

import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.UnidadeDeTrabalho;
import br.com.alura.spring.data.repository.UnidadeDeTrabalhoRepository;

@Service
public class CrudUnidadeDeTrabalhoService {

	private Boolean system = true;
	private final UnidadeDeTrabalhoRepository unidadeDeTrabalhoRepository;
	
	public CrudUnidadeDeTrabalhoService(UnidadeDeTrabalhoRepository unidadeDeTrabalhoRepository) {
		this.unidadeDeTrabalhoRepository = unidadeDeTrabalhoRepository;
	}
	
	public void inicial(Scanner scanner) {
		while(system) {
			System.out.println("Qual ação de unidade de tralho deseja executar? ");
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
				visualizar();
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
		System.out.println("Descrição do unidade de trabalho: ");
		String nome = scanner.next();
		
		System.out.println("Qual o endereço da unidade de Trabalho: ");
		String endereco = scanner.next();
		
		UnidadeDeTrabalho unidadeDeTrabalho = new UnidadeDeTrabalho();
		unidadeDeTrabalho.setDescricao(nome);
		unidadeDeTrabalho.setEndereco(endereco);
		
		unidadeDeTrabalhoRepository.save(unidadeDeTrabalho);
		System.out.println("Unidade de Trabalho cadastrada");
	}
	
	private void atualizar(Scanner scanner) {
		System.out.println("Informe o Id para atualizar");
		Long id = scanner.nextLong();
		
		System.out.println("Descrição da unidade de Trabalho: ");
		String nome = scanner.next();
		
		System.out.println("Qual o endereço da unidade de Trabalho: ");
		String endereco = scanner.next();
		
		UnidadeDeTrabalho unidadeDeTrabalho = new UnidadeDeTrabalho();
		unidadeDeTrabalho.setId(id);
		unidadeDeTrabalho.setDescricao(nome);
		unidadeDeTrabalho.setEndereco(endereco);
		
		unidadeDeTrabalhoRepository.save(unidadeDeTrabalho);		
		System.out.println("Unidade de trabalho atualizada");
		
	}
	
	private void visualizar() {
		Iterable<UnidadeDeTrabalho> unidades = unidadeDeTrabalhoRepository.findAll();
		unidades.forEach(unidade -> System.out.println(unidade));
	}
	
	private void deletar(Scanner scanner) {
		System.out.println("Informe o id a ser deletado: ");
		Long id = scanner.nextLong();
		unidadeDeTrabalhoRepository.deleteById(id);
		System.out.println("Unidade de trabalho deletada");
	}
}
