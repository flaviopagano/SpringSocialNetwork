package co.develope.SpringSocialNetwork.services;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;


@Service
public class FileStorageService {

    @Value("${fileRepositoryFolder}")
    private String folderName;

    public String upload(MultipartFile file) throws IOException {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String newFileName = UUID.randomUUID().toString();
        String completeFileName = newFileName + "." + extension;

        String fileRepositoryFolder = System.getProperty("user.home") + "/Desktop/" + folderName;

        File finalFolder = new File(fileRepositoryFolder);
        if(!finalFolder.exists()) throw new IOException("Final folder does not exists");
        if(!finalFolder.isDirectory()) throw new IOException("Final folder is not a directory");

        File finalDestination = new File(fileRepositoryFolder + File.separator + completeFileName);
        if(finalDestination.exists()) throw new IOException("File conflict");

        file.transferTo(finalDestination);
        return completeFileName;
    }

}
