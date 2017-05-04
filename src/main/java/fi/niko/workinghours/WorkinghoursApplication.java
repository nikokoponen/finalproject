package fi.niko.workinghours;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import fi.niko.workinghours.domain.CompanyRepository;
import fi.niko.workinghours.domain.DepartmentRepository;
import fi.niko.workinghours.domain.EmployeeRepository;

@SpringBootApplication
public class WorkinghoursApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorkinghoursApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner bookstore(EmployeeRepository erepository,CompanyRepository crepository,DepartmentRepository drepository){
		return (args) -> {
			
		};
	}
}
