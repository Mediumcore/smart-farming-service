package com.sdpm.smart.farming.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author shirukai
 */
@Controller
@RequestMapping("/api/v1/auth/")
public class AuthController {
    @Value("${sso.url}")
    private String ssoUrl;

    @GetMapping("/logout")
    public String logout() {
        return "redirect:" + ssoUrl + "/#/logout";
    }

    @GetMapping("/login")
    public String login(
            @RequestParam(value = "redirect") String redirect,
            @RequestParam(value = "router") String router
    ) {
       // return "redirect:" + ssoUrl + "/#/login?redirect=" + redirect + "&router=" + router;
        return "redirect:" + ssoUrl + "/#/login";
    }

    @GetMapping("/user")
    public String getUserByToken(
            @RequestParam(value = "token") String token
    ) {
        return "redirect:" + ssoUrl + "/api/v1/token/user?token=" + token;
    }
}
