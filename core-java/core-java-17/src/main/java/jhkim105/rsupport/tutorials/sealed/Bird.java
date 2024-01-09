package jhkim105.rsupport.tutorials.sealed;

public sealed abstract class Bird permits Penguin, Sparrow {
    public abstract void fly();

}

