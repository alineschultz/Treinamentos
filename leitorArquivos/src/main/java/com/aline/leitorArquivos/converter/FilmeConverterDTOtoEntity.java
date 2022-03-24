package com.aline.leitorArquivos.converter;

import com.aline.leitorArquivos.controler.form.FilmeForm;
import com.aline.leitorArquivos.dtos.FilmeDto;
import com.aline.leitorArquivos.modelo.Filme;

public class FilmeConverterDTOtoEntity{

	public static FilmeDto convertFromEntity(Filme filmeEntity) {
		FilmeDto filmeDto = new FilmeDto();
		filmeDto.setProducer(filmeEntity.getProducer());
		filmeDto.setStudio(filmeEntity.getStudio());
		filmeDto.setTitle(filmeEntity.getTitle());
		filmeDto.setWinner(filmeEntity.getWinner());
		filmeDto.setYear(filmeEntity.getYear());
		return filmeDto;
	}

	public static Filme convertFromDTO(FilmeDto filmeDto) {
		
		Filme filmeEntity = new Filme();
		
		filmeEntity.setProducer(filmeDto.getProducer());
		filmeEntity.setStudio(filmeDto.getStudio());
		filmeEntity.setTitle(filmeDto.getTitle());
		filmeEntity.setWinner(filmeDto.getWinner());
		filmeEntity.setYear(filmeDto.getYear());
		
		return filmeEntity;
	}

	
	public static Filme convertFormToEntity(FilmeForm form) {
		
		Filme filmeEntity = new Filme();
		
		filmeEntity.setProducer(form.getProducer());
		filmeEntity.setStudio(form.getStudio());
		filmeEntity.setTitle(form.getTitle());
		filmeEntity.setWinner(form.getWinner());
		filmeEntity.setYear(form.getYear());
		
		return filmeEntity;
	}

	
	
	
	
}
