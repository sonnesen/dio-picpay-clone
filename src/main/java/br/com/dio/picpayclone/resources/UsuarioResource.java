package br.com.dio.picpayclone.resources;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.dio.picpayclone.dto.UsuarioDTO;
import br.com.dio.picpayclone.services.UsuarioService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/usuarios")
public class UsuarioResource extends ResourceBase<UsuarioDTO> {

	private final UsuarioService usuarioService;

	@GetMapping("/{login}")
	public ResponseEntity<UsuarioDTO> consultar(@PathVariable String login) {
		UsuarioDTO usuario = usuarioService.consultar(login);
		return responderSucessoComRecurso(usuario);
	}

	@GetMapping("/contatos")
	public ResponseEntity<List<UsuarioDTO>> listar(@RequestParam String login) {
		List<UsuarioDTO> usuarios = usuarioService.listar(login);
		return responderListaDeRecursos(usuarios);
	}

	@GetMapping("/{login}/saldo")
	public ResponseEntity<UsuarioDTO> consultarSaldo(@PathVariable String login) {
		UsuarioDTO usuario = usuarioService.consultar(login);
		return responderSucessoComRecurso(usuario);
	}

}
