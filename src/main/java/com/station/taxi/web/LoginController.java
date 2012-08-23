package com.station.taxi.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author alex
 */
@Controller
public class LoginController {
	
	@RequestMapping("/login")
	public String login() {
		return "login";
	}

	@RequestMapping("/loginError")
	public String loginError() {
		return "loginError";
	}

}
