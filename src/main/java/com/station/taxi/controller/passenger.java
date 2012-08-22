/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.station.taxi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author srgrn
 */
@Controller
@RequestMapping(value="/passenger")
public class passenger {
    @RequestMapping(value="/")
    public @ResponseBody String passenger() {
		return "here will be passanger page";
	}
    @RequestMapping(value="/{var}")
    public String SpecificPassenger(@PathVariable String var,Model model) {
            model.addAttribute("name", var);
            return "passengerdetails";
	}
}
