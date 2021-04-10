package rest.service.api;

import java.util.Collection;

import org.springframework.http.ResponseEntity;

import rest.service.exceptions.ResourceCreateFromFileException;
import rest.service.model.DataResource;

public interface ResourceFileUpdateController {
	
	ResponseEntity<Collection<DataResource>> createFromFile();
	
	ResponseEntity<Collection<DataResource>> createFromFile(String type) throws ResourceCreateFromFileException;

}
