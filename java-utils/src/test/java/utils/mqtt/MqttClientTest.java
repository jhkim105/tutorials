package utils.mqtt;

import java.util.UUID;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class MqttClientTest {

  @Test
  @Disabled
  public void testSttLog() throws MqttException {
    String conferenceId = "abccc5e434ea4906a80c38587e1159d9";
    String url = "tcp://devpns.remotemeeting.com:1883";
    String topic = String.format("/RCCP/CON/%s/STT", conferenceId);
    sendMessage(url,topic, "test");
  }


  private void sendMessage(String url, String topic, String message) throws MqttException {
    IMqttClient client = client(url);
    MqttMessage mqttMessage = new MqttMessage(message.getBytes());
    client.publish(topic, mqttMessage);
  }

  public IMqttClient client(String url) throws MqttException {
    String clientId = UUID.randomUUID().toString();
    IMqttClient client = new MqttClient(url, clientId);
    MqttConnectOptions options = new MqttConnectOptions();
    options.setAutomaticReconnect(true);
    options.setCleanSession(true);
    options.setConnectionTimeout(10);
    client.connect(options);
    return client;
  }

}
