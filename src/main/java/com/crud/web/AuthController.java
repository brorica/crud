package com.crud.web;

import com.crud.config.auth.jwt.JwtManager;
import com.crud.domain.user.AuthToken;
import com.crud.service.user.UserService;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private final UserService userService;
    private final JwtManager jwtManager;

    @RequestMapping("/auth")
    public void auth(@RequestParam String email,
        HttpServletResponse response) throws IOException {
        AuthToken token = userService.getToken(email);
        response.addHeader("accessToken", jwtManager.createAccessToken(token.getAccessToken()));
        response.addHeader("refreshToken", jwtManager.createRefreshToken(token.getRefreshToken()));
        System.out.println(response.getHeader("accessToken"));
        System.out.println(response.getHeader("refreshToken"));
//        response.sendRedirect("/");
//        response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
    }
}
