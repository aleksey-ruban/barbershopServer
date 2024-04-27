package com.alekseyruban.barbershopServer.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/authorization/")
public class AuthorizationController {

    @GetMapping({"/signup", "/signup/"})
    public String signup() {
        return "authorization/signup";
    }

    @GetMapping({"/signin", "/signin/"})
    public String signin() {
        return "authorization/signin";
    }

    @GetMapping({"/restore-password", "/restore-password/"})
    public String restorePassword() {
        return "authorization/restore-password";
    }

    @GetMapping({"/account", "/account/"})
    public String account() {
        return "authorization/account";
    }

}
