package jhkim105.rsupport.tutorials.record;

public record Member(String name, int age) {

  public Member(int age) {
    this("Anonymous", age);
  }
}
