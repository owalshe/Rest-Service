package rest.service.response;

import rest.service.model.DataResource;

public class UpdateResponse {

	private DataResource resource;
	private boolean isUpdated;
	
	public UpdateResponse(DataResource resource, boolean isUpdated) {
		this.resource = resource;
		this.isUpdated = isUpdated;
	}

	public DataResource getResource() {
		return resource;
	}

	public boolean isUpdated() {
		return isUpdated;
	}
}
