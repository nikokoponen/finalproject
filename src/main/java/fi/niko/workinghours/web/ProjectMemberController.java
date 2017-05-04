package fi.niko.workinghours.web;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
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
import fi.niko.workinghours.domain.ProjectMemberRepository;
import fi.niko.workinghours.domain.ProjectRepository;
import fi.niko.workinghours.domain.ProjectWorkhoursRepository;
import fi.niko.workinghours.domain.Project_member;
import fi.niko.workinghours.domain.Project_workhours;
import fi.niko.workinghours.domain.User;
import fi.niko.workinghours.domain.UserRepository;

@Controller
public class ProjectMemberController {

	@Autowired
	private EmployeeRepository erepository;

	@Autowired
	private ProjectRepository prepository;

	@Autowired
	private UserRepository urepository;

	@Autowired
	private ProjectMemberRepository pmrepository;

	@RequestMapping(value = "/projectmembers", method = RequestMethod.GET)
	public String projectMemberList(Model model) {
		// Get logged in user
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		// Get logged in username
		String name = auth.getName();
		// Get whole user object of logged in user
		User curruser = urepository.findByUsername(name);
		// Get role of current user
		String uRole = curruser.getRole();
		// Add new project member object if admin wants to add
		model.addAttribute("newpmember", new Project_member());
		// If user is admin get all project members
		if (uRole.equals("ADMIN")) {
			// Get all project members
			model.addAttribute("pmembers", pmrepository.findAll());

			// Get all ids of project members
			List<Project_member> prmmembers = pmrepository.findByCompanyId(curruser.getEmployee().getCompany().getId());
			// Get all employees of the company that are not in project
			List<Long> ids = new ArrayList<Long>();
			for (int i = 0; i < prmmembers.size(); i++) {
				ids.add(prmmembers.get(i).getEmployee().getId());
			}
			model.addAttribute("employees", erepository.findByIdNotIn(ids));

			// Get projects of company
			model.addAttribute("projects", prepository.findByCompanyId(curruser.getEmployee().getCompany().getId()));
			//Add employee to model and use company id in the form
			Employee employee = erepository.findOne(curruser.getEmployee().getId());
			model.addAttribute("employee", employee);
		}
		// Else get user's own projects where he/she is member
		else {
			// Get employee id of logged in user
			Long curremployeeid = curruser.getEmployee().getId();
			// Find projects where user is member and add to model
			model.addAttribute("pmembers", pmrepository.findByEmployeeId(curremployeeid));
		}

		return "projectmembers";
	}

	@RequestMapping(value = "/pmemberadd", method = RequestMethod.POST)
	public String createProjectMember(Project_member newpmember) {
		// Insert new project member object into database
		pmrepository.save(newpmember);
		return "redirect:projectmembers";
	}

	@RequestMapping(value = "/editpmember/{id}", method = RequestMethod.GET)
	public String pmember(@PathVariable("id") Long id, Model model) {
		// Get selected projectmember by ID
		Project_member pmember = pmrepository.findOne(id);
		// Add project member to model and return to view
		model.addAttribute("pmember", pmember);
		//Add projects of company to model
		model.addAttribute("projects", prepository.findByCompanyId(pmember.getCompany().getId()));
		
		return "editpmember";
	}

	// Save project member details
	@RequestMapping(value = "/pmembersave", method = RequestMethod.POST)
	public String save(Project_member pmember) {
		// Update the project member
		pmrepository.save(pmember);
		return "redirect:projectmembers";
	}

	// Delete project member by ID
	@RequestMapping(value = "/delpmember/{id}", method = RequestMethod.GET)
	public String deleteProjectMember(@PathVariable("id") Long pmemberId, Model model) {
		pmrepository.delete(pmemberId);
		return "redirect:../projectmembers";
	}
	// RESTful service to get project member by id
	@RequestMapping(value = "/projectmember/{id}", method = RequestMethod.GET)
	public @ResponseBody Project_member findProjectmemberRest(@PathVariable("id") Long pmId) {
		return pmrepository.findOne(pmId);
	}

	// RESTful service to get all project members
	@RequestMapping(value = "/projectmemberlist", method = RequestMethod.GET)
	public @ResponseBody List<Project_member> projectmemberListRest() {
		return (List<Project_member>) pmrepository.findAll();
	}
}
