package com.crud.web;

import static com.crud.config.auth.jwt.JwtManager.AUTHORIZATION_KEY;
import static com.crud.config.auth.jwt.JwtManager.REFRESH_KEY;

import com.crud.config.auth.LoginUser;
import com.crud.config.auth.dto.CustomOauth2User;
import com.crud.config.auth.dto.SessionUser;
import com.crud.config.auth.dto.TokenDto;
import com.crud.config.auth.jwt.JwtManager;
import com.crud.domain.user.User;
import com.crud.service.posts.PostsService;
import com.crud.service.user.UserService;
import com.crud.web.dto.PostsResponseDto;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final UserService userService;
    private final PostsService postsService;
    private final JwtManager jwtManager;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user, HttpServletResponse response, Authentication authentication) {
        if (authentication != null) {
            CustomOauth2User userDetails = (CustomOauth2User) authentication.getPrincipal();
            TokenDto token = userDetails.getToken();
            response.addHeader(AUTHORIZATION_KEY, jwtManager.createAccessToken(token));
            response.addHeader(REFRESH_KEY, jwtManager.createRefreshToken(token));
            User test = userService.findById(token.getUid());
            model.addAttribute("userName", test.getName());
        }
        model.addAttribute("posts", postsService.findAllDesc());
        if (user != null) {
            model.addAttribute("userName", user.getName());
        }
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);
        return "posts-update";
    }
}
