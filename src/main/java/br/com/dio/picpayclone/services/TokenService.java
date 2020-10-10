package br.com.dio.picpayclone.services;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import br.com.dio.picpayclone.modelo.Usuario;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {

	@Value("${picpayclone.jwt.expiration}")
	private String expiration;

	@Value("${picpayclone.jwt.secretKey}")
	private String secretKey;

	public String gerarToken(Authentication authenticate) {
		Usuario usuarioLogado = (Usuario) authenticate.getPrincipal();

		LocalDateTime hoje = LocalDateTime.now();
		LocalDateTime tempoExpiracao = hoje.plus(Long.parseLong(expiration), ChronoUnit.MILLIS);

		Date dataExpiracao = Date.from(tempoExpiracao.atZone(ZoneId.systemDefault()).toInstant());

		return Jwts.builder().setIssuer("API PicPay Clone").setSubject(usuarioLogado.getId().toString())
				.setIssuedAt(dataExpiracao).signWith(SignatureAlgorithm.HS256, secretKey).compact();
	}

	public boolean isTokenValido(String token) {
		try {
			Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Long getIdUsuario(String token) {
		Claims body = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
		return Long.parseLong(body.getSubject());
	}

}
