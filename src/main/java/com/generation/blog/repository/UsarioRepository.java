package com.generation.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.blog.model.Usuario;

public interface UsarioRepository extends JpaRepository<Usuario, Long> {

	public Optional<Usuario> findByUsuario(String Usuario);
}
