package rest.service.api;

import java.util.Collection;

import org.springframework.http.ResponseEntity;

import rest.service.exceptions.ResourceAlreadyExistsException;
import rest.service.exceptions.ResourceNotFoundException;
import rest.service.model.DataResource;

public interface ResourceCrudController {

	ResponseEntity<DataResource> create(DataResource dataResource) throws ResourceAlreadyExistsException;
	
	ResponseEntity<DataResource> read(Long id) throws ResourceNotFoundException;
	
	ResponseEntity<Collection<DataResource>> readAll();

	ResponseEntity<DataResource> update(Long id, DataResource dataResource);

	ResponseEntity<String> delete(Long id);
}
