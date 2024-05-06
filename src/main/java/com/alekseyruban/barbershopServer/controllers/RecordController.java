package com.alekseyruban.barbershopServer.controllers;

import com.alekseyruban.barbershopServer.dto.RecordDTO;
import com.alekseyruban.barbershopServer.entity.Client;
import com.alekseyruban.barbershopServer.entity.Master;
import com.alekseyruban.barbershopServer.entity.Record;
import com.alekseyruban.barbershopServer.entity.TreatmentService;
import com.alekseyruban.barbershopServer.security.CustomUserDetails;
import com.alekseyruban.barbershopServer.service.ClientService;
import com.alekseyruban.barbershopServer.service.MasterService;
import com.alekseyruban.barbershopServer.service.RecordService;
import com.alekseyruban.barbershopServer.service.TreatmentServiceService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping({"/record/", "/record"})
@AllArgsConstructor
public class RecordController {

    private final ClientService clientService;
    private final MasterService masterService;
    private final TreatmentServiceService treatmentServiceService;
    private final RecordService recordService;

    @GetMapping({"", "/"})
    public String defaulPage() {
        return "redirect:/record/select-master";
    }

    @GetMapping({"/start", "/start/"})
    public String start() {
        Long userId = null;
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof CustomUserDetails userDetails) {
                userId = userDetails.getId();
            }
        }
        if (userId == null) {
            return "redirect:/authorization/signin";
        }
        List<Record> recordList = recordService.readByClientIdAndIsDone(userId, false).stream().filter(r -> r.getDate().atTime(r.getTime()).compareTo(LocalDateTime.now()) >= 0).toList();
        if (recordList.size() > 0) {
            return "redirect:/authorization/account";
        }
        return "redirect:/record/select-master";
    }


    @GetMapping({"/select-master", "/select-master/"})
    public String masterPage(Model model) {
        Iterable<Master> masters = masterService.readAll();
        model.addAttribute("masters", masters);
        return "creating-record/select-master";
    }

    @GetMapping({"/select-services", "/select-services/"})
    public String servicesPage(Model model) {
        Iterable<TreatmentService> services = treatmentServiceService.readAll();
        model.addAttribute("services", services);
        return "creating-record/select-service";
    }

    @GetMapping({"/select-date", "/select-date/"})
    public String datePage(@RequestParam("master-id") Long masterId, Model model) {
        Map<LocalDate, LocalTime[]> schedule = new HashMap<>();

        LocalDate today = LocalDate.now();
        LocalDate start = LocalDate.of(today.getYear(), today.getMonthValue() - 1, 22);
        LocalDate end = LocalDate.of(today.getYear(), today.getMonthValue() + 1, 7);

        if (masterId == null) {
            while (!start.isEqual(end)) {
                LocalDate finalStart = start;
                List<LocalTime> timesAvailable = new ArrayList<>();
                LocalTime currentTime = LocalTime.of(9, 0);
                for (int i = 0; i < 24; i++) {
                    timesAvailable.add(LocalTime.from(currentTime));
                    currentTime = currentTime.plusMinutes(30);
                }
                schedule.put(finalStart, timesAvailable.toArray(new LocalTime[0]));
                start = start.plusDays(1);
            }
            model.addAttribute("schedule", schedule);

            return "creating-record/select-date";
        }

        List<Record> recordList = recordService.readInRangeForMaster(masterId, start, end);

        while (!start.isEqual(end)) {
            LocalDate finalStart = start;
            List<Record> recordsOfDay = recordList.stream().filter(r -> r.getDate().isEqual(finalStart)).toList();
            List<LocalTime> timesAvailable = new ArrayList<>();
            LocalTime currentTime = LocalTime.of(9, 0);
            LocalTime busyUpTo = LocalTime.of(8, 0);
            for (int i = 0; i < 24; i++) {
                LocalTime finalCurrentTime = currentTime;
                List<Record> currentTimeRecords = recordsOfDay.stream().filter(r -> r.getTime().compareTo(finalCurrentTime) == 0).toList();
                if (currentTimeRecords.size() > 0) {
                    Record currentTimeRecord = currentTimeRecords.get(0);
                    int duration = currentTimeRecord.getServices().stream().mapToInt(TreatmentService::getDuration).sum();
                    busyUpTo = currentTime.plusMinutes(duration);
                }

                if (currentTime.compareTo(busyUpTo) < 0 || (currentTime.compareTo(LocalTime.now()) < 0 && finalStart.isEqual(LocalDate.now()))) {
                    currentTime = currentTime.plusMinutes(30);
                    continue;
                } else {
                    timesAvailable.add(LocalTime.from(currentTime));
                }

                currentTime = currentTime.plusMinutes(30);
            }

            schedule.put(finalStart, timesAvailable.toArray(new LocalTime[0]));

            start = start.plusDays(1);
        }

        model.addAttribute("schedule", schedule);

        return "creating-record/select-date";
    }

    @RequestMapping(value = {"/save-record", "/save-record/"}, method = RequestMethod.POST)
    public ResponseEntity<Record> saveRecord(@RequestParam(value = "master-id") Long masterId,
                                             @RequestParam(value = "service-id") List<Long> serviceIds,
                                             @RequestParam(value = "date") String date,
                                             @RequestParam(value = "time") String time) {
        Long userId = null;
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof CustomUserDetails userDetails) {
                userId = userDetails.getId();
            }
        }
        Client client = clientService.getById(userId);
        Master master = masterService.getById(masterId);
        List<TreatmentService> services = treatmentServiceService.readAllById(serviceIds);
        RecordDTO recordDTO = RecordDTO.builder()
                .client(client)
                .master(master)
                .services(services)
                .date(LocalDate.parse(date))
                .time(LocalTime.parse(time))
                .isDone(false)
                .build();

        return new ResponseEntity<>(recordService.create(recordDTO), HttpStatus.OK);
    }

    @RequestMapping(value = {"/delete-record", "/delete-record/"}, method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteRecord(@RequestParam(value = "record-id") Long recordId) {
        recordService.delete(recordId);
        return ResponseEntity.ok("Record was deleted");
    }

}
