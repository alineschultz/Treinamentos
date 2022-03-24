package com.aline.leitorArquivos.dtos;

import java.util.Iterator;

public class FilmeDto {
	private int year;
	private String title;
	private String studio;
	private String producer;
	private String winner;

	public FilmeDto() {
		// TODO Auto-generated constructor stub
	}
	
	public boolean  preencheDados(String leituraArquivo) {
		boolean registrovalido = true;
		try {
			
			leituraArquivo = leituraArquivo.replace('[',' ');
			leituraArquivo = leituraArquivo.replace(']',' ');
			leituraArquivo = leituraArquivo.replace(',',' ');
			leituraArquivo = leituraArquivo.trim();
			String[] slipted = leituraArquivo.split(";");

			if(slipted.length >= 1) {
				if (slipted[0] != null) {
					this.setYear(Integer.parseInt(slipted[0]));
				}
				
			}
			
			if (slipted.length >= 2) {
				if (slipted[1] != null) {
					this.setTitle(slipted[1]);
				}
				
			}
				

			if (slipted.length >= 3) {
				if (slipted[2] != null) {
					this.setStudio(slipted[2]);
				}
			}
			
			if (slipted.length >= 4) {
				if (slipted[3] != null) {
					this.setProducer(slipted[3]);
				}
			}
			
			if (slipted.length >= 5) {
				this.setWinner(slipted[4]);
			}else {
				this.setWinner("no");
			}
				
			
		} catch (Exception e) {
			System.out.println("erro em dado");
		}

//		}
		return registrovalido;		
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStudio() {
		return studio;
	}

	public void setStudio(String studio) {
		this.studio = studio;
	}

	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	public String getWinner() {
		return winner;
	}

	public void setWinner(String winner) {
		this.winner = winner;
	}
	
	
	

}
