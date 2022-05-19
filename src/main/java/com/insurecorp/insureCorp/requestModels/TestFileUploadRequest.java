package com.insurecorp.insureCorp.requestModels;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;

@Data
@NoArgsConstructor
public class TestFileUploadRequest {
private String objectName;
private File file;
}
