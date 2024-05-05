package com.alekseyruban.barbershopServer.controllers;

import com.alekseyruban.barbershopServer.dto.MasterDTO;
import com.alekseyruban.barbershopServer.dto.TreatmentServiceDTO;
import com.alekseyruban.barbershopServer.entity.Master;
import com.alekseyruban.barbershopServer.entity.Record;
import com.alekseyruban.barbershopServer.entity.TreatmentService;
import com.alekseyruban.barbershopServer.enums.MasterQualification;
import com.alekseyruban.barbershopServer.service.MasterService;
import com.alekseyruban.barbershopServer.service.RecordService;
import com.alekseyruban.barbershopServer.service.TreatmentServiceService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;


@Controller
@RequestMapping({"/admin/", "/admin"})
@AllArgsConstructor
public class AdminController {

    private final MasterService masterService;
    private final TreatmentServiceService treatmentServiceService;
    private final RecordService recordService;

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

    @RequestMapping(value = {"/services", "/services/"}, method = RequestMethod.POST)
    public ResponseEntity<TreatmentService> createService(@RequestParam String name, @RequestParam Integer coast,
                                                          @RequestParam Integer duration) {
        TreatmentServiceDTO treatmentServiceDTO = TreatmentServiceDTO.builder()
                .name(name)
                .coast(coast)
                .duration(duration)
                .build();

        return new ResponseEntity<>(treatmentServiceService.create(treatmentServiceDTO), HttpStatus.OK);
    }

    @RequestMapping(value = {"/services", "/services/"}, method = RequestMethod.GET)
    public String getServiceId(@RequestParam(value = "name", required = false) String name, Model model) {
        if (name == null) {
            model.addAttribute("answer", "Здесь появится ID");
        } else {
            Long serviceId = treatmentServiceService.getId(name);
            if (serviceId == null) {
                model.addAttribute("answer", "Такой услуги нет");
            } else {
                model.addAttribute("answer", "ID услуги: " + serviceId);
            }
        }
        return "admin/services";
    }

    @RequestMapping(value = {"/services", "/services/"}, method = RequestMethod.DELETE)
    public void deleteService(@RequestParam Long idService) {
        treatmentServiceService.delete(idService);
    }

    @RequestMapping(value = {"/records", "/records/"}, method = RequestMethod.PUT)
    public void closeRecord(@RequestParam Long recordId) {
        recordService.close(recordId);
    }

    @RequestMapping(value = {"/records", "/records/"}, method = RequestMethod.GET)
    public String getRecordId(@RequestParam(value = "clientEmail", required = false) String clientEmail,
                              @RequestParam(value = "masterName", required = false) String masterName,
                              @RequestParam(value = "recordTime", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime,
                              Model model) {

        if ((clientEmail == null && masterName == null) || dateTime == null) {
            model.addAttribute("recordId", "Здесь появится ID");
        } else {
            LocalDate date = dateTime.toLocalDate();
            LocalTime time = dateTime.toLocalTime();
            Long recordId = recordService.getId(clientEmail, masterName, date, time);
            if (recordId == null) {
                model.addAttribute("recordId","Такой записи нет");
            } else {
                model.addAttribute("recordId", "ID записи: " + recordId);
            }
        }

        return "admin/records";
    }

    @RequestMapping(value = {"/records", "/records/"}, method = RequestMethod.DELETE)
    public void deleteRecord(@RequestParam Long recordId) {
        recordService.delete(recordId);
    }

}
