package main.java.com.sportradar.domain;

public record Team(String country) {

    public Team {
        if (country == null || country.isBlank()) {
            throw new IllegalArgumentException("Country cannot be null or blank");
        }
    }

}
