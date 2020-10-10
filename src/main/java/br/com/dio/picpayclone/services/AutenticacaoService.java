package br.com.dio.picpayclone.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.dio.picpayclone.constantes.MensagemValidacao;
import br.com.dio.picpayclone.modelo.Usuario;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AutenticacaoService implements UserDetailsService {

	private final UsuarioService usuarioService;

	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		Usuario usuario = usuarioService.consultarEntidade(login);

		if (!isUsuarioValido(usuario)) {
			throw new UsernameNotFoundException(MensagemValidacao.ERRO_USUARIO_SEM_PERMISSAO);
		}

		return usuario;
	}

	private boolean isUsuarioValido(Usuario usuario) {
		if (usuario != null && usuario.getPermissao() != null && usuario.getAtivo())
			return true;
		return false;
	}

}
