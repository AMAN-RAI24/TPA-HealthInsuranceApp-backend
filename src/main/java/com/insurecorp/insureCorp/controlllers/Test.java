package com.insurecorp.insureCorp.controlllers;

import com.google.api.gax.paging.Page;
import com.google.cloud.storage.*;

import com.insurecorp.insureCorp.entities.User;
import com.insurecorp.insureCorp.repositories.UserRepository;
import com.insurecorp.insureCorp.requestModels.TestFileUploadRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;


@RestController
public class Test {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/hw")
    public String helloWorld()
    {
        return "Hello World";
    }


    @GetMapping("/get/{id}")
    User getUser(@PathVariable Integer id){
        return userRepository.findById(id).get();
    }
    @PostMapping(value = "/test-file",consumes = MediaType.ALL_VALUE)
    public void addFile(@RequestBody MultipartFile file) throws IOException {
        String projectId="us-gcp-ame-con-116-npd-1";
        String bucketName="hu-may-prod-insure-corp";
        System.out.println("dsfsadf");
        System.out.println(file.getOriginalFilename());




        Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
        BlobId blobId = BlobId.of(bucketName, "uploaded_image");
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        storage.create(blobInfo, file.getBytes());
        System.out.println(
                "File " + file.getOriginalFilename() + " uploaded to bucket " + bucketName + " as " + "uploaded_image");

        Storage storage1 = StorageOptions.getDefaultInstance().getService();
        System.out.println("Buckets:");
        Page<Bucket> buckets = storage1.list();
        for (Bucket bucket : buckets.iterateAll()) {
            System.out.println(bucket.toString());
        }
//        System.out.println(file.getName());

//        System.out.println(fileDetails.getObjectName());
//        fileDetails.setFile(new File("./a"));
//        System.out.println(fileDetails.getFile().getName());
    }
    @GetMapping("/list-buckets")
    void listBuckets()
    {
        Storage storage1 = StorageOptions.getDefaultInstance().getService();
        System.out.println("Buckets:");
        Page<Bucket> buckets = storage1.list();
        for (Bucket bucket : buckets.iterateAll()) {
            System.out.println(bucket.toString());
        }
    }
}
