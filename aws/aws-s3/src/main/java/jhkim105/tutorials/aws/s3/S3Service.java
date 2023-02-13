package jhkim105.tutorials.aws.s3;

import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import com.amazonaws.util.IOUtils;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
@Slf4j
public class S3Service {


  private final TransferManager tm;

  public String uploadWithoutContentLength(String bucketName, String key, MultipartFile multipartFile) {
    InputStream inputStream;
    try {
      inputStream = multipartFile.getInputStream();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    ObjectMetadata om = objectMetadata(multipartFile);
    PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, inputStream, om);
    Upload upload = tm.upload(putObjectRequest);
    try {
      upload.waitForCompletion();
    } catch (InterruptedException e) {
      throw new IllegalStateException(String.format("upload error. key:%s, error:%s", key, e), e);
    }

    log.debug(String.format("upload success. key:%s", key));

    return key;
  }

  private ObjectMetadata objectMetadata(MultipartFile multipartFile) {
    ObjectMetadata metadata = new ObjectMetadata();
    metadata.setContentType(multipartFile.getContentType());
    metadata.setHeader("filename", multipartFile.getOriginalFilename());
    return metadata;
  }

  public String upload(String bucketName, String key, MultipartFile multipartFile) {
    long contentLength;
    ByteArrayInputStream bis;
    try {
      bis = new ByteArrayInputStream(multipartFile.getBytes());
      contentLength = IOUtils.toByteArray(bis).length;/**/
      bis.reset();
    } catch (IOException e) {
      throw new IllegalStateException(String.format("upload error. key:%s, error:%s", key, e), e);
    }

    ObjectMetadata om = objectMetadata(multipartFile);
    om.setContentLength(contentLength);
    PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, bis, om);
    Upload upload = tm.upload(putObjectRequest);

    try {
      upload.waitForCompletion();
    } catch (InterruptedException e) {
      throw new IllegalStateException(String.format("upload error. key:%s, error:%s", key, e), e);
    }

    return key;
  }

  public List<String> getList(String bucketName ) {
    List<String> list = new ArrayList<>();
    ObjectListing objectListing = tm.getAmazonS3Client().listObjects(bucketName);
    for(S3ObjectSummary os : objectListing.getObjectSummaries()) {
      list.add(os.getKey());
    }
    return list;
  }



}