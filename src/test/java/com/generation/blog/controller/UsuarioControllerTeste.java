package com.generation.blog.controller;
import org.junit.jupiter.api.BeforeAll; 
import org.junit.jupiter.api.DisplayName; 
import org.junit.jupiter.api.Test; 
import org.junit.jupiter.api.TestInstance; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.boot.test.context.SpringBootTest; 
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment; 
import org.springframework.boot.test.web.client.TestRestTemplate; 
import org.springframework.http.HttpEntity; 
import org.springframework.http.HttpMethod; 
import org.springframework.http.HttpStatus; 
import org.springframework.http.ResponseEntity; 
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import com.generation.blog.model.Usuario; 
import com.generation.blog.repository.UsarioRepository; 
import com.generation.blog.service.UsuarioService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT) //roda na porta aleatoria 
@TestInstance(TestInstance.Lifecycle.PER_CLASS) //testar toda a classe 
public class UsuarioControllerTeste { 
	
	@Autowired 
	private UsarioRepository usuarioRepository; 

	@Autowired 
	private UsuarioService usuarioService; 

	@Autowired 
	private TestRestTemplate testeRestTemplate; 


	//apaga todos os usuarios pré existentes e cria um novo  

	@BeforeAll 
	void start () { 
		usuarioRepository.deleteAll();
		usuarioService.cadastrarUsuario(new Usuario(0L, "root", "root@root.com", "rootroot", "")); 
	} 

	//criação do primeiro teste 

	@Test 
	@DisplayName("Deve criar um novo usuario") 
	public void deveCriarUmNovoUsuario() { 
		HttpEntity<Usuario> corpoRequisição = new HttpEntity<Usuario>( 
				new Usuario(0L, "Thiago", "Thiago@gmail.com", "123456789", "") 
				);

		ResponseEntity<Usuario> corpoResposta = testeRestTemplate.exchange( 
				"/usuarios/cadastrar", HttpMethod.POST, corpoRequisição, Usuario.class 
				); 
		assertEquals(HttpStatus.CREATED, corpoResposta.getStatusCode()); 

	}  
		@Test
		@DisplayName("Não deve permitir duplicação do Usuário")
		public void naoDeveDuplicarUsuario() {

		    usuarioService.cadastrarUsuario(new Usuario(0L,
		        "Maria da Silva", "maria_silva@email.com.br", "13465278", ""));

		    HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(new Usuario(0L,
		        "Maria da Silva", "maria_silva@email.com.br", "13465278", ""));

		    ResponseEntity<Usuario> corpoResposta = testeRestTemplate
		        .exchange("/usuarios/cadastrar", HttpMethod.POST, corpoRequisicao, Usuario.class);

		    assertEquals(HttpStatus.BAD_REQUEST, corpoResposta.getStatusCode());
		}
		
			@Test
			@DisplayName("Atualizar um Usuário")
			public void deveAtualizarUmUsuario() {
			    Optional<Usuario> usuarioCadastrado = usuarioService.cadastrarUsuario(new Usuario(0L,
			        "Juliana Andrews", "juliana_andrews@email.com.br", "juliana123", ""));

			    Usuario usuarioUpdate = new Usuario(usuarioCadastrado.get().getId(),
			        "Juliana Andrews Ramos", "juliana_ramos@email.com.br", "juliana123", "");

			    HttpEntity<Usuario> corpoRequisicao = new HttpEntity<Usuario>(usuarioUpdate);

			    ResponseEntity<Usuario> corpoResposta = testeRestTemplate
			        .withBasicAuth("root@root.com", "rootroot")
			        .exchange("/usuarios/atualizar", HttpMethod.PUT, corpoRequisicao, Usuario.class);

			    assertEquals(HttpStatus.OK, corpoResposta.getStatusCode());
			}
	

				@Test
				@DisplayName("Listar todos os Usuários")
				public void deveMostrarTodosUsuarios() {

				    usuarioService.cadastrarUsuario(new Usuario(0L,
				        "Sabrina Sanches", "sabrina_sanches@email.com.br", "sabrina123", ""));

				    usuarioService.cadastrarUsuario(new Usuario(0L,
				        "Ricardo Marques", "ricardo_marques@email.com.br", "ricardo123", ""));

				    ResponseEntity<String> resposta = testeRestTemplate
				        .withBasicAuth("root@root.com", "rootroot")
				        .exchange("/usuarios/all", HttpMethod.GET, null, String.class);

				    assertEquals(HttpStatus.OK, resposta.getStatusCode());
				}
			
} 