package com.project.metrics.control;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.metrics.model.Coluna;
import com.project.metrics.model.Coordenada;
import com.project.metrics.model.Dataset;
import com.project.metrics.model.Grafico;
import com.project.metrics.model.Resposta;

import lombok.Getter;

@Controller
public class GraficoControl {
	
	private HashMap<Integer, CoresEnum> cores = new HashMap<Integer, CoresEnum>();
	
	public GraficoControl() {
		int cont = 0;
		for (CoresEnum cor : CoresEnum.values()) {
			cores.put(++cont, cor);
		}
	}
	
	public Grafico getGrafico(String tipo, Resposta resposta) {
		switch (tipo) {
		    case "cyc":
		    	return getCyc(resposta);
		    case "lea":
		    	return getLea(resposta);
		    case "thr":
		    	return getThr(resposta);
		    case "cfd":
		    	return getCfd(resposta);
		    case "wip":
		    	return getWip(resposta);
		    default:
		    	return null;
		}
		
	}
	
	public Grafico getCyc(Resposta resposta) {
		Grafico grafico = new Grafico();
		ArrayList<Dataset> datasets = new ArrayList<>();
		HashMap<String, LocalDate> dts = getFechadas(resposta);
		grafico.setLabels(new ArrayList<>(dts.keySet()));
		Dataset dataset = new Dataset();
	    dataset.setLabel("Issue (number) x Number of Days");
	    CoresEnum cor = cores.get(5);
	    dataset.setBackgroundColor(cor.backgroundColor);
	    dataset.setBorderColor(cor.borderColor);
	    ArrayList<Object> data = new ArrayList<>();
		for(Map.Entry<String, LocalDate> entry : dts.entrySet()) {
			String url = "https://api.github.com/repos/" + resposta.getUsuario() + "//" + resposta.getRepository().getName() + "/issues/" + entry.getKey() + "/timeline";
			ResponseEntity<String> timelines = getJson(url, resposta.getToken());
			ObjectMapper mapper_tm = new ObjectMapper();
			Integer valor = 0;
			try {
				JsonNode node_tm = mapper_tm.readTree(timelines.getBody());
	        	if (node_tm.isArray()) {
	        		LocalDate dt_ini = null;
	        		for (Object obj : node_tm) {
						JsonNode local = mapper_tm.readTree(obj.toString());
						if (local.get("project_card") != null && local.get("project_card").get("column_name") != null) {
							String name = local.get("project_card").get("column_name").asText();
							if (name.equalsIgnoreCase(resposta.getColunain().getName())) {
								dt_ini = LocalDate.parse(local.get("created_at").asText().substring(0,10));
							}
	        			}
					}
	        		if (dt_ini != null) {	  
	        			Long p2 = ChronoUnit.DAYS.between(dt_ini,entry.getValue());
	        			valor = p2.intValue();	        				        		
	        		}
	        	}
			} catch (JsonProcessingException e) {
	            e.printStackTrace();
	        }
			data.add(valor);
		}
		dataset.setData(data);
		datasets.add(dataset);
		grafico.setDatasets(datasets);
		return grafico;
	}
	
