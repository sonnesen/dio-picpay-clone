package br.com.dio.picpayclone.modelo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class EntidadeBase {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

}
