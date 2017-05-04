package fi.niko.workinghours.domain;




import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
public class Project_workhours {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name="company_id")
	private Company company;
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name="project_id")
	private Project project;
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name="employee_id")
	private Employee employee;
	private String task;
	private Date date;
	private Double hours;
	
	public Project_workhours(){}
	
	public Project_workhours(Company company,Project project,Employee employee,String task,Date date,Double hours){
		
		this.company = company;
		this.project = project;
		this.employee = employee;
		this.task = task;
		this.date = date;
		this.hours = hours;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Double getHours() {
		return hours;
	}

	public void setHours(Double hours) {
		this.hours = hours;
	}

}
