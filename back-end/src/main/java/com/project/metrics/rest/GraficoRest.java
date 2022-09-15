package com.project.metrics.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.metrics.control.GraficoControl;
import com.project.metrics.model.Grafico;
import com.project.metrics.model.Resposta;

@RestController
@CrossOrigin
@RequestMapping("/grafico")
public class GraficoRest {
	
	@Autowired
	private GraficoControl graficoControl;
	
	@PostMapping
	public Grafico getGrafico(@RequestParam String tipo, @RequestBody Resposta resposta) {
		return graficoControl.getGrafico(tipo, resposta);
	}

}
