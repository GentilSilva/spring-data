package br.com.alura.spring.data.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.orm.FuncionarioProjecao;

@Repository										// ativar paginação do repository				// querys para consultas dinâmicas como criteria
public interface FuncionarioRepository extends PagingAndSortingRepository<Funcionario, Long>, JpaSpecificationExecutor<Funcionario> {

//	Derived Query -> busca pelo nome, existem várias derived query olhar na documentação do spring data jpa
	List<Funcionario> findByNome(String nome);
	
//	busca utilizando derived query
//	List<Funcionario> findByNomeAndSalarioGraterThanAndDataContratacao(String nome, BigDecimal salario, LocalDate dataContratacao);
	
//	busca utilizando  query jpql
	@Query("SELECT f FROM Funcionario f WHERE f.nome = :nome AND f.salario >= :salario AND f.dataContratacao = :dataContratacao")
	List<Funcionario> findNomeSalarioMaiorDataContratacao(String nome, BigDecimal salario, LocalDate dataContratacao);
	
//	busca utilizando native query do sql --- utilizando os nomes da tabela do banco de dados
	@Query(value = "SELECT * FROM funcionarios f WHERE f.data_contratacao > :dataContratacao", 
			nativeQuery = true)
	List<Funcionario> findPorDataContratacaoMaior(LocalDate dataContratacao);
	
//	cria uma projeção com atributos que queremos apresentar, é necessário criar uma interface com os atributos
	@Query(value = "SELECT f.id, f.nome, f.salario FROM funcionarios f", nativeQuery = true)
	public List<FuncionarioProjecao> findFuncionarioSalario();
}
