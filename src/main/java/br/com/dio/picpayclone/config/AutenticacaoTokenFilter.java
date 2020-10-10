package br.com.dio.picpayclone.config;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.dio.picpayclone.modelo.Usuario;
import br.com.dio.picpayclone.repositories.UsuarioRepository;
import br.com.dio.picpayclone.services.TokenService;

public class AutenticacaoTokenFilter extends OncePerRequestFilter {

	private final TokenService tokenService;
	private final UsuarioRepository usuarioRepository;

	public AutenticacaoTokenFilter(TokenService tokenService, UsuarioRepository usuarioRepository) {
		this.tokenService = tokenService;
		this.usuarioRepository = usuarioRepository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = recuperarToken(request);
		boolean isTokenValido = tokenService.isTokenValido(token);
		
		if (isTokenValido) {
			autenticarUsuario(token);
		}
		
		filterChain.doFilter(request, response);
	}

	private void autenticarUsuario(String token) {
		Long idUsuario = tokenService.getIdUsuario(token);
		Optional<Usuario> usuarioOptional = usuarioRepository.findById(idUsuario);
		
		if (usuarioOptional.isPresent()) {
			Usuario usuario = usuarioOptional.get();
			Authentication autenticacao = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(autenticacao);
		}
	}

	private String recuperarToken(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		
		if (token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
			return null;
		}
		
		return token.substring(7);
	}

}
