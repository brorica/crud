package com.crud.domain.article;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "sejong_market")
public class Article {

    @Id
    private Long id;

    private String businessName;

    private String branchName;

    private String commercialSubClassificationCode;

    private String roadName;

    private String buildingMainAddress;

    private String buildingSubAddress;

    private String newZipcode;
}
