package com.example.demo.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Repository.StudyMaterialRepository;
import com.example.demo.Utility.FileStorageProperties;
import com.example.demo.customException.FileStorageException;
import com.example.demo.customException.MyFileNotFoundException;
import com.example.demo.model.StudyMaterial;
 
@Service
@Transactional
public class StudyMaterialService {
 
    @Autowired
    private StudyMaterialRepository repo;
     
    public List<StudyMaterial> listAll() {
        return repo.findAll();
    }
     
    public StudyMaterial save(StudyMaterial StudyMaterial) {
        return repo.save(StudyMaterial);
    }
     
    public StudyMaterial get(long id) {
        return repo.findById(id).get();
    }
     
    public void delete(long id) {
        repo.deleteById(id);
    }
    
    
    // for file storage
    private  Path fileStorageLocation;

    @Autowired
    public void FileStorageService(FileStorageProperties fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir())
                .toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (Exception ex) {
            throw new FileStorageException("Could not create the directory where the uploaded files will be stored.", ex);
        }
    }

    public String storeFile(MultipartFile file) {
        // Normalize file name
		/* String fileName = StringUtils.cleanPath(file.getOriginalFilename()); */
    	String fileName = file.getOriginalFilename();

        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            // Copy file to the target location (Replacing existing file with the same name)
            Path targetLocation = this.fileStorageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Resource loadFileAsResource(String fileName) {
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
			/* Resource resource = new UrlResource(filePath.toUri()); */
            Resource resource = (Resource) new UrlResource(filePath.toUri());
            if(resource!=null) {
                return resource;
			} /*
				 * else { throw new MyFileNotFoundException("File not found " + fileName); }
				 */
        } catch (MalformedURLException ex) {
            throw new MyFileNotFoundException("File not found " + fileName, ex);
        }
        return null;
    }
}
