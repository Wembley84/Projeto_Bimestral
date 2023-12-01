package br.com.rodaelli.forum.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.rodaelli.forum.modelo.Curso;
import br.com.rodaelli.forum.modelo.Topico;

public interface CursoRepository extends JpaRepository<Curso, Long> {

	Curso findByNome(String nome);

}
