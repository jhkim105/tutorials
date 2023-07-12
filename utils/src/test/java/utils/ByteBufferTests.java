package utils;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.nio.ByteBuffer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;



@Slf4j
class ByteBufferTests {

  @Test
  void test() {
    int number = 12;
    String text = "This is text.";
    ByteBuffer writeBuffer = ByteBuffer.allocate(100);
    writeBuffer.putInt(number);
    writeBuffer.put(text.getBytes());
    writeBuffer.flip();
    byte[] out = new byte[writeBuffer.limit()];
    System.arraycopy(writeBuffer.array(), 0, out, 0, writeBuffer.limit());
    log.debug("{}",out.length);

    ByteBuffer readBuffer = ByteBuffer.wrap(out);
    int numberIn = readBuffer.getInt();
    byte[] in = new byte[out.length - 4];
    readBuffer.get(in, 0, out.length - 4);
    String textIn = new String(in);

    assertThat(numberIn).isEqualTo(number);
    assertThat(textIn).isEqualTo(text);

  }
}
