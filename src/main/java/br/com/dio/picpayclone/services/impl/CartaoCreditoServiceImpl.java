package br.com.dio.picpayclone.services.impl;

import org.springframework.stereotype.Service;

import br.com.dio.picpayclone.converters.CartaoCreditoConversor;
import br.com.dio.picpayclone.dto.CartaoCreditoDTO;
import br.com.dio.picpayclone.modelo.CartaoCredito;
import br.com.dio.picpayclone.repositories.CartaoCreditoRepository;
import br.com.dio.picpayclone.services.CartaoCreditoService;
import br.com.dio.picpayclone.services.UsuarioService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartaoCreditoServiceImpl implements CartaoCreditoService {

	private final CartaoCreditoRepository cartaoCreditoRepository;
	private final CartaoCreditoConversor cartaoCreditoConversor;
	private final UsuarioService usuarioService;

	@Override
	public CartaoCreditoDTO salvar(CartaoCreditoDTO cartaoCreditoDTO) {
		CartaoCreditoDTO cartaoCreditoRetorno = null;

		if (cartaoCreditoDTO.getIsSalva()) {
			CartaoCredito cartaoCredito = cartaoCreditoConversor.converterDTOParaEntidade(cartaoCreditoDTO);
			usuarioService.validar(cartaoCredito.getUsuario());
			CartaoCredito cartaoCreditoSalvo = cartaoCreditoRepository.save(cartaoCredito);
			cartaoCreditoRetorno = cartaoCreditoConversor.converterEntidadeParaDTO(cartaoCreditoSalvo);
		}

		return cartaoCreditoRetorno;
	}

}