	public Grafico getLea(Resposta resposta) {
		int totalIssues = 0;
		int totalDias = 0;
		Grafico grafico = new Grafico();
		ArrayList<Dataset> datasets = new ArrayList<>();
		HashMap<String, LocalDate> dts = getFechadas(resposta);
		grafico.setLabels(new ArrayList<>(dts.keySet()));
		Dataset dataset = new Dataset();
	    dataset.setLabel("Issue (number) x Number of Days");
	    CoresEnum cor = cores.get(4);
	    dataset.setBackgroundColor(cor.backgroundColor);
	    dataset.setBorderColor(cor.borderColor);
	    dataset.setType("scatter");
		ArrayList<Object> data = new ArrayList<>();
		for(Map.Entry<String, LocalDate> entry : dts.entrySet()) {
			Coordenada coordenada = new Coordenada();
			coordenada.setX(Integer.parseInt(entry.getKey()));
			String url = "https://api.github.com/repos/" + resposta.getUsuario() + "//" + resposta.getRepository().getName() + "/issues/" + entry.getKey() + "/timeline";
			ResponseEntity<String> timelines = getJson(url, resposta.getToken());
			ObjectMapper mapper_tm = new ObjectMapper();
			try {
				JsonNode node_tm = mapper_tm.readTree(timelines.getBody());
	        	if (node_tm.isArray()) {
	        		for (Object obj : node_tm) {
						JsonNode local = mapper_tm.readTree(obj.toString());
						if (local.get("project_card") != null && local.get("project_card").get("column_name") != null) {
							String name = local.get("project_card").get("column_name").asText();
							if (name.equalsIgnoreCase(resposta.getColunato().getName())) {
								LocalDate dt_td = LocalDate.parse(local.get("created_at").asText().substring(0,10));
								Long p2 = ChronoUnit.DAYS.between(dt_td, entry.getValue());
								coordenada.setY(p2.intValue());
								++totalIssues;
								totalDias = totalDias + (p2.intValue());
							}
	        			}
					}
	        	}
			} catch (JsonProcessingException e) {
	            e.printStackTrace();
	        }
			data.add(coordenada);
		}
		
		dataset.setData(data);
		double result = (double) totalDias / totalIssues; 
		int LeadMedio = (int) Math.round(result);
		Dataset dataset2 = new Dataset();
		dataset2.setLabel("Average Lead Time");
	    CoresEnum cor2 = cores.get(3);
	    dataset2.setBackgroundColor(cor2.backgroundColor);
	    dataset2.setBorderColor(cor2.borderColor);
	    dataset2.setType("line");
	    dataset.setFill(false);
	    ArrayList<Object> data2 = new ArrayList<>();
	    for(@SuppressWarnings("unused") Map.Entry<String, LocalDate> entry : dts.entrySet()) {
	    	data2.add(LeadMedio);
	    }
	    dataset2.setData(data2);
	    datasets.add(dataset2);
		datasets.add(dataset);
		grafico.setDatasets(datasets);
		return grafico;
	}
	
	public Grafico getThr(Resposta resposta) {
		Grafico grafico = new Grafico();
		ArrayList<Dataset> datasets = new ArrayList<>();
		HashMap<String, LocalDate> dates = getDates(resposta);
		grafico.setLabels(new ArrayList<>(dates.keySet()));
		HashMap<String, LocalDate> fechadas = getFechadas(resposta);
		ArrayList<LocalDate> dts = new ArrayList<LocalDate>();
		for(Map.Entry<String, LocalDate> fechada : fechadas.entrySet()) {
			dts.add(fechada.getValue());
		}
		Dataset dataset = new Dataset();
	    dataset.setLabel("Issues closed");
	    CoresEnum cor = cores.get(6);
	    dataset.setBackgroundColor(cor.backgroundColor);
		dataset.setBorderColor(cor.borderColor);
		ArrayList<Object> data = new ArrayList<>();
		if (resposta.getPeriodicidade().getId().equals("1")) {
			int cont = 0;
			for(Map.Entry<String, LocalDate> entry : dates.entrySet()) {
				for(LocalDate dt : dts) {
					if (entry.getValue().isEqual(dt)) {
						++cont;
					}
				}
				data.add(cont);
        		cont = 0;
			}
		} else if (resposta.getPeriodicidade().getId().equals("2") || resposta.getPeriodicidade().getId().equals("3")) {	
			int cont = 0;
			LocalDate dt_final = null;
			LocalDate dt_inicial = null;
			ArrayList<LocalDate> locais = new ArrayList<LocalDate>();	
			for(Map.Entry<String, LocalDate> entry : dates.entrySet()) {
				locais.add(entry.getValue());
			}
			for(LocalDate local : locais) {
				dt_final = local;
				dt_inicial = local;
				if (resposta.getPeriodicidade().getId().equals("2")) {
					dt_inicial = dt_inicial.minusDays(1);
					dt_final = dt_final.plusWeeks(1);
				}
				if (resposta.getPeriodicidade().getId().equals("3")) {
					TemporalAdjuster temporalAdjuster = TemporalAdjusters.firstDayOfMonth();
					dt_inicial = dt_inicial.with(temporalAdjuster);
					dt_final = dt_inicial.plusMonths(1);
				}
				for(LocalDate dt : dts) {
					if (dt.isAfter(dt_inicial) && dt.isBefore(dt_final)) {
						++cont;
					}
				}
				data.add(cont);
        		cont = 0;
			}	
		}
		dataset.setData(data);
		datasets.add(dataset);
		grafico.setDatasets(datasets);
		return grafico;
	}
	
