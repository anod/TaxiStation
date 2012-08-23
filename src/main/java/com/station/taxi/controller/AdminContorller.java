package com.station.taxi.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

/**
 *
 * @author alex
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminContorller {

	@RequestMapping(value = "/")
	public String admin(Model model) {
		return "admin";	
	}
}
