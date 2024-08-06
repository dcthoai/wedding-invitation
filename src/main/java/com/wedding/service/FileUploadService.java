package com.wedding.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileUploadService {

    @Value("${resources.uploads.path}")
    private String uploadPath;

    public boolean isUploadFile(MultipartFile file) {
        return file != null && !file.isEmpty();
    }

    public boolean isUploadFile(MultipartFile[] files) {
        return files != null && files.length > 0;
    }

    private String generateUniqueFileName(String originalFileName) {
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        return UUID.randomUUID() + extension;
    }

    public String saveFile(MultipartFile file) {
        // Check that the file from the client request is not empty
        if (isUploadFile(file) && file.getOriginalFilename() != null) {
            try {
                String fileName = generateUniqueFileName(file.getOriginalFilename());
                String relativePath = "uploads" + File.separator + fileName;
                String path = uploadPath + File.separator + fileName;

                // Save file to path
                File newFile = new File(path);
                file.transferTo(newFile);

                return relativePath;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        return null;
    }

    public boolean deleteFile(String filePath) {
        if (filePath == null || filePath.isEmpty())
            return false;

        String fileName = filePath.substring(filePath.lastIndexOf(File.separator) + 1);
        String path = uploadPath + File.separator + fileName;
        File file = new File(path);

        if (file.exists())
            return file.delete();   // True if delete success

        return false;
    }
}
