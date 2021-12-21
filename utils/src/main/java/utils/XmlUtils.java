package utils;

import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class XmlUtils {
  public static String toString(Object object) {
    StringWriter writer = new StringWriter();
    try {
      JAXBContext context = JAXBContext.newInstance(object.getClass());
      Marshaller m = context.createMarshaller();
      m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.FALSE);
      m.marshal(object, writer);
    } catch (JAXBException ex) {
      throw new RuntimeException(String.format("object convert to xml string error..,%s", ex.getMessage()), ex);
    }
    return writer.toString();
  }

  public static <T> T toObject(String str, Class<T> clazz) {
    T result;
    try {
      JAXBContext context = JAXBContext.newInstance(clazz);
      Unmarshaller um = context.createUnmarshaller();
      result = (T)um.unmarshal(new StreamSource(new StringReader(str)));
    } catch (JAXBException ex) {
      throw new RuntimeException(String.format("xml convert to object error..,%s", ex.getMessage()), ex);
    }
    return result;
  }
}
