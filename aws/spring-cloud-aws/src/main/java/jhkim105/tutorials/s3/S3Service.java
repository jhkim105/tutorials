package jhkim105.tutorials.s3;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.core.sync.ResponseTransformer;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.DeleteBucketRequest;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Request;
import software.amazon.awssdk.services.s3.model.ListObjectsV2Response;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Object;

@Component
@RequiredArgsConstructor
@Slf4j
public class S3Service {

    private final ResourceLoader resourceLoader;
    private final S3Client s3;

    public File download(String s3Url, String destDir) {
        Resource resource = resourceLoader.getResource(s3Url);

        File destinationDirectory = new File(destDir);
        if (!destinationDirectory.exists()) {
            destinationDirectory.mkdirs();
        }

        File downloadedFile = new File(destinationDirectory, resource.getFilename());

        try (InputStream inputStream = resource.getInputStream()) {
            Files.copy(inputStream, downloadedFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
          throw new RuntimeException(e);
        }

      return downloadedFile;
    }
    public String upload(String bucketName, String key, MultipartFile multipartFile) {
        if (!doesBucketExist(bucketName)) {
            throw new IllegalStateException(String.format("Bucket does not exist: %s", bucketName));
        }
        return uploadObject(bucketName, key, multipartFile);
    }

    private String uploadObject(String bucketName, String key, MultipartFile multipartFile) {
        try {
            s3.putObject(PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType(multipartFile.getContentType())
                .contentLength(multipartFile.getSize())
                .build(), RequestBody.fromInputStream(multipartFile.getInputStream(), multipartFile.getSize()));
            log.debug("Uploaded object: {}", key);
            return key;
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload object to S3", e);
        }
    }

    public List<String> getObjectKeyList(String bucketName) {
        List<String> keys = new ArrayList<>();
        ListObjectsV2Request listObjectsV2Request = ListObjectsV2Request.builder()
            .bucket(bucketName)
            .build();

        ListObjectsV2Response listObjectsV2Response = s3.listObjectsV2(listObjectsV2Request);
        for (S3Object object : listObjectsV2Response.contents()) {
            keys.add(object.key());
        }
        return keys;
    }

    public Resource loadAsResource(String bucketName, String key) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
            .bucket(bucketName)
            .key(key)
            .build();

        try (ResponseInputStream<GetObjectResponse> responseInputStream =
            s3.getObject(getObjectRequest, ResponseTransformer.toInputStream())) {
            byte[] bytes = responseInputStream.readAllBytes();
            return new ByteArrayResource(bytes);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load object as resource from S3", e);
        }
    }

    public List<String> getBucketNameList() {
        ListBucketsResponse response = s3.listBuckets();
        return response.buckets().stream().map(Bucket::name).toList();
    }

    public void deleteBucket(String bucketName) {
        s3.deleteBucket(DeleteBucketRequest.builder().bucket(bucketName).build());
    }

    public void deleteObject(String bucketName, String key) {
        s3.deleteObject(DeleteObjectRequest.builder().bucket(bucketName).key(key).build());
    }

    private boolean doesBucketExist(String bucketName) {
        return s3.listBuckets().buckets().stream().anyMatch(b -> b.name().equals(bucketName));
    }

}