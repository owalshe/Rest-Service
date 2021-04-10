package rest.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import rest.service.model.DataResource;

@Component
public class DataFactory {

	private static final String FILE_PATH = "PracticeFile.csv";
	
	private Map<Integer, DataResource> dataMap;
	
	@Autowired
	private DataReader dataReader;
	
	@PostConstruct
    public void init() throws Exception {
        this.dataMap = this.dataReader.readCSV(FILE_PATH);
    }
    
    public List<DataResource> getOddRowNumberData() {
    	return this.dataMap.entrySet().stream()
    			.filter(e -> e.getKey()%2!=0)
    			.map(Map.Entry::getValue)
    			.collect(Collectors.toList());
    }
    
    public List<DataResource> getEvenRowNumberData() {
    	return this.dataMap.entrySet().stream()
    			.filter(e -> e.getKey()%2==0)
    			.map(Map.Entry::getValue)
    			.collect(Collectors.toList());
    }
    
    public Collection<DataResource> getAllData() {
    	return this.dataMap.values();
    }
}
