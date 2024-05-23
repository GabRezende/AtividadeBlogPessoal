package com.generation.blog.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity //classe vai se tornaruma entidade no banco de dados. entidade = tabela
@Table(name="tb_postagem")//nomeando a tabela no banco de dados 
public class Postagem {

	@Id //tornar o id uma primary key no databse 
	@GeneratedValue(strategy=GenerationType.IDENTITY) //define como a chave vai ser implementada //primary key em auto increment 
	private Long id;
	
	
	@NotBlank(message = "O atributo TÍTULO é obrigatório!") //validation 
	@Size(min = 5, max = 100, message = "O atibuto título deve ter no mínimo 5 caracteres e no máximo 100 caracteres!")
	private String titulo;
	
	@NotBlank(message = "O atibuto TEXTO é obrigatorio!")
	@Size(min = 5, max = 100, message = "O atibuto texto deve ter no mínimo 10 caracteres e no máximo 1000 caracteres!")
	private String texto;
	
	
	@UpdateTimestamp //salva a data e hora no database 
	private LocalDateTime data;

	
	@ManyToOne
	@JsonIgnoreProperties("postagem")
	private Tema tema;
	
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getTitulo() {
		return titulo;
	}


	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}


	public String getTexto() {
		return texto;
	}


	public void setTexto(String texto) {
		this.texto = texto;
	}


	public LocalDateTime getData() {
		return data;
	}


	public void setData(LocalDateTime data) {
		this.data = data;
	}


	public Tema getTema() {
		return tema;
	}


	public void setTema(Tema tema) {
		this.tema = tema;
	} 
	
	
}
