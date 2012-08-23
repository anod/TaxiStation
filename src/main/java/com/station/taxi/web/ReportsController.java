/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.station.taxi.web;

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
public class ReportsController {
	private static final String TPL_REPORT = "report";
    
    @Autowired
    private ReceiptRepository repository;
    
    @RequestMapping(value = "/admin/reports")
    public String report(Model model)
    {
        model.addAttribute("receipts", repository.findAll());
        return TPL_REPORT;
    }
    @RequestMapping(value="/admin/reports/cab/{cabno}")
    public String reportByCab(Model model,@PathVariable int cabno)
    {
        model.addAttribute("receipts", repository.findByCabID(cabno));
        return TPL_REPORT;
    }
    @RequestMapping(value="/admin/reports/count/{number}")
    public String reportByPassengerCount(Model model,@PathVariable int number)
    {
        model.addAttribute("receipts", repository.findByPassengersCount(number));
        return TPL_REPORT;
    }
    @RequestMapping(value = "/admin/reports/from/{date}")
    public String reportFromDate(Model model,@PathVariable String date)
    {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        try {
            Date today = df.parse(date);
            model.addAttribute("receipts", repository.findAllinTimeRange(today));
        } catch (ParseException e) {
            e.printStackTrace();
        }
       
        return TPL_REPORT;
    }
    @RequestMapping(value = "/admin/reports/from/{date}/to/{date2}")
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
       
        return TPL_REPORT;
    }
}
