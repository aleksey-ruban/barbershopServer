package com.alekseyruban.barbershopServer.controllers;

import com.alekseyruban.barbershopServer.dto.MasterDTO;
import com.alekseyruban.barbershopServer.entity.Master;
import com.alekseyruban.barbershopServer.enums.MasterQualification;
import com.alekseyruban.barbershopServer.service.MasterService;
import jakarta.websocket.server.PathParam;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.Method;

@Controller
@RequestMapping({"/admin/", "/admin"})
@AllArgsConstructor
public class AdminController {

    private final MasterService masterService;

    @GetMapping({"", "/"})
    public String defaultPage () {
        return "redirect:/admin/records";
    }

    @RequestMapping(value = {"/masters", "/masters/"}, method = RequestMethod.POST)
    public ResponseEntity<Master> createMaster(@RequestParam String name,
                                               @RequestParam String qualification) {
        MasterQualification masterQualification = MasterQualification.fromString(qualification);
        MasterDTO masterDTO = MasterDTO.builder()
                .name(name)
                .qualification(masterQualification)
                .build();

        return new ResponseEntity<>(masterService.create(masterDTO), HttpStatus.OK);
    }

    @RequestMapping(value = {"/masters", "/masters/"}, method = RequestMethod.GET)
    public String getMasterId(@RequestParam(value = "name", required = false) String name, Model model) {
        if (name == null) {
            model.addAttribute("answer", "Здесь появится ID");
        } else {
            Long masterId = masterService.getId(name);
            if (masterId == null) {
                model.addAttribute("answer", "Такого мастера нет");
            } else {
                model.addAttribute("answer", "ID мастера: " + masterId);
            }
        }
        return "admin/masters";
    }

    @RequestMapping(value = {"/masters", "/masters/"}, method = RequestMethod.DELETE)
    public void deleteMaster(@RequestParam Long idMaster) {
        masterService.delete(idMaster);
    }

    @GetMapping({"/services", "/services/"})
    public String services() {
        return "admin/services";
    }

    @GetMapping({"/records", "/records/"})
    public String records() {
        return "admin/records";
    }

}
