package com.alekseyruban.barbershopServer.controllers;

import com.alekseyruban.barbershopServer.dto.AuthTokenDTO;
import com.alekseyruban.barbershopServer.dto.ClientDTO;
import com.alekseyruban.barbershopServer.entity.AuthorizationToken;
import com.alekseyruban.barbershopServer.entity.Client;
import com.alekseyruban.barbershopServer.entity.Record;
import com.alekseyruban.barbershopServer.entity.TreatmentService;
import com.alekseyruban.barbershopServer.helpers.EmailWorker;
import com.alekseyruban.barbershopServer.service.AuthTokenService;
import com.alekseyruban.barbershopServer.service.ClientService;
import com.alekseyruban.barbershopServer.service.RecordService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping({"/authorization/", "/authorization"})
@AllArgsConstructor
public class AuthorizationController {

    private final ClientService clientService;
    private final AuthTokenService authTokenService;
    private final RecordService recordService;

    @GetMapping({"", "/"})
    public String defaultPage() {
        return "redirect:/authorization/signin";
    }

    @GetMapping({"/signup", "/signup/"})
    public String signup() {
        return "authorization/signup";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String create(@RequestParam String name,@RequestParam String email,
                         @RequestParam String password) {
        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setName(name);
        clientDTO.setEmail(email);
        clientDTO.setPassword(password);
        Client client = clientService.create(clientDTO);

        AuthTokenDTO tokenDTO = new AuthTokenDTO();
        tokenDTO.setClient(client);
        authTokenService.createToken(tokenDTO);

        return "redirect:signin";
    }

    @GetMapping(value = "/confirm-email/{token}")
    public String confirmEmail(@PathVariable String token) {
        AuthTokenDTO tokenDTO = AuthTokenDTO.builder()
                .token(token)
                .build();

        AuthorizationToken authorizationToken = authTokenService.confirmEmail(tokenDTO);

        if (authorizationToken == null) {
            return null;
        }

        Client client = clientService.confirmEmail(authorizationToken.getClient());

        authTokenService.deleteToken(authorizationToken.getId());

        EmailWorker.successEmail(client.getEmail());

        return "redirect:/authorization/signin";
    }

    @GetMapping({"/signin", "/signin/"})
    public String signin() {
        return "authorization/signin";
    }

    @GetMapping({"/restore-password", "/restore-password/"})
    public String restorePassword() {
        return "authorization/restore-password";
    }

    @RequestMapping(value = "/restore-password", method = RequestMethod.POST)
    public @ResponseBody HttpStatus restore(@RequestParam String email) {

        clientService.restorePassword(email);

        return HttpStatus.OK;
    }

    @GetMapping({"/account", "/account/"})
    public String account(Model model) {
        Long userId = 1L;
        Client client = clientService.getById(userId);

        model.addAttribute("name", client.getName());
        model.addAttribute("email", client.getEmail());

        List<Record> recordList = recordService.readByClientIdAndIsDone(userId, false).stream().filter(r -> r.getDate().atTime(r.getTime()).compareTo(LocalDateTime.now()) >= 0).toList();

        if (recordList.size() != 0) {

            Map<Integer, String> months = new HashMap<>(){{
                put(1, "Января");
                put(2, "Февраля");
                put(3, "Марта");
                put(4, "Апреля");
                put(5, "Мая");
                put(6, "Июня");
                put(7, "Июля");
                put(8, "Августа");
                put(9, "Сентября");
                put(10, "Ноября");
                put(11, "Октября");
                put(12, "Декабря");
            }};

            model.addAttribute("hasRecord", true);
            Record record = recordList.get(0);
            model.addAttribute("masterName", record.getMaster().getName());
            model.addAttribute("masterQualification", record.getMaster().getQualification().toString());
            model.addAttribute("dateAndTime", "" + record.getDate().getDayOfMonth() + " " + months.get(record.getDate().getMonth().getValue()) + ", " + record.getTime().format(DateTimeFormatter.ofPattern("HH:mm")));
            model.addAttribute("recordServices", record.getServices().stream().map(TreatmentService::getName).collect(Collectors.joining(", ")));
            model.addAttribute("recordId", record.getId());
        } else {
            model.addAttribute("hasRecord", false);
        }

        return "authorization/account";
    }

}
