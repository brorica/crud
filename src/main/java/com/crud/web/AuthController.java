package com.crud.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
public class AuthController {

    @RequestMapping("/auth")
    public String auth(HttpServletRequest request, HttpServletResponse response) {
        return "index";
    }
}
