package br.com.dio.picpayclone.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.dio.picpayclone.dto.TransacaoDTO;

public interface TransacaoService {

	Page<TransacaoDTO> listar(Pageable paginacao, String login);

	TransacaoDTO processar(TransacaoDTO transacaoDTO);

}
