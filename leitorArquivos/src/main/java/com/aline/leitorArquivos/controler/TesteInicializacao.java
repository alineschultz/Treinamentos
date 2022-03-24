package com.aline.leitorArquivos.controler;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TesteInicializacao {

	@RequestMapping("/")
	@ResponseBody
	public String testeInicializacao() {
		return "Pacote OK";
	}
	
}
