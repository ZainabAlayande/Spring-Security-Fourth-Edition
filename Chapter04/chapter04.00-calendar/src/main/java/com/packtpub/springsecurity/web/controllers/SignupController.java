package com.packtpub.springsecurity.web.controllers;


import com.packtpub.springsecurity.domain.CalendarUser;
import com.packtpub.springsecurity.service.CalendarService;
import com.packtpub.springsecurity.service.UserContext;
import com.packtpub.springsecurity.web.model.SignupForm;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * The type Signup controller.
 */
@Controller
public class SignupController {
	/**
	 * The User context.
	 */
	private final UserContext userContext;

	/**
	 * The Calendar service.
	 */
	private final CalendarService calendarService;

	/**
	 * Instantiates a new Signup controller.
	 *
	 * @param userContext     the user context
	 * @param calendarService the calendar service
	 */
	@Autowired
	public SignupController(UserContext userContext, CalendarService calendarService) {
		if (userContext == null) {
			throw new IllegalArgumentException("userContext cannot be null");
		}
		if (calendarService == null) {
			throw new IllegalArgumentException("calendarService cannot be null");
		}
		this.userContext = userContext;
		this.calendarService = calendarService;
	}

	/**
	 * Signup string.
	 *
	 * @param signupForm the signup form
	 * @return the string
	 */
	@RequestMapping("/signup/form")
	public String signup(@ModelAttribute SignupForm signupForm) {
		return "signup/form";
	}

	/**
	 * Signup string.
	 *
	 * @param signupForm         the signup form
	 * @param result             the result
	 * @param redirectAttributes the redirect attributes
	 * @return the string
	 */
	@RequestMapping(value = "/signup/new", method = RequestMethod.POST)
	public String signup(@Valid SignupForm signupForm, BindingResult result, RedirectAttributes redirectAttributes) {
		if (result.hasErrors()) {
			return "signup/form";
		}

		String email = signupForm.getEmail();
		if (calendarService.findUserByEmail(email) != null) {
			result.rejectValue("email", "errors.signup.email", "Email address is already in use.");
			return "signup/form";
		}

		CalendarUser user = new CalendarUser();
		user.setEmail(email);
		user.setFirstName(signupForm.getFirstName());
		user.setLastName(signupForm.getLastName());
		user.setPassword(signupForm.getPassword());

		int id = calendarService.createUser(user);
		user.setId(id);
		userContext.setCurrentUser(user);

		redirectAttributes.addFlashAttribute("message", "You have successfully signed up and logged in.");
		return "redirect:/";
	}
}
