package rest.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rest.service.model.DataResource;

public interface DataResourceRepository extends JpaRepository<DataResource, Long> {

}
