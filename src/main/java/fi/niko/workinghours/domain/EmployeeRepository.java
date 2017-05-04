package fi.niko.workinghours.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {
	
	List<Employee> findBySsn(String ssn);
	//Employee findEmployeesNotInProject();
	List<Employee> findByIdNotIn(List<Long> ids);
	
} 
