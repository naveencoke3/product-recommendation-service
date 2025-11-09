package com.naveen.sample.productrecommendationservice.util;

public class AgeRangeParser {
    public static boolean ageInRange(String range, int age) {
        if (range == null || range.isBlank()) return true;

        String[] parts = range.split("-");
        try {
            int min = Integer.parseInt(parts[0].trim());
            int max = (parts.length > 1) ? Integer.parseInt(parts[1].trim()) : min;
            return age >= min && age <= max;
        } catch (NumberFormatException e) {
            return true;
        }
    }
}
