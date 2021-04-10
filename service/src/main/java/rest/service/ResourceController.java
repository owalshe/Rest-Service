package rest.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import rest.service.api.ResourceCrudController;
import rest.service.api.ResourceFileUpdateController;
import rest.service.exceptions.ResourceAlreadyExistsException;
import rest.service.exceptions.ResourceCreateFromFileException;
import rest.service.exceptions.ResourceNotFoundException;
import rest.service.model.DataResource;
import rest.service.response.DeleteResponse;
import rest.service.response.UpdateResponse;

@RestController
public class ResourceController implements ResourceCrudController, ResourceFileUpdateController {

	@Autowired
	private ResourceService service;
	
	@PostMapping(value = "/data", produces = "application/json")
	public @ResponseBody ResponseEntity<DataResource> create(@RequestBody DataResource dataResource) throws ResourceAlreadyExistsException {
		return new ResponseEntity<>(this.service.createResource(dataResource), HttpStatus.CREATED);
	}
	
	@GetMapping(value = "/data/{id}", produces = "application/json")
	@Override
	public @ResponseBody ResponseEntity<DataResource> read(@PathVariable Long id) throws ResourceNotFoundException {
		return new ResponseEntity<>(this.service.getResource(id), HttpStatus.OK);
	}
	
	@GetMapping(value = "/data", produces = "application/json")
	@Override
	public @ResponseBody ResponseEntity<Collection<DataResource>> readAll() {
		return new ResponseEntity<>(this.service.getAllResources(), HttpStatus.OK);
	}
	
	@PutMapping(value = "/data/{id}", produces = "application/json")
	@Override
	public @ResponseBody ResponseEntity<DataResource> update(@PathVariable Long id, @RequestBody DataResource dataResource) {
		UpdateResponse response = this.service.updateResource(id, dataResource);
		if(response.isUpdated()) {
			return new ResponseEntity<>(response.getResource(), HttpStatus.OK);
		}
		return new ResponseEntity<>(response.getResource(), HttpStatus.CREATED);
	}
	
	@DeleteMapping(value = "/data/{id}", produces = "application/json")
	@Override
	public @ResponseBody ResponseEntity<String> delete(@PathVariable Long id) {
		DeleteResponse deleteReponse = this.service.deleteResource(id);
		if(deleteReponse.isDeleted()) {
			return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>("", HttpStatus.NOT_FOUND);
	}
	
	@PostMapping(value = "/data/file", produces = "application/json")
	@Override
	public @ResponseBody ResponseEntity<Collection<DataResource>> createFromFile() {
		return new ResponseEntity<>(this.service.createResourcesFromFile(), HttpStatus.CREATED);
	}
	
	@PostMapping(value = "/data/file/{type}", produces = "application/json")
	@Override
	public @ResponseBody ResponseEntity<Collection<DataResource>> createFromFile(@PathVariable String type) throws ResourceCreateFromFileException {
		return new ResponseEntity<>(this.service.createResourcesFromFile(type), HttpStatus.CREATED);
	}
}