	public Grafico getCfd(Resposta resposta) {
		Grafico grafico = new Grafico();
		ArrayList<Dataset> datasets = new ArrayList<>();
		HashMap<String, LocalDate> dates = getDates(resposta);
		grafico.setLabels(new ArrayList<>(dates.keySet()));
		HashMap<String, String> colunas = getColunas(resposta);
		if (!colunas.isEmpty()) {
			int cont = 0;
			ArrayList<HashMap<String, LocalDate>> hist = new ArrayList<HashMap<String,LocalDate>>();
			HashMap<String, ArrayList<HashMap<String, LocalDate>>> map = new HashMap<String, ArrayList<HashMap<String, LocalDate>>>();
			for(Map.Entry<String, String> entry : colunas.entrySet()) {
				Dataset dataset = new Dataset();
			    dataset.setLabel(entry.getValue());
			    CoresEnum cor = cores.get(++cont);
			    dataset.setBackgroundColor(cor.transpColor);
				dataset.setBorderColor(cor.borderColor);
				dataset.setFill(true);
			    dataset.setTension(0.4);
				if (cont == 10) {
					cont = 0;
				}
				String url = "https://api.github.com/projects/columns/" + entry.getKey() + "/cards";
				ResponseEntity<String> cards = getJson(url, resposta.getToken());
				ObjectMapper mapper = new ObjectMapper();
				ArrayList<Object> data = new ArrayList<>();
				try {
		        	JsonNode node = mapper.readTree(cards.getBody());
		        	if (node.isArray()) {
		        		ArrayList<String> timelines = new ArrayList<String>();
		        		for (Object obj : node) {
		        			JsonNode local = mapper.readTree(obj.toString());
		        			if (local.get("content_url") != null) {
		        				timelines.add(local.get("content_url").asText() + "/timeline");
		        			}
		    			}
		        		for (String timeline : timelines) {
		        			String issui = timeline.replace("/timeline", "").substring(timeline.indexOf("issues/")+7);
		        			
		        			ResponseEntity<String> times = getJson(timeline, resposta.getToken());
		        			ObjectMapper mapper_times = new ObjectMapper();
		        			HashMap<String, LocalDate> dates_card = new HashMap<String, LocalDate>();
		    				try {
		    					JsonNode node_times = mapper_times.readTree(times.getBody());
		    					if (node_times.isArray()) {
		    						for (Object obj : node_times) {
		    							JsonNode local = mapper.readTree(obj.toString());
		    							if (local.get("project_card") != null) {
				    						dates_card.put(local.get("project_card").get("column_name").asText(), LocalDate.parse(local.get("created_at").asText().substring(0,10)));
					        			}
		    						}
		    						hist.add(getSort(dates_card));
		    					}
		    				} catch (JsonProcessingException e) {
		    		            e.printStackTrace();
		    		        }
		    				map.put(issui, hist);
		    				hist = new ArrayList<HashMap<String,LocalDate>>();
		        		}
		        		dataset.setData(data);
		        	}
		        } catch (JsonProcessingException e) {
		            e.printStackTrace();
		        }
			    datasets.add(dataset);
			}
			for(Map.Entry<String, LocalDate> data : dates.entrySet()) {
				int index = 0;
				for(Map.Entry<String, String> coluna : colunas.entrySet()) {
					int total = 0;
					for(Map.Entry<String, ArrayList<HashMap<String, LocalDate>>> registro : map.entrySet()) {
						var elemtos = registro.getValue();
						for(HashMap<String, LocalDate> elemto : elemtos) {
							for(Map.Entry<String, LocalDate> local : elemto.entrySet()) {
								int ilo = getIndex(coluna.getValue(), resposta.getColunas());
								boolean encontrou = false;
								boolean veri = this.verificaData(local.getValue(), data.getValue(), ilo, resposta);
								if (coluna.getValue().equals(local.getKey()) && veri) {
									encontrou = true;
									for(Map.Entry<String, LocalDate> loop : elemto.entrySet()) {
										int ilp = getIndex(loop.getKey(), resposta.getColunas());
										boolean veri_loop = this.verificaData(loop.getValue(), data.getValue(), ilo, resposta);
										if (veri_loop && ilp < ilo) {
											encontrou = false;
										}
									}
									if (encontrou) {
										++total;
									}
								}
							}
						}
					}
					datasets.get(index).getData().add(total);
					++index;
				}
			}
		}
		grafico.setDatasets(datasets);
		return grafico;
	}
	
