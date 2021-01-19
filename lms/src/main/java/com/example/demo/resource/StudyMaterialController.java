package com.example.demo.resource;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.Service.StudyMaterialService;
import com.example.demo.Utility.FileStorageService;
import com.example.demo.model.StudyMaterial;

@RestController
public class StudyMaterialController {
	@Autowired
    private StudyMaterialService studyMaterialservice;
	
	
	@Autowired
    private FileStorageService fileStorageService;
    
//    @PostMapping("/uploadFile")
//    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
//        String fileName = fileStorageService.storeFile(file);
//
//        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
//                .path("/downloadFile/")
//                .path(fileName)
//                .toUriString();
//
//        return new UploadFileResponse(fileName, fileDownloadUri,
//                file.getContentType(), file.getSize());
//    }
	
    @PostMapping("/uploadsm")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
    	StudyMaterial studyMaterial = new StudyMaterial();
        String fileName = fileStorageService.storeFile(file);
       
        String fileDownloadUri;
		long fileSize;
		String fileType ;
        try {
			fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/downloadFile/").path(fileName).toUriString();
			fileSize = file.getSize();
			fileType = file.getContentType();
			// set all required information to study Material
			studyMaterial.setFileName(fileName);
			studyMaterial.setFilePath(fileType);
			studyMaterial.setFileSize(fileSize);
			// to make each file to be uniquely identified
			String uuid = UUID.randomUUID().toString();
			studyMaterial.setUUID(uuid);
			
			studyMaterialservice.save(studyMaterial);
			
		} catch (Exception e) {
			return new ResponseEntity<>("File uploading failed ",  HttpStatus.BAD_REQUEST);
		}

        return new ResponseEntity<>("File uploaded successfully at path "+fileDownloadUri,  HttpStatus.OK);
    }

	
	@GetMapping("/sm")
	public String getStudyMaterialUploadForm() {
		return "Upload Study Material Form";
	}
	
	@PostMapping("/sm")
	  StudyMaterial uploadStudyMaterial(@RequestBody StudyMaterial StudyMaterial) {
	    return studyMaterialservice.save(StudyMaterial);
	  }
}
