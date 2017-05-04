package fi.niko.workinghours.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import fi.niko.workinghours.domain.Company;
import fi.niko.workinghours.domain.Department;
import fi.niko.workinghours.domain.DepartmentRepository;

@Controller
public class DepartmentController {
	@Autowired
	private DepartmentRepository drepository;

	@RequestMapping(value = "/departments", method = RequestMethod.GET)
	public String getDepartments(Model model) {
		// Get and add departments into model
		model.addAttribute("departments", drepository.findAll());
		// Add new department object if user wants to add
		model.addAttribute("newdept", new Department());
		return "departments";
	}

	@RequestMapping(value = "/deptadd", method = RequestMethod.POST)
	public String createDepartment(Department newdept) {
		// Insert new department object into database
		drepository.save(newdept);
		return "redirect:departments";
	}

	// Delete department by ID
	@RequestMapping(value = "/deldept/{id}", method = RequestMethod.GET)
	public String deleteDepartment(@PathVariable("id") Long deptId, Model model) {
		drepository.delete(deptId);
		return "redirect:../departments";
	}
	//No edit possibility since there is no relation between company and department
	//In real life there would be a relation between departments and a company, but at first I forgot to make the relation and did not have time to do it again
	//For now assumption is that every company has all the departments that are in database. User can add new ones or delete.
	//Delete should not in this case be possible neither but I left that functionality.
	
	// RESTful service to get department by id
	@RequestMapping(value = "/department/{id}", method = RequestMethod.GET)
	public @ResponseBody Department findDepartmentRest(@PathVariable("id") Long deptId) {
		return drepository.findOne(deptId);
	}

	// RESTful service to get all departments
	@RequestMapping(value = "/departmentlist", method = RequestMethod.GET)
	public @ResponseBody List<Department> departmentListRest() {
		return (List<Department>) drepository.findAll();
	}
}