	public Grafico getWip(Resposta resposta) {
		Grafico grafico = new Grafico();
		ArrayList<Dataset> datasets = new ArrayList<>();
		grafico.setLabels(new ArrayList<>(Arrays.asList("COLUMN")));
		HashMap<String, String> colunas = getColunas(resposta);
		int memo = 0;
		if (!colunas.isEmpty()) {
			int cont = 0;
			for(Map.Entry<String, String> entry : colunas.entrySet()) {
			    Dataset dataset = new Dataset();
			    dataset.setLabel(entry.getValue());
			    CoresEnum cor = cores.get(++cont);
			    dataset.setBackgroundColor(cor.backgroundColor);
				dataset.setBorderColor(cor.borderColor);
				if (cont == 10) {
					cont = 0;
				}
				String url = "https://api.github.com/projects/columns/" + entry.getKey() + "/cards";
				ResponseEntity<String> cards = getJson(url, resposta.getToken());
				ObjectMapper mapper = new ObjectMapper();
				ArrayList<Object> data = new ArrayList<>();
				int numero = 0;
				try {
		        	JsonNode node = mapper.readTree(cards.getBody());
		        	if (node.isArray()) {
		        		for (Object obj : node) {
		        			JsonNode local = mapper.readTree(obj.toString());
		        			if (local.get("content_url") != null) {
		        				++numero;
		        			}
		    			}
		        		data.add(numero);
		        		dataset.setData(data);
		        		dataset.setFill(true);
		        		if (entry.getValue().equals(resposta.getColunain().getName())) {
		        			dataset.setFill(false);
		        			memo = numero;
		        		}
		        	}
		        } catch (JsonProcessingException e) {
		            e.printStackTrace();
		        }
			    datasets.add(dataset);
			}
		}
		
		if (memo != 0) {
			Dataset dataset2 = new Dataset();
			dataset2.setLabel("Total WIP: " + memo);
		    CoresEnum cor2 = cores.get(4);
		    dataset2.setBackgroundColor(cor2.backgroundColor);
		    dataset2.setBorderColor(cor2.borderColor);
		    dataset2.setType("line");
		    ArrayList<Object> data2 = new ArrayList<>();
//		    for(int i=0;i<colunas.size();i++) {
//		    	data2.add(memo);
//		    }
		    dataset2.setData(data2);
		    datasets.add(dataset2);
		}
		
		grafico.setDatasets(datasets);
	    return grafico;
	}
	
	private HashMap<String, String> getColunas(Resposta resposta) {
		String url = "https://api.github.com/projects/" + resposta.getProjeto().getId()+ "/columns";
		ResponseEntity<String> colunas = getJson(url, resposta.getToken());
		HashMap<String, String> hmColunas = new HashMap<String, String>();
		if (colunas.getStatusCode().value() == 200) {
			ObjectMapper mapper = new ObjectMapper();
	        try {
	        	JsonNode node = mapper.readTree(colunas.getBody());
	        	if (node.isArray()) {
	        		for (Object obj : node) {
	        			JsonNode local = mapper.readTree(obj.toString());
	        			hmColunas.put(local.get("id").asText(), local.get("name").asText());
	    			}
	        	}
	        } catch (JsonProcessingException e) {
	            e.printStackTrace();
	        }
		}
		return hmColunas;
	}
	
	private ResponseEntity<String> getJson(String url, String token) {
		RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setBearerAuth(token);
		HttpEntity<String> request = new HttpEntity<String>(headers);
		ResponseEntity<String> response = restTemplate.exchange(
		    url,HttpMethod.GET, request, String.class, 1);
		return response;
	}
	
	private boolean verificaData(LocalDate data, LocalDate compara, Integer index, Resposta resposta) {
		var verifica = false;
		if (resposta.getPeriodicidade().getId().equals("1")) {
			if (index == 0) {
				verifica = data.isEqual(compara);
			} else {
				verifica = data.isBefore(compara) || data.isEqual(compara);
			}
		} else if (resposta.getPeriodicidade().getId().equals("2") || resposta.getPeriodicidade().getId().equals("3")) {	
				verifica = (data.isBefore(compara) || data.isEqual(compara));			
		} 
		return verifica;
	}
	
