package br.com.dio.picpayclone.resources;

import javax.validation.Valid;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.dio.picpayclone.dto.TransacaoDTO;
import br.com.dio.picpayclone.services.TransacaoService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/transacoes")
public class TransacaoResource extends ResourceBase<TransacaoDTO> {

	private final TransacaoService transacaoService;

	@GetMapping
	@Cacheable(cacheNames = "Transacoes", key = "#root.method.name")
	public ResponseEntity<Page<TransacaoDTO>> listar(@PageableDefault(page = 0, size = 20) Pageable paginacao,
			@RequestParam String login) {
		Page<TransacaoDTO> transacoes = transacaoService.listar(paginacao, login);
		return super.responderListaDeRecursosPaginada(transacoes);
	}

	@PostMapping
	@CacheEvict(cacheNames = "Transacoes", allEntries = true)
	public ResponseEntity<TransacaoDTO> salvar(@RequestBody @Valid TransacaoDTO transacaoDTO,
			UriComponentsBuilder uriBuilder) {
		TransacaoDTO transacaoRetornoDTO = transacaoService.processar(transacaoDTO);
		return responderRecursoCriadoComURI(transacaoRetornoDTO, uriBuilder, "/transacoes/{codigo}",
				transacaoRetornoDTO.getCodigo());
	}

}
