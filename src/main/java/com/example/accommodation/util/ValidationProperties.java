package com.example.accommodation.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "validation")
@Getter
@Setter
@Validated
public class ValidationProperties {
    @NotEmpty(message = "Validation property \"nameBlackList\" cannot be null: check application.yml")
    List<String> nameBlackList;
    @NotEmpty(message = "Validation property \"nameSymbolsMin\" cannot be null: check application.yml")
    int nameSymbolsMin;
    @NotEmpty(message = "Validation property \"ratingMin\" cannot be null: check application.yml")
    int ratingMin;
    @NotEmpty(message = "Validation property \"ratingMax\" cannot be null: check application.yml")
    int ratingMax;
    @NotEmpty(message = "Validation property \"categoryWhiteList\" cannot be null: check application.yml")
    List<String> categoryWhiteList;
    @NotEmpty(message = "Validation property \"zipCodeLength\" cannot be null: check application.yml")
    int zipCodeLength;
    @NotEmpty(message = "Validation property \"reputationMin\" cannot be null: check application.yml")
    int reputationMin;
    @NotEmpty(message = "Validation property \"reputationMax\" cannot be null: check application.yml")
    int reputationMax;
    @NotEmpty(message = "Validation property \"reputationRedThreshold\" cannot be null: check application.yml")
    int reputationRedThreshold;
    @NotEmpty(message = "Validation property \"reputationYellowThreshold\" cannot be null: check application.yml")
    int reputationYellowThreshold;
}
