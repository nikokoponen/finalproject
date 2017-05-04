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
import fi.niko.workinghours.domain.CompanyRepository;
import fi.niko.workinghours.domain.DepartmentRepository;
import fi.niko.workinghours.domain.Employee;
import fi.niko.workinghours.domain.EmployeeRepository;

@Controller
public class CompanyController {

	@Autowired
	private CompanyRepository crepository;

	@RequestMapping(value = "/companies", method = RequestMethod.GET)
	public String getCompanies(Model model) {
		// Get and add companies into model
		model.addAttribute("companies", crepository.findAll());
		// Add new company object if user wants to add
		model.addAttribute("newcomp", new Company());
		return "companies";
	}

	@RequestMapping(value = "/compadd", method = RequestMethod.POST)
	public String createCompany(Company newcomp) {
		// Insert new company object into database
		crepository.save(newcomp);
		return "redirect:companies";
	}

	@RequestMapping(value = "/editcomp/{id}", method = RequestMethod.GET)
	public String company(@PathVariable("id") Long id, Model model) {
		// Get selected company by ID
		Company company = crepository.findOne(id);
		// Add company to model and return to view
		model.addAttribute("company", company);
		return "editcomp";
	}

	// Save company details
	@RequestMapping(value = "/compsave", method = RequestMethod.POST)
	public String save(Company company) {
		// Update the company
		crepository.save(company);
		return "redirect:companies";
	}

	// Delete company by ID
	@RequestMapping(value = "/delcomp/{id}", method = RequestMethod.GET)
	public String deleteCompany(@PathVariable("id") Long compId, Model model) {
		crepository.delete(compId);
		return "redirect:../companies";
	}

	// RESTful service to get company by id
	@RequestMapping(value = "/company/{id}", method = RequestMethod.GET)
	public @ResponseBody Company findCompanyRest(@PathVariable("id") Long companyId) {
		return crepository.findOne(companyId);
	}

	// RESTful service to get all companies
	@RequestMapping(value = "/companylist", method = RequestMethod.GET)
	public @ResponseBody List<Company> companyListRest() {
		return (List<Company>) crepository.findAll();
	}
}
