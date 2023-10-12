package com.example.accommodation.properties;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * Validation properties for hotels and locations.
 */
@Configuration
@EnableCaching
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
    @NotNull(message = "Validation property \"reputationRedBadge\" cannot be null: check application.yml")
    String reputationRedBadge;
    @NotNull(message = "Validation property \"reputationYellowBadge\" cannot be null: check application.yml")
    String reputationYellowBadge;
    @NotNull(message = "Validation property \"reputationGreenBadge\" cannot be null: check application.yml")
    String reputationGreenBadge;
    @NotNull(message = "Validation property \"errorMsgNameContainsRestrictedWords\" cannot be null: check application.yml")
    String errorMsgNameContainsRestrictedWords;
    @NotNull(message = "Validation property \"errorMsgNameIsShort\" cannot be null: check application.yml")
    String errorMsgNameIsShort;
    @NotNull(message = "Validation property \"errorInvalidRating\" cannot be null: check application.yml")
    String errorInvalidRating;
    @NotNull(message = "Validation property \"errorInvalidCategory\" cannot be null: check application.yml")
    String errorInvalidCategory;
    @NotNull(message = "Validation property \"errorImageUrl\" cannot be null: check application.yml")
    String errorImageUrl;
    @NotNull(message = "Validation property \"errorLocationZipCode\" cannot be null: check application.yml")
    String errorLocationZipCode;
    @NotNull(message = "Validation property \"errorReputation\" cannot be null: check application.yml")
    String errorReputation;
}
