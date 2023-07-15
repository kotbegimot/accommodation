package com.example.accommodation.util;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "validation")
@Getter
@Setter
@Validated
public class ValidationProperties {
    @NotEmpty(message = "Validation property \"nameBlackList\" cannot be null: check application.yml")
    List<String> nameBlackList;
    @NotNull(message = "Validation property \"nameSymbolsMin\" cannot be null: check application.yml")
    Integer nameSymbolsMin;
    @NotNull(message = "Validation property \"ratingMin\" cannot be null: check application.yml")
    Integer ratingMin;
    @NotNull(message = "Validation property \"ratingMax\" cannot be null: check application.yml")
    Integer ratingMax;
    @NotEmpty(message = "Validation property \"categoryWhiteList\" cannot be null: check application.yml")
    List<String> categoryWhiteList;
    @NotNull(message = "Validation property \"zipCodeLength\" cannot be null: check application.yml")
    Integer zipCodeLength;
    @NotNull(message = "Validation property \"reputationMin\" cannot be null: check application.yml")
    Integer reputationMin;
    @NotNull(message = "Validation property \"reputationMax\" cannot be null: check application.yml")
    Integer reputationMax;
    @NotNull(message = "Validation property \"reputationRedThreshold\" cannot be null: check application.yml")
    Integer reputationRedThreshold;
    @NotNull(message = "Validation property \"reputationYellowThreshold\" cannot be null: check application.yml")
    Integer reputationYellowThreshold;
}
