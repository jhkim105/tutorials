package jhkim105.tutorials.io;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class FileReader {

    public byte[] right(String filePath, int numBytes) {
        try (RandomAccessFile file = new RandomAccessFile(filePath, "r");
            FileChannel channel = file.getChannel()) {

            long fileSize = channel.size();
            long startPosition = Math.max(0, fileSize - numBytes);

            MappedByteBuffer buffer = channel.map(FileChannel.MapMode.READ_ONLY, startPosition, numBytes);
            byte[] data = new byte[numBytes];
            buffer.get(data);

            return data;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}