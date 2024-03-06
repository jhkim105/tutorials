package jhkim105.tutorials.spring.mvc.controller;

public record Sample(
    String id,
    String name,
    StorageType storageType
) {

  public Sample(String id, String name) {
    this(id, name, null);
  }
}
