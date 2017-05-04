package fi.niko.workinghours.web;

import java.util.ArrayList;
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

import fi.niko.workinghours.domain.CompanyRepository;
import fi.niko.workinghours.domain.Department;
import fi.niko.workinghours.domain.DepartmentRepository;
import fi.niko.workinghours.domain.Employee;
import fi.niko.workinghours.domain.EmployeeRepository;
import fi.niko.workinghours.domain.Project;
import fi.niko.workinghours.domain.ProjectMemberRepository;
import fi.niko.workinghours.domain.ProjectRepository;
import fi.niko.workinghours.domain.ProjectWorkhoursRepository;
import fi.niko.workinghours.domain.Project_member;
import fi.niko.workinghours.domain.User;
import fi.niko.workinghours.domain.UserRepository;

@Controller
public class ProjectController {
	@Autowired
	private ProjectRepository prepository;

	@Autowired
	private UserRepository urepository;

	@RequestMapping(value = "/projects", method = RequestMethod.GET)
	public String projectList(Model model) {
		// Get logged in user
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		// Get logged in username
		String name = auth.getName();
		// Get whole user object of logged in user
		User curruser = urepository.findByUsername(name);
		// Get role of current user
		String uRole = curruser.getRole();
		// Get all projects of company
		model.addAttribute("projects", prepository.findByCompanyId(curruser.getEmployee().getCompany().getId()));

		if (uRole.equals("ADMIN")) {

			// Add new project object if admin wants to add
			model.addAttribute("newproject", new Project());
			// Add company id to model, used to create new project
			Long companyId = curruser.getEmployee().getCompany().getId();
			model.addAttribute("companyId",companyId);

		}
		return "projects";
	}

	@RequestMapping(value = "/projectadd", method = RequestMethod.POST)
	public String createProject(Project newproject) {
		// Insert new project object into database
		prepository.save(newproject);
		return "redirect:projects";
	}

	@RequestMapping(value = "/editproject/{id}", method = RequestMethod.GET)
	public String project(@PathVariable("id") Long id, Model model) {
		// Get selected project by ID
		Project project = prepository.findOne(id);
		// Add project to model and return to view
		model.addAttribute("project", project);

		return "editproject";
	}

	// Save project details
	@RequestMapping(value = "/projectsave", method = RequestMethod.POST)
	public String save(Project project) {
		// Update the project 
		prepository.save(project);
		return "redirect:projects";
	}

	// Delete project by ID
	@RequestMapping(value = "/delproject/{id}", method = RequestMethod.GET)
	public String deleteProject(@PathVariable("id") Long projectId, Model model) {
		prepository.delete(projectId);
		return "redirect:../projects";
	}
	// RESTful service to get project by id
	@RequestMapping(value = "/project/{id}", method = RequestMethod.GET)
	public @ResponseBody Project findProjectRest(@PathVariable("id") Long projectId) {
		return prepository.findOne(projectId);
	}

	// RESTful service to get all projects
	@RequestMapping(value = "/projectlist", method = RequestMethod.GET)
	public @ResponseBody List<Project> projectListRest() {
		return (List<Project>) prepository.findAll();
	}
}
