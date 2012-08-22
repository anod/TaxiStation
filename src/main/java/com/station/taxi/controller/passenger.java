/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.station.taxi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author srgrn
 */
@Controller
public class passenger {
    @RequestMapping(value="/passenger")
    public @ResponseBody String passenger() {
		return "here will be passanger page";
	}
    @RequestMapping(value="/passenger/{var}")
    public @ResponseBody String SpecificPassenger(@PathVariable String var) {
		return "requested passenger name '" + var + "'";
	}
}
