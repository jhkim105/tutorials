package jhkim105.tutorials.kafka.dto;

public record Farewell(String message, Integer remainMinutes) {

    @Override
    public String toString() {
        return String.format("%s. In %d!", message, remainMinutes);
    }
}
