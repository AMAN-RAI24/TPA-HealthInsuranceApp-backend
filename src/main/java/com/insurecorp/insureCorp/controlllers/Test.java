package com.insurecorp.insureCorp.controlllers;

import com.google.api.gax.paging.Page;
import com.google.cloud.storage.*;

import com.insurecorp.insureCorp.entities.GroupPolicy;
import com.insurecorp.insureCorp.entities.User;
import com.insurecorp.insureCorp.repositories.GroupPolicyRepository;
import com.insurecorp.insureCorp.repositories.UserRepository;
import com.insurecorp.insureCorp.requestModels.TestFileUploadRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;

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
    @Autowired
    GroupPolicyRepository groupPolicyRepository;

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
        long timeStamp = System.currentTimeMillis();
        String projectId="us-gcp-ame-con-116-npd-1";
        String bucketName="hu-may-prod-insure-corp";
        System.out.println("dsfsadf");
        System.out.println(file.getOriginalFilename());

        Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
        BlobId blobId = BlobId.of(bucketName, String.valueOf(timeStamp));
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        storage.create(blobInfo, file.getBytes());
        System.out.println("File " + file.getOriginalFilename() + " uploaded to bucket " + bucketName + " as " + timeStamp);

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

    @GetMapping(value = "/getImage",produces = MediaType.IMAGE_JPEG_VALUE)
    ResponseEntity<Resource> view(@RequestParam Long id){
        String projectId="us-gcp-ame-con-116-npd-1";
        String bucketName="hu-may-prod-insure-corp";

       String destFilePath;  // The ID of your GCP project    // String projectId = "your-project-id";    // The ID of your GCS bucket    // String bucketName = "your-unique-bucket-name";    // The ID of your GCS object    // String objectName = "your-object-name";    // The path to which the file should be downloaded    // String destFilePath = "/local/path/to/file.txt";
//        objectName = "uploaded_image";
//        destFilePath = "/home/vipinkumar6/d";
        Storage storage = StorageOptions.newBuilder().setProjectId(projectId).build().getService();
        Blob blob = storage.get(BlobId.of(bucketName, String.valueOf(id)));
//        long blobLength = (long) blob.getSize()
        byte[] stream = blob.getContent();
        ByteArrayResource inputStream = new ByteArrayResource(stream);


//        blob.downloadTo(Paths.get(destFilePath));
//        System.out.println(        "Downloaded object "            + objectName
//            + " from bucket name "            + bucketName
//                    + " to "            + destFilePath);
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentLength(inputStream.contentLength())
                .body(inputStream);
    }
    @GetMapping("/fixStatus")
    String fixStatus(){
        List<GroupPolicy> gp =  groupPolicyRepository.findGroupPolicyByStatus(null);
        for(GroupPolicy g: gp){
            g.setStatus("PENDING");
            groupPolicyRepository.save(g);
        }
        return "FIXED";
    }
}
