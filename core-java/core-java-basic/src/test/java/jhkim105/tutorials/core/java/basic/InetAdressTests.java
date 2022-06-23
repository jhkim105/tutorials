package jhkim105.tutorials.core.java.basic;


import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
class InetAdressTests {


  @Test
  void test() throws UnknownHostException {
    InetAddress inetAddress = InetAddress.getLocalHost();
    log.info("IP Address: {}" + inetAddress.getHostAddress());
    log.info("Host Name: {}" + inetAddress.getHostName());
  }

  @Test
  void test2() throws Exception {
    try (final DatagramSocket socket = new DatagramSocket()) {
      socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
      String ip = socket.getLocalAddress().getHostAddress();
      log.info("{}", ip);
    }
  }

  @Test
  void test3() {
    String ip = null;
    try {
      Enumeration<NetworkInterface> networkInterfaces = NetworkInterface
          .getNetworkInterfaces();
      while (networkInterfaces.hasMoreElements()) {
        NetworkInterface ni = networkInterfaces
            .nextElement();
        Enumeration<InetAddress> inetAddresses = ni.getInetAddresses();
        while(inetAddresses.hasMoreElements()) {
          InetAddress ia= inetAddresses.nextElement();
          if (!ia.isLinkLocalAddress()
              && !ia.isLoopbackAddress()
              && ia instanceof Inet4Address) {
            ip = ia.getHostAddress();
          }
        }
      }
    } catch (SocketException e) {
      throw new IllegalStateException(e);
    }
    log.info("{}", ip);
  }

}
