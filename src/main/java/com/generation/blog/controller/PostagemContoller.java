package com.generation.blog.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generation.blog.model.Postagem;
import com.generation.blog.repository.PostagemRepository;

import jakarta.validation.Valid;

@RestController  //Diz para o speing que iso é uma controller de acessos e métodos
@RequestMapping("/postagem")  //rota para chegar nessa classe insomnia
@CrossOrigin(origins = "*", allowedHeaders = "*")  //liberar o acesso a outras maquinas
public class PostagemContoller {

	@Autowired  //instancia a classe postagem repository
	private PostagemRepository postagemRepository;
	
	@GetMapping  //define o verbo http que atende esse método abaixo
	public ResponseEntity<List<Postagem>> getAll(){
		//reponse entity permite que o método retorne em formato de requisição http
		return ResponseEntity.ok(postagemRepository.findAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Postagem> getById(@PathVariable Long id){
		// findById = SELECT * FROM tb_postagens WHERE id = 1;
		
		return postagemRepository.findById(id)
				.map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
	}
	
	@GetMapping("/titulo/{titulo}")
	public ResponseEntity<List<Postagem>> GetByTitulo(@PathVariable String titulo) {
		return ResponseEntity.ok(postagemRepository.findAllByTituloContainingIgnoreCase(titulo));
		
		}
	
	//INSERT INTO tb_postagens (titulo, texto, data) VALUES ("Título", "Texto", "2024-12-31 14:05:01");
		@PostMapping
		public ResponseEntity<Postagem> post(@Valid @RequestBody Postagem postagem){
			return ResponseEntity.status(HttpStatus.CREATED)
			.body(postagemRepository.save(postagem));
		}
		
		@PutMapping
		public ResponseEntity<Postagem> put(@Valid @RequestBody Postagem postagem){
			return postagemRepository.findById(postagem.getId())
					.map(resposta-> ResponseEntity.status(HttpStatus.OK)
					.body(postagemRepository.save(postagem)))
					.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
		}
		
		@DeleteMapping("/{id}")
		public void delete(@PathVariable Long id) {
			Optional<Postagem> postagem = postagemRepository.findById(id);
			if(postagem.isEmpty())
				throw new ResponseStatusException(HttpStatus.NOT_FOUND);
			postagemRepository.deleteById(id);
		}
		}
		

