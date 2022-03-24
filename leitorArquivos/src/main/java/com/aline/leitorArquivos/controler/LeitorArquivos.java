package com.aline.leitorArquivos.controler;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aline.leitorArquivos.converter.FilmeConverterDTOtoEntity;
import com.aline.leitorArquivos.dtos.FilmeDto;
import com.aline.leitorArquivos.dtos.IntervaloDto;
import com.aline.leitorArquivos.dtos.PremiacaoDto;
import com.aline.leitorArquivos.modelo.Filme;
import com.aline.leitorArquivos.repository.FilmeRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

@RestController
public class LeitorArquivos {
	
	@Autowired
	private FilmeRepository rps;
	
	@RequestMapping("/listar")
	public List<IntervaloDto> leituraValidacao(String fileName) {
		List<IntervaloDto> listaIntervalos = new ArrayList<IntervaloDto>();
		List<Filme>	 listaDeFilmes = rps.findAll();
		List<FilmeDto> listaLida = new ArrayList<FilmeDto>();
		
		if(fileName == null) {
			System.out.println("Erro no arquivo");
			fileName = "C:\\Users\\aline\\Desktop\\dev\\Nova pasta\\movielist.csv";
		}else {
			System.out.println(fileName);
		}
        
		try (CSVReader reader = new CSVReader(new FileReader(fileName))) {
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

          
          
          
          IntervaloDto intervaloMin = new IntervaloDto();
          intervaloMin.setTipoIntervalo("min");
          IntervaloDto intervaloMax = new IntervaloDto();
          intervaloMax.setTipoIntervalo("max");
           
          List<Filme> listaFiltradacomvencedores = rps.findByProducerInterval();
         
          System.err.println(listaFiltradacomvencedores.size());
          
          for (Filme filmeAtual : listaFiltradacomvencedores) {
        	  String currentProducer = filmeAtual.getProducer();
        	  System.out.println(currentProducer);
        	  
        	  PremiacaoDto premiacaoDto = new PremiacaoDto();
        	  premiacaoDto.setProducer(currentProducer);
        	  premiacaoDto.setPreviousWin(filmeAtual.getYear());
        	  
        	  for (Filme filmeComparado : listaFiltradacomvencedores) {
				if (filmeAtual.getProducer().equalsIgnoreCase(filmeComparado.getProducer()) && filmeAtual.getId() != filmeComparado.getId()) {
//					checkGreaterInterval(premiacaoDto.getPreviousWin(), filmeComparado.getYear());
					premiacaoDto.setFollowingWin(filmeComparado.getYear());
					intervaloMin.getListaPremiacao().add(premiacaoDto);
				}
			}
          }
          
         
         List<Filme> listaFiltrada2 = new ArrayList<Filme>();
          
         listaIntervalos.add(intervaloMin);
          listaIntervalos.add(intervaloMax);
          
            
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
		
		
		
		
		return listaIntervalos;
	}
	
}
