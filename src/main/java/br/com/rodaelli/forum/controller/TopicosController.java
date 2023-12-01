package br.com.rodaelli.forum.controller;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.rodaelli.forum.controller.dto.DetalhesDoTopicoDto;
import br.com.rodaelli.forum.controller.dto.TopicoDto;
import br.com.rodaelli.forum.controller.form.AtualizacaoTopicoForm;
import br.com.rodaelli.forum.controller.form.TopicoForm;
import br.com.rodaelli.forum.modelo.Curso;
import br.com.rodaelli.forum.modelo.Topico;
import br.com.rodaelli.forum.repository.CursoRepository;
import br.com.rodaelli.forum.repository.TopicoRepository;

@RestController
@RequestMapping(value ="/topicos")
public class TopicosController {
	
	@Autowired
	private TopicoRepository topicoRepository;
	
	@Autowired
	private CursoRepository cursoRepository;
	
	//fazer um select * (busca Todos all)
	//@RequestMapping("/topicos")
	//public List<TopicoDto> lista() {
		//List<Topico> topicos = topicoRepository.findAll();
		//return TopicoDto.converter(topicos);
	
	//para receber por nome de curso - na url - http://localhost:8080/topicos?nomeCurso=Spring+Boot
	
	//listagem
	@GetMapping
	public List<TopicoDto> lista(String nomeCurso) {
		if(nomeCurso == null) {
			List<Topico> topicos = topicoRepository.findAll();
			return TopicoDto.converter(topicos);
		} else {
			List<Topico> topicos = topicoRepository.findByCursoNome(nomeCurso);
			return TopicoDto.converter(topicos);
		}
	}
	
	//cadastro com validacao
	@PostMapping
	@Transactional
	public ResponseEntity<TopicoDto> cadastrar(@RequestBody @Valid TopicoForm form, UriComponentsBuilder uriBuilder) {
		Topico topico = form.converter(cursoRepository);
		topicoRepository.save(topico);
		
		URI uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
		return ResponseEntity.created(uri).body(new TopicoDto(topico));
				
	}
	
	//detalhar somente 1 topico --> vai chegar requisicao, chegar id do topico, preciso carregar todos os topicos do bd e transformar ele em um dto, para devolver como resposta 
	//@GetMapping("/{id}")
	//public TopicoDto detalhar(@PathVariable Long id) {
		//Topico topico = topicoRepository.getOne(id);
		//return new TopicoDto(topico);
	//}
	
	//igual ao de cima
	@GetMapping("/{id}")
	public ResponseEntity<DetalhesDoTopicoDto> detalhar(@PathVariable Long id) {
		
		Optional<Topico> topico = topicoRepository.findById(id);
		if(topico.isPresent()) {
			return ResponseEntity.ok(new DetalhesDoTopicoDto(topico.get()));
		}
		
		return ResponseEntity.notFound().build();
		
	}
	
	//atualizar um topico
	@PutMapping("/{id}")
	@Transactional
	public ResponseEntity<TopicoDto> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizacaoTopicoForm form) {
		
		Optional<Topico> optional = topicoRepository.findById(id);
		if(optional.isPresent()) {
			Topico topico = form.atualizar(id, topicoRepository);
			return ResponseEntity.ok(new TopicoDto(topico));
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity<?> remover(@PathVariable Long id) {
		Optional<Topico> optional = topicoRepository.findById(id);
		if(optional.isPresent()) {
			topicoRepository.deleteById(id);
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.notFound().build();
	}
	
	
}













