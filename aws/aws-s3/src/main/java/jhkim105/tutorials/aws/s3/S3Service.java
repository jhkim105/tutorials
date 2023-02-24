package jhkim105.tutorials.aws.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import com.amazonaws.util.IOUtils;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
@Slf4j
public class S3Service {


  private final TransferManager tm;
  private final AmazonS3 s3;

  public String upload(String bucketName, String key, MultipartFile multipartFile) {
//    return uploadUsingTransferManager(bucketName, key, multipartFile);
    return uploadUsingAmazonS3(bucketName, key, multipartFile);
  }

  private String uploadUsingAmazonS3(String bucketName, String key, MultipartFile multipartFile) {
    InputStream inputStream;
    try {
      inputStream = multipartFile.getInputStream();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    ObjectMetadata om = objectMetadata(multipartFile);
    PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, inputStream, om);
    PutObjectResult result = s3.putObject(putObjectRequest);
    log.debug("result-> {}", result);

    return key;
  }
  private String uploadUsingTransferManager(String bucketName, String key, MultipartFile multipartFile) {
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
    metadata.setContentLength(multipartFile.getSize());
    metadata.getUserMetadata().put("fileExtension", FilenameUtils.getExtension(multipartFile.getOriginalFilename()));
    return metadata;
  }

  public List<String> getObjectKeyList(String bucketName ) {
    List<String> list = new ArrayList<>();
    ObjectListing objectListing = s3.listObjects(bucketName);
    for(S3ObjectSummary os : objectListing.getObjectSummaries()) {
      list.add(os.getKey());
    }
    return list;
  }

  public Resource loadAsResource(String bucketName, String key) {
    try {
      S3Object o = s3.getObject(bucketName, key);
      S3ObjectInputStream s3is = o.getObjectContent();
      byte[] bytes = IOUtils.toByteArray(s3is);

      Resource resource = new ByteArrayResource(bytes);
      return resource;
    } catch (IOException e) {
      throw new IllegalStateException(e);
    }
  }

  public Bucket createBucket(String bucketName) {
    return s3.createBucket(bucketName);
  }

  public List<String> getBucketNameList() {
    return s3.listBuckets().stream().map(Bucket::getName).collect(Collectors.toList());
  }

  public void deleteBucket(String bucketName) {
    s3.deleteBucket(bucketName);
  }


  public void deleteObject(String bucketName, String key) {
    s3.deleteObject(bucketName, key);
  }
}