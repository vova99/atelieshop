package com.oksanapiekna.atelieshop.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface AmazonClientService {
    String uploadFile(MultipartFile multipartFile) throws IOException;
    String deleteFileFromS3Bucket(String fileUrl);
    File downloadFileFromS3bucket(String key) throws IOException;
}
