package jhkim105.tutorials.s3;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.CompleteMultipartUploadRequest;
import software.amazon.awssdk.services.s3.model.CompleteMultipartUploadResponse;
import software.amazon.awssdk.services.s3.model.CompletedMultipartUpload;
import software.amazon.awssdk.services.s3.model.CompletedPart;
import software.amazon.awssdk.services.s3.model.CreateMultipartUploadRequest;
import software.amazon.awssdk.services.s3.model.CreateMultipartUploadResponse;
import software.amazon.awssdk.services.s3.model.UploadPartRequest;
import software.amazon.awssdk.services.s3.model.UploadPartResponse;


/**
 * <a href="https://docs.aws.amazon.com/AmazonS3/latest/userguide/mpuoverview.html"/>
 */
@SpringBootTest
@Disabled
class MultipartUploadTest {

  @Autowired
  S3Client s3;

  long partSize = 10 * 1024 * 1024; // 10MB
  String filePath = "/Users/jihwankim/Documents/large/129m.dmg";
  String bucketName = "rtm-test";
  String objectKey = "129m.dmg";


  @Test
  void upload() {
    File file = new File(filePath);
    try {
      List<CompletedPart> completedParts = new ArrayList<>();
      CreateMultipartUploadResponse response = s3.createMultipartUpload(CreateMultipartUploadRequest.builder()
          .bucket(bucketName)
          .key(objectKey)
          .build());

      String uploadId = response.uploadId();

      try (FileInputStream fileInputStream = new FileInputStream(file)) {
        long bytesRead;
        byte[] buffer = new byte[(int) partSize];
        while ((bytesRead = fileInputStream.read(buffer)) != -1) {
          ByteBuffer byteBuffer = ByteBuffer.wrap(buffer, 0, (int)bytesRead);

          UploadPartRequest uploadRequest = UploadPartRequest.builder()
              .bucket(bucketName)
              .key(objectKey)
              .uploadId(uploadId)
              .partNumber(completedParts.size() + 1)
              .contentLength(bytesRead)
              .build();

          RequestBody requestBody = RequestBody.fromByteBuffer(byteBuffer);

          UploadPartResponse uploadPartResponse = s3.uploadPart(uploadRequest, requestBody);
          completedParts.add(CompletedPart.builder()
              .partNumber(completedParts.size() + 1)
              .eTag(uploadPartResponse.eTag())
              .build());
          System.out.println("completedParts.size: " + completedParts.size());

        }
      }

      CompleteMultipartUploadResponse finalResponse = s3.completeMultipartUpload(CompleteMultipartUploadRequest.builder()
          .bucket(bucketName)
          .key(objectKey)
          .uploadId(uploadId)
          .multipartUpload(CompletedMultipartUpload.builder()
              .parts(completedParts)
              .build())
          .build());

      System.out.println("Upload completed. ETag: " + finalResponse.eTag());
    } catch (Exception e) {
      throw new RuntimeException(e);
    } finally {
      s3.close();
    }
  }


}
