package com.devsuperior.uri2990.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.devsuperior.uri2990.dto.EmpregadoDeptDTO;
import com.devsuperior.uri2990.entities.Empregado;
import com.devsuperior.uri2990.projections.EmpregadoDeptProjection;

public interface EmpregadoRepository extends JpaRepository<Empregado, Long> {

	@Query(nativeQuery = true, value = "select e.cpf, e.enome, d.dnome "
			+ "from empregados e "
			+ "inner join departamentos d on e.dnumero = d.dnumero "
			+ "where e.cpf not in ( "
			+ "	select e.cpf  "
			+ "	from empregados "
			+ "	inner join trabalha t on t.cpf_emp = e.cpf "
			+ ") "
			+ "order by e.enome")
	List<EmpregadoDeptProjection> busca1();
	
	@Query("select new com.devsuperior.uri2990.dto.EmpregadoDeptDTO(e.cpf, e.enome, e.departamento.dnome) "
			+ "from Empregado e "
			+ "where e.cpf not in ( "
			+ "	select e.cpf  "
			+ "	from Empregado e "
			+ "	inner join e.projetosOndeTrabalha "
			+ ") "
			+ "order by e.enome")
	List<EmpregadoDeptDTO> busca2();
	
	@Query(nativeQuery = true, value = "select e.cpf, e.enome, d.dnome "
			+ "from empregados e "
			+ "inner join departamentos d on e.dnumero = d.dnumero "
			+ "where e.cpf not in ( "
			+ "	select e.cpf  "
			+ "	from empregados "
			+ "	inner join trabalha t on t.cpf_emp = e.cpf "
			+ ") "
			+ "order by e.enome")
	List<EmpregadoDeptProjection> busca3();
}
