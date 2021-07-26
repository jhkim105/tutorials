## BeanIO를 활용한 전문 만들기

### Example
#### csv 
* http://beanio.org/2.1/docs/reference/index.html#MyFirstStream

#### fixedLength

Message.java
```java
@Getter
@ToString
@NoArgsConstructor
public class Message implements Serializable {

  private static final long serialVersionUID = -3894497903821668443L;
  private Header header;

  private Body body;

  @Builder
  public Message(Header header, Body body) {
    this.header = header;
    this.body = body;
  }
}
```
mapping.xml
```xml
<?xml version='1.0' encoding='UTF-8' ?>
<beanio xmlns="http://www.beanio.org/2012/03" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://www.beanio.org/2012/03 http://www.beanio.org/2012/03/mapping.xsd">

  <stream name="message" format="fixedlength">
    <record name="message01" class="com.example.beanio.example.Message" maxOccurs="1" >
      <segment name="header" class="com.example.beanio.example.Header">
        <field name="size" type="int" length="8"/>
        <field name="token" length="32" justify="right" padding=" "/>
      </segment>
      <segment name="body" class="com.example.beanio.example.Body" >
        <field name="number" type="int" length="4"/>
        <field name="code" length="5" justify="right" padding=" "/>
        <field name="msg" length="20" justify="right" padding=" "/>
      </segment>
    </record>
  </stream>

</beanio>
```
Test Code
```java
      Message message = Message.builder()
          .header(Header.builder()
          .size(20)
          .token("token")
          .build())
          .body(Body.builder()
          .msg("msg")
    //      .code("code")
          .number(21)
          .build())
          .build();

      log.info("message->{}", message);
      StringWriter stringWriter = new StringWriter();
      Resource resource = resourceLoader.getResource("classpath:/mapping-fixedLength.xml");
      log.info("message->{}", message);
      StreamFactory factory = StreamFactory.newInstance();
      factory.load(resource.getFile());
      BeanWriter beanWriter = factory.createWriter("message", stringWriter);
      beanWriter.write(message);
      String messageString = stringWriter.toString();
      log.info("messageString->{}", messageString);
      
      StringReader reader = new StringReader(messageString);
      BeanReader beanReader = factory.createReader("message", reader);
      Message message2 = (Message)beanReader.read();
      log.info("{}", message2);
```
실행 결과
```
2021-07-26 13:44:52.726  INFO 8551 --- [           main] com.example.beanio.FixedLengthTest       : message->Message(header=Header(size=20, token=token), body=Body(number=21, code=null, msg=msg))
2021-07-26 13:44:52.726  INFO 8551 --- [           main] com.example.beanio.FixedLengthTest       : message->Message(header=Header(size=20, token=token), body=Body(number=21, code=null, msg=msg))
2021-07-26 13:44:52.754  INFO 8551 --- [           main] com.example.beanio.FixedLengthTest       : messageString->20                                 token21  ABCDE                 msg

2021-07-26 13:44:52.758  INFO 8551 --- [           main] com.example.beanio.FixedLengthTest       : Message(header=Header(size=20, token=token), body=Body(number=21, code=ABCDE, msg=msg))``` 
```




## Refs
* http://beanio.org/
* [mapping sample](https://svn.apache.org/repos/asf/camel/trunk/components/camel-beanio/src/test/resources/org/apache/camel/dataformat/beanio/mappings.xml)