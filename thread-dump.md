
## jstatd
policy 작성
$JAVA_HOME/jre/lib/security/jstatd.all.policy
(/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.302.b08-0.amzn2.0.1.x86_64/jre/lib/security/jstatd.all.policy)
```shell
grant codebase "file:${java.home}/../lib/tools.jar" {
permission java.security.AllPermission;
};
```
Run
```shell
jstatd -J-Djava.security.policy=/usr/lib/jvm/java-1.8.0-openjdk-1.8.0.302.b08-0.amzn2.0.1.x86_64/jre/lib/security/jstatd.all.policy -J-Djava.rmi.server.hostname=18.178.73.168 -p 40400
```


Connect
```shell
jps -l -m -v rmi://18.178.73.168:40400
```

jstatd 는 지정한 포트외에 한개 랜덤 포트를 추가로 리스닝한다. 접속하려면, 방화벽을 해제하든지, 추가 리스닝 하고 있는 포트를 찾아서 오픈해야 한다.

## JMX
```shell
JAVA_OPTS="$JAVA_OPTS -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=9840 -Dcom.sun.management.jmxremote.rmi.port=9841"
JAVA_OPTS="$JAVA_OPTS -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false"
JAVA_OPTS="$JAVA_OPTS -Djava.rmi.server.hostname=54.249.75.246"
```

