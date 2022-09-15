package com.project.metrics.model;

import java.util.ArrayList;

import lombok.Data;

@Data
public class Grafico {
	
	private ArrayList<String> labels;
	
	private ArrayList<Dataset> datasets; 	
	
}
