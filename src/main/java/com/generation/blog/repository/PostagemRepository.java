package com.generation.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.blog.model.Postagem;

public interface PostagemRepository extends JpaRepository<Postagem, Long> {

}
