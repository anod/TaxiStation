/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.station.taxi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author srgrn
 */
@Controller
public class taxi {
    
    @RequestMapping("/taxi")
    public @ResponseBody String taxi() {
		return "here will be a taxi page";
	}
}
