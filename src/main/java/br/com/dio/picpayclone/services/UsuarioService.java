package br.com.dio.picpayclone.services;

import java.util.List;

import br.com.dio.picpayclone.dto.UsuarioDTO;
import br.com.dio.picpayclone.modelo.Transacao;
import br.com.dio.picpayclone.modelo.Usuario;

public interface UsuarioService {

	Usuario consultarEntidade(String login);

	void atualizarSaldo(Transacao transacao, Boolean isCartaoCredito);

	void validar(Usuario... usuarios);

	UsuarioDTO consultar(String login);

	List<UsuarioDTO> listar(String login);

}
