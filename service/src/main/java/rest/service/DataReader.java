package rest.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import rest.service.model.DataResource;

@Component
public class DataReader {

	public DataReader() {}

	public Map<Integer, DataResource> readCSV(String filePath) throws Exception {
		File file = getFile(filePath);
		InputStreamReader input = new InputStreamReader(new FileInputStream(file));
		CSVParser csvParser = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(input);
		Map<Integer, DataResource> dataMap = new HashMap<>();
		for (CSVRecord record : csvParser) {
			String field_1 = record.get("Row Number");
			String field_2 = record.get("Data1");
			String field_3 = record.get("Data2");
			String field_4 = record.get("Data3");
			dataMap.put(Integer.valueOf(field_1), new DataResource(field_2, field_3, field_4));
		}
		return dataMap;
	}

	private File getFile(String filePath) throws URISyntaxException {
		URL resource = getClass().getClassLoader().getResource("PracticeFile.csv");
		if (resource == null) {
			throw new IllegalArgumentException("file not found!");
		} else {
			return new File(resource.toURI());
		}
	}
}
