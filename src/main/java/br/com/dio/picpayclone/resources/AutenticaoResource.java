package br.com.dio.picpayclone.resources;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.dio.picpayclone.dto.LoginDTO;
import br.com.dio.picpayclone.dto.TokenDTO;
import br.com.dio.picpayclone.services.TokenService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/autenticacao")
@RequiredArgsConstructor
public class AutenticaoResource extends ResourceBase<TokenDTO> {

	private final AuthenticationManager authenticationManager;
	private final TokenService tokenService;

	public ResponseEntity<TokenDTO> autenticar(LoginDTO login) {
		UsernamePasswordAuthenticationToken authentication = login.converter();
	
		try {
			Authentication authenticate = authenticationManager.authenticate(authentication);
			String token = tokenService.gerarToken(authenticate);
			return responderSucessoComRecurso(new TokenDTO(token, "Bearer"));
		} catch (AuthenticationException e) {
			return super.responderRequisicaoMalSucedida();
		}
	}
}
