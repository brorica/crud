package com.crud.service.article;

import com.crud.domain.article.ArticleDto;
import com.crud.domain.article.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ArticleService {

    private final ArticleRepository articleRepository;

    public Page<ArticleDto> findAllArticles(Pageable pageable) {
        return articleRepository.findAll(pageable)
            .map(ArticleDto::from);
    }
}
