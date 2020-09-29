package br.com.dio.picpayclone.services.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.dio.picpayclone.converters.TransacaoConversor;
import br.com.dio.picpayclone.dto.TransacaoDTO;
import br.com.dio.picpayclone.modelo.Transacao;
import br.com.dio.picpayclone.repositories.TransacaoRepository;
import br.com.dio.picpayclone.services.CartaoCreditoService;
import br.com.dio.picpayclone.services.TransacaoService;
import br.com.dio.picpayclone.services.UsuarioService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransacaoServiceImpl implements TransacaoService {

	private final TransacaoRepository transacaoRepository;

	private final TransacaoConversor transacaoConversor;

	private final UsuarioService usuarioService;
	private final CartaoCreditoService cartaoCreditoService;

	@Override
	public TransacaoDTO processar(TransacaoDTO transacaoDTO) {
		Transacao transacao = salvar(transacaoDTO);
		cartaoCreditoService.salvar(transacaoDTO.getCartaoCredito());
		usuarioService.atualizarSaldo(transacao, transacaoDTO.getIsCartaoCredito());
		return transacaoConversor.converterEntidadeParaDTO(transacao);
	}

	private Transacao salvar(TransacaoDTO transacaoDTO) {
		Transacao transacao = transacaoConversor.converterDTOParaEntidade(transacaoDTO);
		usuarioService.validar(transacao.getDestino(), transacao.getOrigem());
		return transacaoRepository.save(transacao);
	}

	@Override
	public Page<TransacaoDTO> listar(Pageable paginacao, String login) {
		Page<Transacao> transacoes = transacaoRepository.findByOrigem_LoginOrDestino_Login(login, login, paginacao);
		return transacaoConversor.converterPageEntidadeParaDTO(transacoes);
	}

}
