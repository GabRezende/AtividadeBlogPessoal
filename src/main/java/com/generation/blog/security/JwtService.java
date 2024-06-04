package com.generation.blog.security;

import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {

	public static final String SECRET = "248fbd34c10063db65b3358ef48445df5b372d7246fc96348064812ad8f5d2b0";
	
	private Key getSignKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	//
	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(getSignKey()).build()
				.parseClaimsJws(token).getBody();
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	//recupera os dados da parte sub do claim onde encontramos o email.
	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	//data que o token espira
	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	//valida se a data que o token espira está dentro da validade 
	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	//valida se o usuario que foi extraido esta dentro da useretails e se a está dentro da validade do token
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	//objetivo calcular o tempo de validade do token e formar o claim com as informações do token 
	private String createToken(Map<String, Object> claims, String userName) {
		return Jwts.builder()
					.setClaims(claims)
					.setSubject(userName)
					.setIssuedAt(new Date(System.currentTimeMillis()))
					.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
					.signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
	}

	//gerar o token puxando os cliams formandos nno metodo anterior 
	public String generateToken(String userName) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, userName);
	}

}
