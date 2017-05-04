package fi.niko.workinghours.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import fi.niko.workinghours.domain.CompanyRepository;
import fi.niko.workinghours.domain.Department;
import fi.niko.workinghours.domain.DepartmentRepository;
import fi.niko.workinghours.domain.Employee;
import fi.niko.workinghours.domain.EmployeeRepository;


@Controller
public class EmployeeController {
	
	@Autowired
	private CompanyRepository crepository;
	
	@Autowired
	private DepartmentRepository drepository;
	
	@Autowired
	private EmployeeRepository erepository;
	
	@RequestMapping(value="/employees", method=RequestMethod.GET)
    public String employeeList(Model model) {	
      
        //Get and add employees to model and return to view
        model.addAttribute("employees", erepository.findAll());
        //Add new employee object if user wants to add
        model.addAttribute("newemp", new Employee());
        //Add companies to model
        model.addAttribute("companies", crepository.findAll());
        //Add departments to model
        model.addAttribute("departments",drepository.findAll());
        return "employees";
    }
	@RequestMapping(value="/empadd", method=RequestMethod.POST)
	public String createEmployee(Employee newemp){
		//Insert new employee object into database
		erepository.save(newemp);
		return "redirect:employees";
	}
	@RequestMapping(value="/editemp/{id}",method=RequestMethod.GET)
	public String employee(@PathVariable("id") Long id, Model model)	{
		//Get selected employee by ID
		Employee employee = erepository.findOne(id);
		//Add employee to model and return to view
		model.addAttribute("employee",employee);
		return "editemp";
	}
	//Save employee details
	@RequestMapping(value="/empsave", method=RequestMethod.POST)
	public String save(Employee employee){
		//Update the employee
		erepository.save(employee);
		return "redirect:employees";
	}
	//Delete employee by ID
	@RequestMapping(value="/delemp/{id}",method=RequestMethod.GET)
	public String deleteEmployee(@PathVariable("id") Long empId, Model model){
		erepository.delete(empId);
		return "redirect:../employees";
	}
	
	// RESTful service to get employee by id
	@RequestMapping(value = "/employee/{id}", method = RequestMethod.GET)
	public @ResponseBody Employee findEmployeeRest(@PathVariable("id") Long empId) {
		return erepository.findOne(empId);
	}

	// RESTful service to get all employees
	@RequestMapping(value = "/employeelist", method = RequestMethod.GET)
	public @ResponseBody List<Employee> employeeListRest() {
		return (List<Employee>) erepository.findAll();
	}
}
