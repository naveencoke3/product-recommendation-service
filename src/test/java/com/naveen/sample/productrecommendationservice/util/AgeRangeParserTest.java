package com.naveen.sample.productrecommendationservice.util;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class AgeRangeParserTest {

    @Test
    void shouldReturnTrueWhenAgeInRange() {
        assertThat(AgeRangeParser.ageInRange("5-10", 7)).isTrue();
    }

    @Test
    void shouldReturnFalseWhenAgeOutOfRange() {
        assertThat(AgeRangeParser.ageInRange("5-10", 3)).isFalse();
    }

    @Test
    void shouldHandleSingleValueRange() {
        assertThat(AgeRangeParser.ageInRange("8", 8)).isTrue();
        assertThat(AgeRangeParser.ageInRange("8", 9)).isFalse();
    }

    @Test
    void shouldHandleNullOrBlank() {
        assertThat(AgeRangeParser.ageInRange(null, 5)).isTrue();
        assertThat(AgeRangeParser.ageInRange("", 5)).isTrue();
    }
}