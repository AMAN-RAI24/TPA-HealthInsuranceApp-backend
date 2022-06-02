package com.insurecorp.insureCorp.controlllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.gax.paging.Page;
import com.google.cloud.storage.*;
import com.insurecorp.insureCorp.entities.*;
import com.insurecorp.insureCorp.repositories.CompanyRepository;
import com.insurecorp.insureCorp.repositories.GroupPolicyRepository;
import com.insurecorp.insureCorp.repositories.UserPolicyRepository;
import com.insurecorp.insureCorp.services.LoginService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
public class UserViewPolicyController {
    @Autowired
    UserPolicyRepository userViewPolicyRepository;
    @Autowired
    GroupPolicyRepository groupPolicyRepository;
    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    UserPolicyRepository userPolicyRepository;
    @Autowired
    LoginService loginService;

    @RequestMapping(path="/get-user-policy",method= RequestMethod.GET)
    public UserPolicy getUserPolicy( @RequestHeader("Authorization") String jwt){
        User user = loginService.getUser(jwt);
        List<UserPolicy> userPolicyList=userViewPolicyRepository.findByUserOrderByUserPolicyIdDesc(user);



        GroupPolicy groupPolicy=new GroupPolicy();
//        List<GroupPolicy> groupPolicyList=groupPolicyRepository.findGroupPolicyByCompanyOrderByCreationDateDesc(user.getCompany());
//        List<GroupPolicy> groupPolicyList=groupPolicyRepository.findGroupPolicyByCompany(user.getCompany());
        List<GroupPolicy> groupPolicyList = groupPolicyRepository.findGroupPolicyByCompanyAndStatus(user.getCompany(),"APPROVED");

        GroupPolicy latest = groupPolicyList.get(0);


        for(GroupPolicy groupPolicyTemp: groupPolicyList){
            if (groupPolicyTemp.getCreationDate().compareTo(latest.getCreationDate()) >= 0){
                latest = groupPolicyTemp;
            }
        }
        if(!groupPolicyList.isEmpty() && !userPolicyList.isEmpty() ){
            if(userPolicyList.get(0).getGroupPolicy().getGroupPolicyId() == latest.getGroupPolicyId()){

                System.out.println("INSIDe if above wala");
                return userPolicyList.get(0);
            }
            else{
                UserPolicy userPolicy=new UserPolicy();
                userPolicy.setUser(user);

                //ADDIng started
                System.out.println("INSIDe elseif above wala");

                userPolicy.setGroupPolicy(latest);
                //END
                userPolicy.setUserFamilyDetails(new ArrayList<>());
                userPolicy.setCoverage(0);
                userPolicy.setUserPolicyId(1);
                return userPolicy;
            }
        } else if (!groupPolicyList.isEmpty()) {
            UserPolicy userPolicy=new UserPolicy();
            userPolicy.setUser(user);

            //ADDIng started
            System.out.println("INSIDe elseif below wala");

            userPolicy.setGroupPolicy(latest);
            //END
            userPolicy.setUserFamilyDetails(new ArrayList<>());
            userPolicy.setCoverage(0);
            userPolicy.setUserPolicyId(1);
            return userPolicy;
        }
        return null;
    }

//,consumes = { MediaType.MULTIPART_FORM_DATA_VALUE ,MediaType.ALL_VALUE}
    @RequestMapping(path="/set-user-policy",method=RequestMethod.POST,consumes = {MediaType.IMAGE_PNG_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    public List<Long> createUserPolicy(@RequestHeader("Authorization") String jwt, @RequestParam("policy") String policyString,@RequestParam("files") MultipartFile[] files) throws IOException {
        List<Long> data = new ArrayList<>();

        System.out.println("TESTING");
        System.out.println(policyString);
        ObjectMapper objectMapper = new ObjectMapper();

        System.out.println("TESTING OBJ");
        UserPolicyDTO policy = objectMapper.readValue(policyString,UserPolicyDTO.class);
//        System.out.println();

        User user=loginService.getUser(jwt);
        List<UserFamilyDetails> fdTemp = policy.getFamilyDetails();
        for(int i = 0;i<fdTemp.size();i++)    {
            Long objName = addFile(files[i]);
            data.add(objName);
            fdTemp.get(i).setImageUrl(String.valueOf(objName));
        }

        System.out.println(user.getName());
        UserPolicy userPolicy=new UserPolicy();
        userPolicy.setUser(user);
        for(MultipartFile mf:files){
            System.out.println(mf.getOriginalFilename());
//            long objName = addFile(mf);


        }
//        List<GroupPolicy> groupPolicyList=groupPolicyRepository.findGroupPolicyByCompanyOrderByCreationDateDesc(user.getCompany());
        List<GroupPolicy> groupPolicyList = groupPolicyRepository.findGroupPolicyByCompanyAndStatus(user.getCompany(),"APPROVED");
        GroupPolicy latest = groupPolicyList.get(0);


        for(GroupPolicy groupPolicyTemp: groupPolicyList){
            if (groupPolicyTemp.getCreationDate().compareTo(latest.getCreationDate()) >= 0){
                latest = groupPolicyTemp;
            }
        }

        GroupPolicy groupPolicy =  latest;


//        Finding existing user policy

        UserPolicy fetchedUserPolicy = userPolicyRepository.findByGroupPolicyAndUser(groupPolicy,user);
        if (!Objects.isNull(fetchedUserPolicy)){
          userPolicy.setUserPolicyId(fetchedUserPolicy.getUserPolicyId());
        }

        //Editing ends here
        userPolicy.setGroupPolicy(groupPolicy);
        userPolicy.setCoverage(policy.getCoverage());
        userPolicy.setUserFamilyDetails(policy.getFamilyDetails());
        userViewPolicyRepository.save(userPolicy);


        return data;
    }

    @RequestMapping(path="/getpolicy/{id}",method=RequestMethod.GET)
    public UserPolicy getUserPolicy(@PathVariable("id") int id){
        return userViewPolicyRepository.getById(id);
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
//        System.out.println(file.getName());

//        System.out.println(fileDetails.getObjectName());
//        fileDetails.setFile(new File("./a"));
//        System.out.println(fileDetails.getFile().getName());
        return timeStamp;
    }
}
