package com.alekseyruban.barbershopServer.controllers;

import com.alekseyruban.barbershopServer.dto.AuthTokenDTO;
import com.alekseyruban.barbershopServer.dto.ClientDTO;
import com.alekseyruban.barbershopServer.entity.AuthorizationToken;
import com.alekseyruban.barbershopServer.entity.Client;
import com.alekseyruban.barbershopServer.helpers.EmailWorker;
import com.alekseyruban.barbershopServer.service.AuthTokenService;
import com.alekseyruban.barbershopServer.service.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/authorization/")
@AllArgsConstructor
public class AuthorizationController {

    private final ClientService clientService;
    private final AuthTokenService authTokenService;

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
    public String account() {
        return "authorization/account";
    }

}
