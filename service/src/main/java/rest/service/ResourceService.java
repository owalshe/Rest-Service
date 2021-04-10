package rest.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import rest.service.exceptions.ResourceAlreadyExistsException;
import rest.service.exceptions.ResourceCreateFromFileException;
import rest.service.exceptions.ResourceNotFoundException;
import rest.service.model.DataResource;
import rest.service.repository.DataResourceRepository;
import rest.service.response.DeleteResponse;
import rest.service.response.UpdateResponse;

@Service
public class ResourceService {

	private static final Logger LOG = LoggerFactory.getLogger(ResourceService.class);
	
	@Autowired
	private DataResourceRepository repository;
	
	@Autowired
	private DataFactory dataFactory;
	
	public DataResource getResource(Long id) throws ResourceNotFoundException {
		Optional<DataResource> optional = this.repository.findById(id);
		if(optional.isPresent()) {
			DataResource dataResource = optional.get();
			LOG.info(String.format("found resource %s", dataResource.toString()));
			return dataResource;
		}
		throw new ResourceNotFoundException(String.format("Unable to find resource for id %d", id));
	}
	
	public List<DataResource> getAllResources() {
		return this.repository.findAll();
	}
	
	public DataResource createResource(DataResource dataResource) throws ResourceAlreadyExistsException {
		Example<DataResource> example = Example.of(dataResource);
	    Optional<DataResource> optional = repository.findOne(example);
	    if(optional.isPresent()) {
			throw new ResourceAlreadyExistsException(String.format("Resource already exists for %s", dataResource));
		}
	    DataResource newResource = this.repository.save(dataResource);
	    LOG.info(String.format("created resource %s", newResource.toString()));
		return newResource;
	}
	
	public UpdateResponse updateResource(Long id, DataResource dataResource) {
		Optional<DataResource> optional = this.repository.findById(id);
		if(optional.isPresent()) {
			DataResource existingResource = optional.get();
			existingResource.setData1(dataResource.getData1());
			existingResource.setData2(dataResource.getData2());
			existingResource.setData3(dataResource.getData3());
			DataResource updatedResource = this.repository.save(existingResource);
			LOG.info(String.format("updated resource %s", updatedResource.toString()));
			return new UpdateResponse(updatedResource, true);
		}
		DataResource newResource = this.repository.save(dataResource);
		LOG.info(String.format("created resource %s", newResource.toString()));
		return new UpdateResponse(newResource, false);
	}

	public DeleteResponse deleteResource(Long id) {
		boolean exists = this.repository.existsById(id);
		if(exists) {
			this.repository.deleteById(id);
			LOG.info(String.format("deleted resource with id %d", id));
			return new DeleteResponse(true);
		}
		LOG.info(String.format("unable to find resource by id %d", id));
		return new DeleteResponse(false);
	}
	
	public Collection<DataResource> createResourcesFromFile() {
		List<DataResource> savedResources = this.repository.saveAll(dataFactory.getAllData());
		LOG.info("created resources from file");
		return savedResources;
	}
	
	public Collection<DataResource> createResourcesFromFile(String type) throws ResourceCreateFromFileException {
		if(Rows.ODD.name().equalsIgnoreCase(type)) {
			List<DataResource> savedResources = this.repository.saveAll(dataFactory.getOddRowNumberData());
			LOG.info("created odd row resources from file");
			return savedResources;
		} else if(Rows.EVEN.name().equalsIgnoreCase(type)) {
			List<DataResource> savedResources = this.repository.saveAll(dataFactory.getEvenRowNumberData());
			LOG.info("created even row resources from file");
			return savedResources;
		}
		throw new ResourceCreateFromFileException(String.format("Unknown file update paramater %s", type));
	}
}
