package com.crud.domain.article;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ArticleDto {

    private Long id;
    private String businessName;

    public static ArticleDto from(Article article) {
        return ArticleDto.builder()
            .id(article.getId())
            .businessName(article.getBusinessName())
            .build();
    }
}
