package jhkim105.tutorials.kafka.dto;

public record Greeting(String message, String name) {

    @Override
    public String toString() {
        return String.format("%s, %s!", message, name);
    }
}
