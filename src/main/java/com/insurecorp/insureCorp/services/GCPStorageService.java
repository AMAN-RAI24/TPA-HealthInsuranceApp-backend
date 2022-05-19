package com.insurecorp.insureCorp.services;

import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class GCPStorageService {
    public String uploadDocumentIntoBucket(){

        return "success";
    }
}
