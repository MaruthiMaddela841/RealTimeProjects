package in.ineuron.controller;

import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import in.ineuron.entities.Role;
import in.ineuron.entities.User;
import in.ineuron.entities.UserBooked;
import in.ineuron.services.EmailService;
import in.ineuron.services.MyUserDetailsService;
import in.ineuron.services.RoleService;
import in.ineuron.services.UserBookedService;
import in.ineuron.services.UserService;

@RestController
@RequestMapping("/user")
@SessionAttributes({ "name", "id", "role", "email" })
public class IndexController {
	@Autowired
	private RoleService role_service;
	@Autowired
	private UserService userService;
	@Autowired
	private EmailService eservice;
	@Autowired
	private MyUserDetailsService ser;
	@Autowired
	private UserBookedService bservice;

	@GetMapping
	public ModelAndView showIndex(ModelAndView model) {
		System.out.println("IndexController.showIndex()");
		User user = new User();
		model.addObject("user", user);
		List<Role> list = role_service.getRoles();
		model.addObject("list", list);
		model.setViewName("Registration");
		return model;
	}

	@PostMapping("/save")
	public ModelAndView saveUser(@ModelAttribute("user") User user, ModelAndView model)
			throws AddressException, MessagingException {
		String pass = userService.PasswordEncode(user.getPassword());
		user.setPassword(pass);

		String token = userService.generate();
		eservice.sendmail(user.getName(), user.getEmail(), token);
		user.setToken(token);
		userService.save(user);
		model.setViewName("redirect:/login");
		return model;
	}

	@GetMapping("/login")
	public ModelAndView login(ModelAndView model) {
		System.out.println("in login");
		return model;
	}

	@GetMapping("/booked")
	public ModelAndView getBookedSeats(ModelAndView model, ModelMap map) {
		int id = (int) map.get("id");
		List<UserBooked> list = bservice.getAll(id);
		model.addObject("list", list);
		model.setViewName("BookedSeats");
		System.out.println(list.size());
		return model;
	}

	@GetMapping("/list")
	public ModelAndView getAll(ModelAndView model) {
		List<User> list = userService.getAll();
		model.addObject("list", list);
		model.setViewName("listusers");
		return model;
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/get/{id}")
	public ModelAndView getUser(@PathVariable("id") int id, ModelAndView model) {
		User user = userService.getUserById(id);
		model.addObject("user", user);
		model.setViewName("update");
		return model;
	}

	@PreAuthorize("hasAnyRole('ROLE_ADMIN')")
	@GetMapping("/delete/{id}")
	public ModelAndView delete(@PathVariable("id") int id, ModelAndView model) {
		userService.delete(id);
		model.setViewName("redirect:/register/list");
		return model;
	}
}
