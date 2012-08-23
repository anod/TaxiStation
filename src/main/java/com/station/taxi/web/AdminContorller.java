package com.station.taxi.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author alex
 */
@Controller
public class AdminContorller {
	private static final String TPL_ADMIN = "admin";

	@RequestMapping(value = "/admin")
	public String admin(Model model) {
		return TPL_ADMIN;	
	}
	

}