	private HashMap<String, LocalDate> getDates(Resposta resposta) {
		HashMap<String, LocalDate> dates = new HashMap<String, LocalDate>();
		LocalDate dt_inicial = LocalDate.ofInstant(resposta.getDt_inicial().toInstant(), ZoneId.systemDefault());
		LocalDate dt_final = null;
		if (resposta.getDt_final() == null) {
			dt_final = LocalDate.now();
		} else {
			dt_final = LocalDate.ofInstant(resposta.getDt_final().toInstant(), ZoneId.systemDefault());
		}
		if (resposta.getPeriodicidade().getId().equals("1")) {
			while (dt_inicial.isBefore(dt_final) || dt_inicial.isEqual(dt_final)) {
				dates.put(dt_inicial.getDayOfMonth() + "/" + dt_inicial.getMonthValue() + "/" + dt_inicial.getYear(), dt_inicial);
				dt_inicial = dt_inicial.plusDays(1);
			}
		} else if (resposta.getPeriodicidade().getId().equals("2")) {
			while (dt_inicial.isBefore(dt_final) || dt_inicial.isEqual(dt_final)) {
				dates.put(dt_inicial.getDayOfMonth() + "/" + dt_inicial.getMonthValue() + "/" + dt_inicial.getYear(), dt_inicial);
				dt_inicial = dt_inicial.plusWeeks(1);
			}
		} else if (resposta.getPeriodicidade().getId().equals("3")) {
			while (dt_inicial.isBefore(dt_final) || dt_inicial.isEqual(dt_final)) {
				TemporalAdjuster temporalAdjuster = TemporalAdjusters.lastDayOfMonth();
				dates.put(dt_inicial.getMonthValue() + "/" + dt_inicial.getYear(), dt_inicial.with(temporalAdjuster));
				dt_inicial = dt_inicial.plusMonths(1);
			}	
		}
		return getSort(dates);
	}
	
	private HashMap<String, LocalDate> getFechadas(Resposta resposta) {
		HashMap<String, LocalDate> dts = new HashMap<String, LocalDate>();
		String url = "https://api.github.com/repos/" + resposta.getUsuario() + "/" + resposta.getRepository().getName() + "/issues?state=closed";
		ResponseEntity<String> issues = getJson(url, resposta.getToken());
		ObjectMapper mapper = new ObjectMapper();
		try {
			JsonNode node = mapper.readTree(issues.getBody());
        	if (node.isArray()) {
        		for (Object obj : node) {
        			JsonNode local = mapper.readTree(obj.toString());
        			if (local.get("closed_at") != null) {
        				dts.put(local.get("number").asText(), LocalDate.parse(local.get("closed_at").asText().substring(0,10)));
        			}
    			}
        	}
		} catch (JsonProcessingException e) {
            e.printStackTrace();
        }
		return getSort(dts);
	}
	
	private HashMap<String, LocalDate> getSort(HashMap<String, LocalDate> hash) {
		return hash.entrySet()
                .stream()
                .sorted((i1, i2) -> i1.getValue().compareTo(i2.getValue()))
                .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
	}
	
	private Integer getIndex(String key, List<Coluna> colunas) {
		int index = 999;
		for (Coluna coluna : colunas) {
			if (coluna.getName().equals(key)) {
				index = coluna.getIndex();
				break;
			}
		}		
	    return index;			
	}
	
	public enum CoresEnum {
		AZUL("#1E90FF", "#1C86EE", "rgb(28,134,238,0.2)"),
		AMARELO("#FFFF00", "#EEEE00", "rgb(238,238,0,0.2)"),
		CINZA("#D3D3D3", "#BEBEBE", "rgb(190,190,190,0.2)"),
		VERMELHO("#FF0000", "#EE0000", "rgb(238,0,0,0.2)"),
		CIANO("#00FFFF", "#00EEEE", "rgb(0,238,238,0.2)"),
		VERDE("#9CCC65", "#7CB342", "rgba(128,179,66,0.2)"),
		SALMAO("#FF8C69", "#EE8262", "rgb(238,130,98,0.2)"),
		ROSA("#FF1493", "#EE1289", "rgb(238,18,137,0.2)"),
		LARANJA("#FF7F00", "#EE7600", "rgb(238,118,0,0.2)"),
		VIOLETA("#9932CC", "#9400D3", "rgb(148,0,211,0.2)");
		
		@Getter
		private String backgroundColor;
		@Getter
		private String borderColor;
		@Getter
		private String transpColor;
		
		CoresEnum(String backgroundColor, String borderColor, String transpColor) {
			this.backgroundColor = backgroundColor;
			this.borderColor = borderColor;
			this.transpColor = transpColor;
		}
		
	}

}
