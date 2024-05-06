package com.alekseyruban.barbershopServer.controllers;

import com.alekseyruban.barbershopServer.dto.AuthTokenDTO;
import com.alekseyruban.barbershopServer.dto.ClientDTO;
import com.alekseyruban.barbershopServer.entity.AuthorizationToken;
import com.alekseyruban.barbershopServer.entity.Client;
import com.alekseyruban.barbershopServer.entity.Record;
import com.alekseyruban.barbershopServer.entity.TreatmentService;
import com.alekseyruban.barbershopServer.helpers.EmailWorker;
import com.alekseyruban.barbershopServer.security.CustomUserDetails;
import com.alekseyruban.barbershopServer.service.AuthTokenService;
import com.alekseyruban.barbershopServer.service.ClientService;
import com.alekseyruban.barbershopServer.service.RecordService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;


    @GetMapping({"", "/"})
    public String defaultPage() {
        if (isClientAuthorized()) {
            return "redirect:/authorization/account";
        }
        return "redirect:/authorization/signin";
    }

    @GetMapping({"/signup", "/signup/"})
    public String signup() {
        if (isClientAuthorized()) {
            return "redirect:/authorization/account";
        }
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
    public String confirmEmail(@PathVariable(value = "token") String tokenPart) {

        String token = tokenPart.split("_")[0];
        Long clientId = Long.parseLong(tokenPart.split("_")[1]);

        AuthTokenDTO tokenDTO = AuthTokenDTO.builder()
                .token(token)
                .build();

        AuthorizationToken authorizationToken = authTokenService.getByClientId(clientId);

        if (passwordEncoder.matches(token, authorizationToken.getToken())) {
            Client client = clientService.confirmEmail(authorizationToken.getClient());
            authTokenService.deleteToken(authorizationToken.getId());
            EmailWorker.successEmail(client.getEmail());
        }

        return "redirect:/authorization/signin";
    }

    @GetMapping("/signin")
    public String signin() {
        if (isClientAuthorized()) {
            return "redirect:/authorization/account";
        }
        return "authorization/signin";
    }

    @GetMapping({"/restore-password", "/restore-password/"})
    public String restorePassword() {
        if (isClientAuthorized()) {
            return "redirect:/authorization/account";
        }
        return "authorization/restore-password";
    }

    @RequestMapping(value = "/restore-password", method = RequestMethod.POST)
    public @ResponseBody HttpStatus restore(@RequestParam String email) {

        clientService.restorePassword(email);

        return HttpStatus.OK;
    }

    @GetMapping({"/account", "/account/"})
    public String account(Model model) {
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

    @RequestMapping(value = {"/account", "/account/"}, method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteAccount() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof CustomUserDetails) {
                CustomUserDetails userDetails = (CustomUserDetails) principal;
                Long userId = userDetails.getId();

                Client client = clientService.getById(userId);
                System.out.println(client.getEmail());

                for (Record r : recordService.readByClientId(userId)) {
                    System.out.println(r.getId() + " id");
                    recordService.delete(r.getId());
                }

                clientService.delete(userId);
            }
        }
        return new ResponseEntity<>("Account deleted", HttpStatus.OK);
    }

    private boolean isClientAuthorized() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            for (GrantedAuthority auth : authentication.getAuthorities()) {
                if (auth.getAuthority().equals("CLIENT") || auth.getAuthority().equals("ADMIN")) {
                    return true;
                }
            }
        }
        return false;
    }


}
