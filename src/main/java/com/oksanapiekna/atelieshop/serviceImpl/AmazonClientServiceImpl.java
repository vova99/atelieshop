//package com.oksanapiekna.atelieshop.serviceImpl;
//
//import com.amazonaws.auth.AWSCredentials;
//import com.amazonaws.auth.BasicAWSCredentials;
//import com.amazonaws.services.s3.AmazonS3;
//import com.amazonaws.services.s3.AmazonS3Client;
//import com.amazonaws.services.s3.model.DeleteObjectRequest;
//import com.amazonaws.services.s3.model.GetObjectRequest;
//import com.amazonaws.services.s3.model.PutObjectRequest;
//import com.amazonaws.services.s3.model.S3Object;
//import com.oksanapiekna.atelieshop.service.AmazonClientService;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.annotation.PostConstruct;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.nio.file.Files;
//import java.nio.file.StandardCopyOption;
//import java.util.Date;
//
//@Service
//@AllArgsConstructor
//@NoArgsConstructor
//@Data
//public class AmazonClientServiceImpl implements AmazonClientService {
//
//    private AmazonS3 s3client;
//
//    @Value("${cloud.aws.credentials.endpointUrl}")
//    private String endpointUrl;
//    @Value("${cloud.aws.credentials.bucketName}")
//    private String bucketName;
//    @Value("${cloud.aws.credentials.accessKey}")
//    private String accessKey;
//    @Value("${cloud.aws.credentials.secretKey}")
//    private String secretKey;
//
//
//    @PostConstruct
//    private void initializeAmazon() {
//        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
//        this.s3client = new AmazonS3Client(credentials);
//    }
//
//    public String uploadFile(MultipartFile multipartFile) throws IOException {
//
//        String fileUrl = "";
//        try {
//            File file = convertMultiPartToFile(multipartFile);
//            String fileName = generateFileName(multipartFile);
//            fileUrl = endpointUrl + fileName;
//            uploadFileTos3bucket(fileName, file);
//            file.delete();
//        } catch (Exception e) {
//			e.printStackTrace();
//		}
//
//        return fileUrl;
//
//    }
//
//    public String deleteFileFromS3Bucket(String fileUrl) {
//        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
//        s3client.deleteObject(new DeleteObjectRequest(bucketName + "/", fileName));
//        return "Successfully deleted";
//    }
//
//    public void uploadFileTos3bucket(String fileName, File file) {
//        s3client.putObject(
//                new PutObjectRequest(bucketName, fileName, file));
//        //.withCannedAcl(CannedAccessControlList.PublicRead));
//    }
//
//    public File downloadFileFromS3bucket(String key) throws IOException {
//        System.out.println("work");
//        S3Object s3Object = s3client.getObject(
//                new GetObjectRequest(bucketName, key));
//        InputStream inputStream = s3Object.getObjectContent();
//        File tmp = File.createTempFile("s3test", "");
//        Files.copy(inputStream, tmp.toPath(), StandardCopyOption.REPLACE_EXISTING);
//
//        return tmp;
//    }
//
//
//    private String generateFileName(MultipartFile multiPart) {
//        return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
//    }
//
//    private File convertMultiPartToFile(MultipartFile file) throws IOException {
//        File convFile = new File(file.getOriginalFilename());
//        FileOutputStream fos = new FileOutputStream(convFile);
//        fos.write(file.getBytes());
//        fos.close();
//        return convFile;
//    }
//
//}
