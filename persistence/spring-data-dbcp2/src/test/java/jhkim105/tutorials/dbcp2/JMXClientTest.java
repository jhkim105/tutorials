package jhkim105.tutorials.dbcp2;

import java.util.Set;
import java.util.TreeSet;
import javax.management.JMX;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.pool2.impl.GenericObjectPoolMXBean;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

@Slf4j
public class JMXClientTest {


  @SneakyThrows
  @Test
  @Disabled
  public void test() {
//    JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://52.193.43.196:9840/jmxrmi");
    JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:9840/jmxrmi");

    JMXConnector jmxc = JMXConnectorFactory.connect(url, null);
    MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();
    Set<ObjectName> names =
        new TreeSet<>(mbsc.queryNames(null, null));
    for (ObjectName name : names) {
      log.debug("ObjectName = " + name);
    }

    ObjectName mbeanName = new ObjectName("org.apache.commons.dbcp2:name=dataSource,type=BasicDataSource");
    GenericObjectPoolMXBean mxBean = JMX.newMBeanProxy(mbsc, mbeanName, GenericObjectPoolMXBean.class, true);
    log.debug("numActive:{}", mxBean.getNumActive());
  }
}
