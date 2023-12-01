package br.com.rodaelli.forum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.rodaelli.forum.modelo.Topico;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

	//comenta ou n
	List<Topico> findByCursoNome(String nomeCurso);
	
	//jeito mais complicado e manual
	//@Query("SELECT t FROM Topico t WHERE t.curso.nome = :nomeCurso")
	//List<Topico> carregarPorNomeDoCurso(@Param("nomeCurso") String nomeCurso);
	
	
}
