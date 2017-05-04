package fi.niko.workinghours.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;


public interface CompanyRepository extends CrudRepository<Company, Long> {
	
	List<Company> findByName(String name);
	List<Company> findById(Long compId);
	
} 
