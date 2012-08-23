/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.station.taxi.controller;

import com.station.taxi.db.repositories.ReceiptRepository;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Date;
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
    @RequestMapping(value = "/from/{date}")
    public String reportFromDate(Model model,@PathVariable String date)
    {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date today = df.parse(date);
            model.addAttribute("receipts", repository.findAllinTimeRange(today));
        } catch (ParseException e) {
            e.printStackTrace();
        }
       
        return "report";
    }
    @RequestMapping(value = "/from/{date}/to/{date2}")
    public String reportFromDateTo(Model model,@PathVariable String date,@PathVariable String date2)
    {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date start = df.parse(date);
            Date end = df.parse(date2);
            model.addAttribute("receipts", repository.findAllinTimeRange(start, end));
        } catch (ParseException e) {
            e.printStackTrace();
        }
       
        return "report";
    }
}
