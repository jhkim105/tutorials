package jhkim105.tutorials;


import lombok.Getter;

@Getter
public enum KafkaTopic {
    FOO("foo");
    private final String topicName;

    KafkaTopic(String topicName) {
        this.topicName = topicName;
    }

}
