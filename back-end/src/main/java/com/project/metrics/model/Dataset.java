package com.project.metrics.model;

import java.util.ArrayList;

import lombok.Data;

@Data
public class Dataset {
	
	private String label;
	
	private String type;
	
	private String backgroundColor;
	
	private String borderColor;
	
	private Boolean fill;
	
	private Double tension;
	
	private ArrayList<Object> data;
	
}
