package fi.niko.workinghours;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

import fi.niko.workinghours.domain.Company;
import fi.niko.workinghours.domain.CompanyRepository;
import fi.niko.workinghours.domain.Department;
import fi.niko.workinghours.domain.DepartmentRepository;
import fi.niko.workinghours.domain.EmployeeRepository;
import fi.niko.workinghours.domain.ProjectMemberRepository;
import fi.niko.workinghours.domain.ProjectRepository;
import fi.niko.workinghours.domain.ProjectWorkhoursRepository;
import fi.niko.workinghours.domain.Project_workhours;

@RunWith(SpringRunner.class)
//@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class RepositoryTests {
	@Autowired
	private CompanyRepository crepository;

	@Autowired
	private DepartmentRepository drepository;
	
	//Company repository tests
	@Test
	public void createCompany(){
		
		Company company = new Company();
		company.setName("test comp");
		crepository.save(company);
	}
	@Test
	public void findCompanyByName(){
		
		List<Company> comp = crepository.findByName("Nikoworks");
		assertThat(comp).hasSize(1);
	}
	@Test
	public void findCompanyById(){
		
		List<Company> comp = crepository.findById(1L);
		assertThat(comp).hasSize(1);
	}
	@Test
	public void updateComp(){
		Company comp = crepository.findOne(1L);
		assertThat(comp.getName()).isEqualTo("Nikoworks");
		comp.setName("Test comp");
		assertThat(comp.getName()).isEqualTo("Test comp");
		crepository.save(comp);
	}
	//Department repo tests
	@Test
	public void createDepartment(){
		
		Department dept = new Department();
		dept.setName("test dept");
		drepository.save(dept);
	}
	@Test
	public void findByDeptId(){
		List<Department>dept = drepository.findById(1L);
		assertThat(dept).hasSize(1);
	}
}
