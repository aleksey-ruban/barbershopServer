package com.alekseyruban.barbershopServer.controllers;

import com.alekseyruban.barbershopServer.MasterTEMP;
import com.alekseyruban.barbershopServer.ServiceTEMP;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/record/")
public class RecordController {

    @GetMapping({"/select-master", "/select-master/"})
    public String masterPage(Model model) {

        return "creating-record/select-master";
    }

    @GetMapping({"/select-services", "/select-services/"})
    public String servicesPage(Model model) {
        
        return "creating-record/select-service";
    }

    @GetMapping({"/select-date", "/select-date/"})
    public String datePage() {
        return "creating-record/select-date";
    }

}