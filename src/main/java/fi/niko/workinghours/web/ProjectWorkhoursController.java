package fi.niko.workinghours.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import fi.niko.workinghours.domain.Company;
import fi.niko.workinghours.domain.CompanyRepository;
import fi.niko.workinghours.domain.Department;
import fi.niko.workinghours.domain.DepartmentRepository;
import fi.niko.workinghours.domain.Employee;
import fi.niko.workinghours.domain.EmployeeRepository;
import fi.niko.workinghours.domain.ProjectRepository;
import fi.niko.workinghours.domain.ProjectWorkhoursRepository;
import fi.niko.workinghours.domain.Project_workhours;
import fi.niko.workinghours.domain.User;
import fi.niko.workinghours.domain.UserRepository;

@Controller
public class ProjectWorkhoursController {

	@Autowired
	private EmployeeRepository erepository;

	@Autowired
	private ProjectWorkhoursRepository pwhrepository;

	@Autowired
	private ProjectRepository prepository;

	@Autowired
	private UserRepository urepository;

	@RequestMapping(value = "/workhours", method = RequestMethod.GET)
	public String workhourslist(Model model) {

		// Get logged in username
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String name = auth.getName();
		// Get whole user object of logged in user
		User curruser = urepository.findByUsername(name);
		// Get employee details of logged in user
		Employee curremployee = erepository.findOne(curruser.getEmployee().getId());
		// Find project workhours of current logged in user.
		// FOR NOW -- company id, project id and employee id will be used in the form
		Project_workhours currpwh = pwhrepository.findOne(curremployee.getId());
		// Add currpwh to model and return to view
		model.addAttribute("currpwh", currpwh);

		model.addAttribute("workhours", pwhrepository.findAll());
		// Add new pwh object if user wants to add
		model.addAttribute("newpwh", new Project_workhours());
		// Get all projects and add to model
		model.addAttribute("projects", prepository.findAll());

		return "workhours";
	}

	@RequestMapping(value = "/pwhadd", method = RequestMethod.POST)
	public String createPWH(Project_workhours newpwh) {
		// Insert new pwh object into database
		pwhrepository.save(newpwh);
		return "redirect:workhours";
	}

	@RequestMapping(value = "/editpwh/{id}", method = RequestMethod.GET)
	public String pwh(@PathVariable("id") Long id, Model model) {
		// Get selected pwh by ID
		Project_workhours pwh = pwhrepository.findOne(id);
		// Add pwh to model and return to view
		model.addAttribute("pwh", pwh);
		return "editpwh";
	}

	// Save pwh details
	@RequestMapping(value = "/pwhsave", method = RequestMethod.POST)
	public String save(Project_workhours pwh) {
		// Update the pwh
		pwhrepository.save(pwh);
		return "redirect:workhours";
	}

	// Delete pwh by ID
	@RequestMapping(value = "/delpwh/{id}", method = RequestMethod.GET)
	public String deletePwh(@PathVariable("id") Long pwhId, Model model) {
		pwhrepository.delete(pwhId);
		return "redirect:../workhours";
	}

	// RESTful service to get workhours by id
	@RequestMapping(value = "/workhours/{id}", method = RequestMethod.GET)
	public @ResponseBody Project_workhours findProjectworkhoursRest(@PathVariable("id") Long pwhId) {
		return pwhrepository.findOne(pwhId);
	}

	// RESTful service to get all workhours
	@RequestMapping(value = "/workhourlist", method = RequestMethod.GET)
	public @ResponseBody List<Project_workhours> projectworkhourListRest() {
		return (List<Project_workhours>) pwhrepository.findAll();
	}
}
