/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.station.taxi.controller;

import com.station.taxi.db.repositories.ReceiptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author srgrn
 */
@Controller
@RequestMapping(value = "/reports")
public class Report {
    
    @Autowired
    private ReceiptRepository repository;
    
    @RequestMapping(value = "/")
    public String report(Model model)
    {
        model.addAttribute("receipts", repository.findAll());
        return "report";
    }
    @RequestMapping(value="/cab/{cabno}")
    public String reportByCab(Model model,@PathVariable int cabno)
    {
        model.addAttribute("receipts", repository.findBymCabID(cabno));
        return "report";
    }
    @RequestMapping(value="/count/{number}")
    public String reportByPassengerCount(Model model,@PathVariable int number)
    {
        model.addAttribute("receipts", repository.findBymPassengersCount(number));
        return "report";
    }
    
}
