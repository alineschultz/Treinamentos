package com.aline.leitorArquivos.controler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.aline.leitorArquivos.controler.form.FilmeForm;
import com.aline.leitorArquivos.converter.FilmeConverterDTOtoEntity;
import com.aline.leitorArquivos.dtos.FilmeDto;
import com.aline.leitorArquivos.dtos.IntervaloDto;
import com.aline.leitorArquivos.dtos.PremiacaoDto;
import com.aline.leitorArquivos.modelo.Filme;
import com.aline.leitorArquivos.repository.FilmeRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

@RestController
@RequestMapping("/api-premiacao")
public class LeitorArquivos2 {
	
	@Autowired
	private FilmeRepository rps;
	
	@GetMapping
	public List<IntervaloDto> leituraValidacao() {
		List<IntervaloDto> listaIntervalos = new ArrayList<IntervaloDto>();
		try {
          IntervaloDto intervaloMin = new IntervaloDto();
          intervaloMin.setTipoIntervalo("min");
          IntervaloDto intervaloMax = new IntervaloDto();
          intervaloMax.setTipoIntervalo("max");
           
          List<Filme> listaFiltradacomvencedores = rps.findByProducerInterval();
         
          System.err.println(listaFiltradacomvencedores.size());
          
          Map<String, List<Integer>> mapaProdutores = new HashMap<String, List<Integer>>();
          
          for (Filme filmeAtual : listaFiltradacomvencedores) {
        	  String currentProducer = filmeAtual.getProducer();
        	  System.out.println(currentProducer);
        	  List<Integer> listaAnosVencedores = mapaProdutores.get(currentProducer);
        	  if (listaAnosVencedores == null) {
        		  listaAnosVencedores = new ArrayList<Integer>();
        		  mapaProdutores.put(currentProducer, listaAnosVencedores);
        	  }
        	  listaAnosVencedores.add(filmeAtual.getYear());
          }
          //aqui temos um mapa de todos os produtores, com uma lista dos anos em que venceram
          
          for (Map.Entry<String, List<Integer>> produtor : mapaProdutores.entrySet()) {
			String key = produtor.getKey();
			List<Integer> listaAnosVencedores = produtor.getValue();
			
			int[] infoIntervaloMax = maiorPrimeiroIntervalo(listaAnosVencedores);
			int[] infoIntervaloMin = menorPrimeiroIntervalo(listaAnosVencedores);
			
			if (infoIntervaloMax != null) {
				preencherPremiacao(intervaloMax, key, infoIntervaloMax);
			}
			if (infoIntervaloMin != null) {
				preencherPremiacao(intervaloMin, key, infoIntervaloMin);
			}
			
		}
          
         
         List<Filme> listaFiltrada2 = new ArrayList<Filme>();
          
         listaIntervalos.add(intervaloMin);
          listaIntervalos.add(intervaloMax);
          
            
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		return listaIntervalos;
	}
	
	
	
	@DeleteMapping
	public String limparBase(){
		String retorno = "";
		
		try {
			rps.deleteAll();
			retorno = "sucesso na limpeza da base";
		} catch (Exception e) {
			retorno = "Erro na limpeza da base";
		}
		
		
		
		return retorno;
		
	}
		
	@PutMapping
	public String putting(@RequestBody FilmeForm form){
		String retorno 	= "";

		Filme filmeEntity = new Filme();
		
		
		
		try {
			filmeEntity = rps.findByTitle_Producer (form.getTitle(), form.getProducer());
			
			filmeEntity.setProducer(form.getProducer());
			filmeEntity.setStudio(form.getStudio());
			filmeEntity.setTitle(form.getTitle());
			filmeEntity.setWinner(form.getWinner());
			filmeEntity.setYear(form.getYear());
			
			rps.save(filmeEntity);
			
			retorno = "sucesso na inclusao";
		} catch (Exception e) {
			retorno = "Erro na limpeza da base";
		}
		
		
		
		return retorno;
		
	}
	
	@PostMapping
	public String incluirDados(@RequestBody FilmeForm form) {
		
		String retorno 	= "";
		Filme filmeEntity = new Filme();
		filmeEntity = FilmeConverterDTOtoEntity.convertFormToEntity(form);
		try {
			rps.save(filmeEntity);
			retorno = "sucesso na inclusao";
		} catch (Exception e) {
			retorno = "Erro na inclusao";
		}
		
		
		
		return retorno;
		
	}
	
	

	@PostConstruct
	private void inicializarBase() {
		String fileName = "movielist.csv";
		List<Filme>	 listaDeFilmes = rps.findAll();
		List<FilmeDto> listaLida = new ArrayList<FilmeDto>();
		
//		if(fileName == null) {
//			System.out.println("Erro no arquivo");
//			fileName = "C:\\Users\\aline\\Desktop\\dev\\Nova pasta\\movielist.csv";
//		}else {
//			System.out.println(fileName);
//		}
		
		try {
			CSVReader reader = new CSVReader(new FileReader(fileName));
            List<String[]> arquivoLido = reader.readAll();
            String[] header = arquivoLido.get(0);
           
            String headerPadrao = header[0].toString();

            //Validar se o header esta no padrao correto
            if (headerPadrao.compareTo("year;title;studios;producers;winner")==0) {
				System.out.println("header validado com sucesso");
			}else {
				System.out.println("Header do arquivo fora do padrão, o esperado para execução é year;title;studios;producers;winner");
				//TODO ThROW exception
			}
            

            System.out.println(Arrays.toString(arquivoLido.get(0)));
            
            for (int i = 1; i < arquivoLido.size(); i++) {
            	String lida = "";
            	System.out.println(Arrays.toString(arquivoLido.get(i)));
            	FilmeDto filmeDTO  =  new FilmeDto();
            	lida= Arrays.toString(arquivoLido.get(i));
            	if (filmeDTO.preencheDados(lida)) {
            		listaLida.add(filmeDTO);
            		listaDeFilmes.add(FilmeConverterDTOtoEntity.convertFromDTO(filmeDTO));
					
				}
            	
			}
            
          rps.saveAll(listaDeFilmes);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CsvException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void preencherPremiacao(IntervaloDto intervalo, String producer, int[] infoIntervalo) {
		PremiacaoDto premiacaoMax = new PremiacaoDto();
		premiacaoMax.setProducer(producer);
		premiacaoMax.setPreviousWin(infoIntervalo[0]);
		premiacaoMax.setFollowingWin(infoIntervalo[1]);
		premiacaoMax.setInterval(infoIntervalo[2]);
		if (intervalo.getListaPremiacao() == null) {
			intervalo.setListaPremiacao(new ArrayList<PremiacaoDto>());
		}
		intervalo.getListaPremiacao().add(premiacaoMax);
	}
	
	private int[] maiorPrimeiroIntervalo(List<Integer> anosVencedores) {
		if (anosVencedores == null || anosVencedores.size() == 1) {
			return null;
		}
		int intervalo = 0;
		int intervaloTemp = 0;
		int[] retorno = new int[3];
		
		int anoAnterior = 0;
		for (Integer anoAtual : anosVencedores) {
			if (anoAnterior == 0) {
				anoAnterior = anoAtual;
				continue;
			}
			intervaloTemp = anoAtual - anoAnterior;
			
			if (intervaloTemp > intervalo) {
				intervalo = intervaloTemp;
				retorno[0] = anoAnterior;
				retorno[1] = anoAtual;
				retorno[2] = intervalo;
			}
			anoAnterior = anoAtual;
		}
		return retorno;
	}
	
	private int[] menorPrimeiroIntervalo(List<Integer> anosVencedores) {
		if (anosVencedores == null || anosVencedores.size() == 1) {
			return null;
		}
		int intervalo = 999999999;
		int intervaloTemp = 0;
		int[] retorno = new int[3];
		
		int anoAnterior = 0;
		for (Integer anoAtual : anosVencedores) {
			if (anoAnterior == 0) {
				anoAnterior = anoAtual;
				continue;
			}
			intervaloTemp = anoAtual - anoAnterior;
			
			if (intervaloTemp < intervalo) {
				intervalo = intervaloTemp;
				retorno[0] = anoAnterior;
				retorno[1] = anoAtual;
				retorno[2] = intervalo;
			}
			anoAnterior = anoAtual;
		}
		return retorno;
	}
	
}
