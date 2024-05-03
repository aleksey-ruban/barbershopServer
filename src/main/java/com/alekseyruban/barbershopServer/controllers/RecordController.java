package com.alekseyruban.barbershopServer.controllers;

import com.alekseyruban.barbershopServer.MasterTEMP;
import com.alekseyruban.barbershopServer.ServiceTEMP;
import com.alekseyruban.barbershopServer.entity.Master;
import com.alekseyruban.barbershopServer.service.MasterService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/record/")
@AllArgsConstructor
public class RecordController {

    private final MasterService masterService;

    @GetMapping({"/select-master", "/select-master/"})
    public String masterPage(Model model) {
        Iterable<Master> masters = masterService.readAll();
        model.addAttribute("masters", masters);
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
