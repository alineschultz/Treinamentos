package com.aline.leitorArquivos.dtos;

import java.util.List;

public class IntervaloDto {
	
	String tipoIntervalo;
	List<PremiacaoDto> listaPremiacao;
	
	public IntervaloDto() {
		// TODO Auto-generated constructor stub
	}

	public String getTipoIntervalo() {
		return tipoIntervalo;
	}

	public void setTipoIntervalo(String tipoIntervalo) {
		this.tipoIntervalo = tipoIntervalo;
	}

	public List<PremiacaoDto> getListaPremiacao() {
		return listaPremiacao;
	}

	public void setListaPremiacao(List<PremiacaoDto> listaPremiacao) {
		this.listaPremiacao = listaPremiacao;
	}
	
	
	

}
