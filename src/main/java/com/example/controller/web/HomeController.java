package com.example.controller.web;

import com.example.entity.ResponseJSON;
import com.example.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(value = {"", "/"})
public class HomeController {
    @GetMapping
    @ResponseBody
    public String homepage() {
        String currentWorkingDirectory = System.getProperty("user.dir");
        return "Current working directory: " + currentWorkingDirectory;
    }

    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) {
        if (fileUploadService.isUploadFile(file)) {
            try {
                String filePath = fileUploadService.saveFile(file);

                return ResponseJSON.ok(filePath);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseJSON.serverError(e.getMessage());
            }
        } else {
            return ResponseJSON.badRequest("No such file in request");
        }
    }
}
