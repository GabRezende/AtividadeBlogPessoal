package com.generation.blog.security;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.generation.blog.model.Usuario;
import com.generation.blog.repository.UsarioRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private UsarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
	 Optional <Usuario> usuario = usuarioRepository.findByUsuario(username);
	 
	  if (usuario.isPresent()) {
		  return new UserDetailsImpl(usuario.get());
	  }else {
		  throw new ResponseStatusException(HttpStatus.FORBIDDEN);
	  }
	}
}
