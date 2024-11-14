package jhkim105.tutorials.core.java.basic;


import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import org.junit.jupiter.api.Test;

class InetAddressTests {


  @Test
  void test() throws UnknownHostException {
    InetAddress inetAddress = InetAddress.getLocalHost();
    System.out.println("IP Address: {}" + inetAddress.getHostAddress());
    System.out.println("Host Name: {}" + inetAddress.getHostName());
  }

  @Test
  void test2() throws Exception {
    try (final DatagramSocket socket = new DatagramSocket()) {
      socket.connect(InetAddress.getByName("8.8.8.8"), 10002);
      String ip = socket.getLocalAddress().getHostAddress();
      System.out.println(ip);
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
    System.out.println(ip);
  }

}
