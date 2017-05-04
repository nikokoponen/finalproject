package fi.niko.workinghours.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Project_member {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name="employee_id")
	private Employee employee;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name="company_id")
	private Company company;
	
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name="project_id")
	private Project project;
	
	public Project_member(){}
	
	public Project_member(Employee employee, Company company, Project project){
		
		this.setEmployee(employee);
		this.setCompany(company);
		this.setProject(project);
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
