package br.com.dio.picpayclone.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenDTO {

	private String token;
	private String tipo;
}
