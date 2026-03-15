package com.wl.big_things.controller;

import com.wl.big_things.pojo.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
public class FileUploadController {
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) throws IOException {
        String originalFilename=file.getOriginalFilename();
        String filename= UUID.randomUUID().toString()+originalFilename.substring(originalFilename.lastIndexOf("."));
        file.transferTo(new File("E:\\垃圾代码\\big_things\\src\\main\\resources\\files\\"+filename));
        return Result.success("Url地址...");
    }
}
