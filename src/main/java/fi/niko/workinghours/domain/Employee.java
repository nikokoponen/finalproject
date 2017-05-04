package fi.niko.workinghours.domain;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;



@Entity
/*@NamedQuery(name = "Employee.findEmployeesNotInProject",
query = "select e from Employee e where id not in(select p from Project_member p where company_id = ?1)")*/
public class Employee {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String ssn;
	private String firstName;
	private String lastName;
	private String gender;
	private String email;
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name="company_id")
	private Company company;
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name="department_id")
	private Department department;
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL,mappedBy="employee")
	private List<User> users;
	
	public Employee(){}
	
	public Employee(String ssn, String firstName, String lastName, String gender, String email, Company company, Department department){
		super();
		this.ssn = ssn;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.email = email;
		this.company = company;
		this.department = department;
	}
	
	public String getSsn() {
		return ssn;
	}
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
}
