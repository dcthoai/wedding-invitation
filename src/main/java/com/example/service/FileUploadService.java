package com.example.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.NotDirectoryException;
import java.util.UUID;

@Service
public class FileUploadService {

    @Value("${resources.uploads.path}")
    private String uploadPath;

    public Boolean createDirectoryForUpload() throws NotDirectoryException {
        File newDirectory = new File(uploadPath);

        if (!newDirectory.exists()) {
            String currentWorkingDirectory = System.getProperty("user.dir") + "/uploads/";

            if (currentWorkingDirectory.equals(uploadPath))
                return newDirectory.mkdirs();
            else
                throw new NotDirectoryException("Invalid URL: the directory location you requested does not exist.");
        }

        return true;
    }

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

    public String saveFile(MultipartFile file) throws IOException {
        // Check that the file from the client request is not empty
        if (isUploadFile(file) && file.getOriginalFilename() != null) {
            if (createDirectoryForUpload()) {
                String fileName = generateUniqueFileName(file.getOriginalFilename());
                String relativePath = "uploads" + File.separator + fileName;
                String path = uploadPath + File.separator + fileName;

                // Save file to path
                File newFile = new File(path);
                file.transferTo(newFile);

                return relativePath;
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
