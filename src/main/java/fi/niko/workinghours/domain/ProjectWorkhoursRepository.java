package fi.niko.workinghours.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ProjectWorkhoursRepository extends CrudRepository<Project_workhours, Long> {
	
	List<Project_workhours> findById(Long id);
	
} 
