package com.project.metrics.model;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class Resposta {
	
	private String usuario;
	
	private String token;
	
	private Date dt_inicial;
	
	private Date dt_final;
	
	private Item projeto;
	
	private Item periodicidade;
	
	private Coluna colunato;
	
	private Coluna colunain;
	
	private List<Coluna> colunas;
	
	private Nome repository;
	
}
