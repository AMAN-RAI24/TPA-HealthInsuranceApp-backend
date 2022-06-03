package com.insurecorp.insureCorp.controlllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.gax.paging.Page;
import com.google.cloud.storage.*;
import com.insurecorp.insureCorp.entities.*;
import com.insurecorp.insureCorp.repositories.ClaimRepository;
import com.insurecorp.insureCorp.repositories.GroupPolicyRepository;
import com.insurecorp.insureCorp.repositories.HospitalRepository;
import com.insurecorp.insureCorp.repositories.UserPolicyRepository;
import com.insurecorp.insureCorp.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.insurecorp.insureCorp.services.LoginService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ClaimController {
    @Autowired
    ClaimRepository claimRepository;
    @Autowired
    LoginService loginService ;
    @Autowired
    HospitalRepository hospitalRepository;
    @Autowired
    GroupPolicyRepository groupPolicyRepository;
    @Autowired
    UserPolicyRepository userPolicyRepository;
    @RequestMapping(path = "/get-hospitals" , method=RequestMethod.GET)
    public List<Hospital> getHospitalsByTier(@RequestHeader("Authorization") String jwt){
        User user=loginService.getUser(jwt);
        GroupPolicy groupPolicy=groupPolicyRepository.findGroupPolicyByCompanyOrderByGroupPolicyIdDesc(user.getCompany()).get(0);
        return hospitalRepository.findByTierLessThanEqual(groupPolicy.getHospitalTier());
//        return hospitalRepository.findByTier(groupPolicy.getHospitalTier());
    }
    @RequestMapping(path="/get-family" , method=RequestMethod.GET)
    public List<UserFamilyDetails> getFamilyDetails(@RequestHeader("Authorization") String jwt){
        User user=loginService.getUser(jwt);
        UserFamilyDetails userFamilyDetails=new UserFamilyDetails();
        userFamilyDetails.setImageUrl("");
        userFamilyDetails.setPhoneNumber(user.getMobileNumber());
        userFamilyDetails.setName(user.getName());
        userFamilyDetails.setAge(0);
        userFamilyDetails.setRelation("self");
        List<UserFamilyDetails> x=userPolicyRepository.findByUserOrderByUserPolicyIdDesc(user).get(0).getUserFamilyDetails();
        x.add(userFamilyDetails);
        return x;
    }

    @RequestMapping(path="/claim-request",method = RequestMethod.POST,consumes = {MediaType.IMAGE_PNG_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    public String claimRequest(@RequestHeader("Authorization") String jwt, @RequestParam("claim") String claimRequest, @RequestParam("files") MultipartFile[] files) throws IOException {
        User user= loginService.getUser(jwt);
        Claim claim=new Claim();
        ObjectMapper claimObject=new ObjectMapper();
        ClaimDTO claimData=claimObject.readValue(claimRequest,ClaimDTO.class);
        claim.setUserName(user.getEmail());
        claim.setHospital(claimData.getHospital());
        claim.setClaimAmount(claimData.getClaimAmount());
        claim.setDisease(claimData.getDisease());
        claim.setDiseaseCategory(claimData.getDiseaseCategory());
        claim.setPatient(claimData.getPatient());
        List<String> docUrls=new ArrayList<>();
        System.out.println(claimData.getClaimAmount());
        for(MultipartFile file : files){
            System.out.println(file.getOriginalFilename());
            Long objName = addFile(file);
            docUrls.add(String.valueOf(objName));
        }
        claim.setDocumentUrl(docUrls);
        claimRepository.save(claim);
        return "ok";
    }

    @RequestMapping(path="/get-claims",method=RequestMethod.GET)
    public List<Claim>  getClaimsByUser(@RequestHeader("Authorization") String jwt){
        User user= loginService.getUser(jwt);
        return claimRepository.findByUserName(user.getEmail(), Sort.by("createdAt").descending());
    }

    public long addFile(MultipartFile file) throws IOException {
        long timeStamp = System.currentTimeMillis();
        String projectId="us-gcp-ame-con-116-npd-1";
        String bucketName="hu-may-prod-insure-corp";
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
        return timeStamp;
    }
}
