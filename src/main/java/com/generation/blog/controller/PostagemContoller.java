package com.generation.blog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.blog.model.Postagem;
import com.generation.blog.repository.PostagemRepository;

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
}

