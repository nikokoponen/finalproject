package fi.niko.workinghours.web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import fi.niko.workinghours.domain.Department;
import fi.niko.workinghours.domain.EmployeeRepository;
import fi.niko.workinghours.domain.SignupForm;
import fi.niko.workinghours.domain.User;
import fi.niko.workinghours.domain.UserRepository;

@Controller
public class UserController {
	@Autowired
	private UserRepository repository;
	@Autowired
	private EmployeeRepository erepository;

	@RequestMapping(value = "/login")
	public String login() {
		return "login";
	}

	@RequestMapping(value = "/signup")
	public String addUser(Model model) {
		// Add signup form to model and return to view
		model.addAttribute("signupform", new SignupForm());
		// Adding list of users to form because user table has a relation to
		// employee table.
		// Logic is wrong. In real life creation of new accounts would be done differently
		// Now the user who is registering chooses who he/she is from the list
		// of users, which in real life would not be that way
		// Because of limited time the registration is implemented this way, if
		// there would be more time then different implementation would done
		model.addAttribute("employees", erepository.findAll());
		return "signup";
	}

	/**
	 * Create new user Check if user already exists & form validation
	 * 
	 * @param signupForm
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value = "saveuser", method = RequestMethod.POST)
	public String save(@Valid @ModelAttribute("signupform") SignupForm signupForm, BindingResult bindingResult) {
		if (!bindingResult.hasErrors()) { // validation errors
			if (signupForm.getPassword().equals(signupForm.getPasswordCheck())) { // check
																					// password
																					// match
				String pwd = signupForm.getPassword();
				BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
				String hashPwd = bc.encode(pwd);

				User newUser = new User();
				newUser.setEmployee(signupForm.getEmployee());
				newUser.setPasswordHash(hashPwd);
				newUser.setUsername(signupForm.getUsername());
				newUser.setRole("USER");
				if (repository.findByUsername(signupForm.getUsername()) == null) { // Check
																					// if
																					// user
																					// exists
					repository.save(newUser);
				} else {
					bindingResult.rejectValue("username", "err.username", "Username already exists");
					return "signup";
				}
			} else {
				bindingResult.rejectValue("passwordCheck", "err.passCheck", "Passwords does not match");
				return "signup";
			}
		} else {
			return "signup";
		}
		return "redirect:/login";
	}

	// RESTful service to get user by id
	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
	public @ResponseBody User findUserRest(@PathVariable("id") Long userId) {
		return repository.findOne(userId);
	}

	// RESTful service to get all users
	@RequestMapping(value = "/userlist", method = RequestMethod.GET)
	public @ResponseBody List<User> userListRest() {
		return (List<User>) repository.findAll();
	}
}
