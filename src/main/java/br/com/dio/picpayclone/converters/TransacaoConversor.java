package br.com.dio.picpayclone.converters;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import br.com.dio.picpayclone.dto.TransacaoDTO;
import br.com.dio.picpayclone.modelo.Transacao;
import br.com.dio.picpayclone.services.UsuarioService;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TransacaoConversor extends ConversorBase<Transacao, TransacaoDTO> {

	private final UsuarioService usuarioService;

	@Override
	public TransacaoDTO converterEntidadeParaDTO(Transacao entidade) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.addMappings(new PropertyMap<Transacao, TransacaoDTO>() {

			@Override
			protected void configure() {

			}
		});

		return modelMapper.map(entidade, TransacaoDTO.class);
	}

	@SuppressWarnings("unchecked")
	public Page<TransacaoDTO> converterPageEntidadeParaDTO(Page<Transacao> entidade) {
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.addMappings(new PropertyMap<Page<Transacao>, Page<TransacaoDTO>>() {
			@Override
			protected void configure() {

			}
		});

		return modelMapper.map(entidade, Page.class);
	}

	@Override
	public Transacao converterDTOParaEntidade(TransacaoDTO dto) {
		return Transacao.builder().codigo(dto.getCodigo()).dataHora(dto.getDataHora()).valor(dto.getValor())
				.destino(usuarioService.consultarEntidade(dto.getDestino().getLogin()))
				.origem(usuarioService.consultarEntidade(dto.getOrigem().getLogin())).build();
	}

}
