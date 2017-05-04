package fi.niko.workinghours.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface ProjectMemberRepository extends CrudRepository<Project_member, Long> {
	
	List<Project_member> findById(Long employeeid, Long companyid, Long projectid);
	List<Project_member> findByEmployeeId(Long employeeid);
	List<Project_member> findByProjectId(Long projectid);
	List<Project_member> findByCompanyId(Long companyid);
	
	
} 
