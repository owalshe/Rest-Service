package rest.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST) 
public class ResourceCreateFromFileException extends Exception {

	public ResourceCreateFromFileException(String msg) {
		super(msg);
	}
}
