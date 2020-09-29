package br.com.dio.picpayclone.converters;

import java.util.ArrayList;
import java.util.List;

public abstract class ConversorBase<E, D> {

	public abstract D converterEntidadeParaDTO(E entidade);

	public abstract E converterDTOParaEntidade(D dto);

	public List<D> converterEntidadesParaDTOs(List<E> entidades) {
		List<D> dtos = new ArrayList<>();
		entidades.stream().forEach(entidade -> dtos.add(converterEntidadeParaDTO(entidade)));
		return dtos;
	}

	public List<E> converterDTOsParaEntidades(List<D> dtos) {
		List<E> entidades = new ArrayList<>();
		dtos.stream().forEach(dto -> entidades.add(converterDTOParaEntidade(dto)));
		return entidades;
	}

}
