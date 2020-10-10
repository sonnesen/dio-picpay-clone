package br.com.dio.picpayclone.resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.dio.picpayclone.dto.TokenDTO;

public abstract class ResourceBase<T> {

	protected ResponseEntity<T> responderRecursoCriado(T recurso) {
		return ResponseEntity.status(HttpStatus.CREATED).body(recurso);
	}

	protected ResponseEntity<T> responderRecursoCriadoComURI(T recurso, UriComponentsBuilder uriBuilder, String path,
			String codigo) {
		URI uri = uriBuilder.path(path).buildAndExpand(codigo).toUri();
		return ResponseEntity.created(uri).body(recurso);
	}

	protected ResponseEntity<T> responderRecursoNaoEncontrado() {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	protected ResponseEntity<T> responderSucesso() {
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	protected ResponseEntity<T> responderSucessoComRecurso(T recurso) {
		return ResponseEntity.status(HttpStatus.OK).body(recurso);
	}

	protected ResponseEntity<List<T>> responderListaVazia() {
		List<T> listaVazia = new ArrayList<>();
		return ResponseEntity.status(HttpStatus.OK).body(listaVazia);
	}

	protected ResponseEntity<List<T>> responderListaDeRecursos(List<T> listaRecursos) {
		return ResponseEntity.status(HttpStatus.OK).body(listaRecursos);
	}

	public ResponseEntity<Page<T>> responderListaDeRecursosPaginada(Page<T> recursosPaginados) {
		return ResponseEntity.status(HttpStatus.OK).body(recursosPaginados);
	}

	public ResponseEntity<TokenDTO> responderRequisicaoMalSucedida() {
		return ResponseEntity.badRequest().build();
	}

}
