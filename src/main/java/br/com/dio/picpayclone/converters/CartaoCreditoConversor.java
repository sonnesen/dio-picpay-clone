package br.com.dio.picpayclone.converters;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

import br.com.dio.picpayclone.dto.CartaoCreditoDTO;
import br.com.dio.picpayclone.modelo.CartaoCredito;
import br.com.dio.picpayclone.services.UsuarioService;
import br.com.dio.picpayclone.utils.CartaoCreditoUtil;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CartaoCreditoConversor extends ConversorBase<CartaoCredito, CartaoCreditoDTO> {

	private final UsuarioService usuarioService;

	@Override
	public CartaoCreditoDTO converterEntidadeParaDTO(CartaoCredito entidade) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.addMappings(new PropertyMap<CartaoCredito, CartaoCreditoDTO>() {

			@Override
			protected void configure() {

			}
		});

		return modelMapper.map(entidade, CartaoCreditoDTO.class);
	}

	@Override
	public CartaoCredito converterDTOParaEntidade(CartaoCreditoDTO dto) {
		return CartaoCredito.builder().bandeira(dto.getBandeira()).numero(CartaoCreditoUtil.mascarar(dto.getNumero()))
				.numeroToken(dto.getNumeroToken())
				.usuario(usuarioService.consultarEntidade(dto.getUsuario().getLogin())).build();
	}

}
