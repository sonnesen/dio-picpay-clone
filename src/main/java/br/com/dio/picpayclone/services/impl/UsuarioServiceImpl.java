package br.com.dio.picpayclone.services.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import br.com.dio.picpayclone.converters.UsuarioConversor;
import br.com.dio.picpayclone.dto.UsuarioDTO;
import br.com.dio.picpayclone.exceptions.NegocioException;
import br.com.dio.picpayclone.modelo.Transacao;
import br.com.dio.picpayclone.modelo.Usuario;
import br.com.dio.picpayclone.repositories.UsuarioRepository;
import br.com.dio.picpayclone.services.UsuarioService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

	private final UsuarioRepository usuarioRepository;
	private final UsuarioConversor usuarioConversor;

	@Override
	public Usuario consultarEntidade(String login) {
		return usuarioRepository.findByLogin(login);
	}

	@Override
	@Async("asyncExecutor")
	public void atualizarSaldo(Transacao transacao, Boolean isCartaoCredito) {
		decrementarSaldo(transacao, isCartaoCredito);
		incrementarSaldo(transacao);
	}

	private void incrementarSaldo(Transacao transacaoSalva) {
		usuarioRepository.updateIncrementarSaldo(transacaoSalva.getDestino().getLogin(), transacaoSalva.getValor());
	}

	private void decrementarSaldo(Transacao transacaoSalva, Boolean isCartaoCredito) {
		if (!isCartaoCredito) {
			usuarioRepository.updateDecrementarSaldo(transacaoSalva.getOrigem().getLogin(), transacaoSalva.getValor());
		}
	}

	@Override
	public void validar(Usuario... usuarios) {
		Arrays.asList(usuarios).stream().forEach(usuario -> {
			if (usuario == null) {
				throw new NegocioException("Usuário informado não existe!");
			}
		});
	}

	@Override
	public UsuarioDTO consultar(String login) {
		Usuario usuario = consultarEntidade(login);
		return usuarioConversor.converterEntidadeParaDTO(usuario);
	}

	@Override
	public List<UsuarioDTO> listar(String login) {
		List<Usuario> usuarios = usuarioRepository.findAll();
		List<Usuario> usuariosFiltrados = usuarios.stream().filter(u -> !u.getLogin().equals(login))
				.collect(Collectors.toList());
		return usuarioConversor.converterEntidadesParaDTOs(usuariosFiltrados);
	}

}
