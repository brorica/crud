package com.crud.web;

import com.crud.domain.article.ArticleDto;
import com.crud.service.article.ArticleService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ArticleController {

    private final ArticleService articleService;

    @GetMapping("/article")
    public List<ArticleDto> find(@PageableDefault(size = 7) Pageable pageable) {
        return articleService.findAllArticles(pageable).getContent();
    }
}
