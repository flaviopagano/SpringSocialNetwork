package co.develope.SpringSocialNetwork.services;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
public class FileStorageService {

    private Logger logger = LoggerFactory.getLogger(FileStorageService.class);

    @Value("${fileRepositoryFolderPosts}")
    private String folderNamePosts;

    @Value("${fileRepositoryFolderProfilePictures}")
    private String folderNameProfilePictures;

    private String getFileRepositoryFolderForPosts() {
        return System.getProperty("user.home") + File.separator + "Desktop"+ File.separator + folderNamePosts;
    }

    private String getFileRepositoryFolderForProfilePictures(){
        return System.getProperty("user.home") + File.separator + "Desktop"+
                File.separator + folderNamePosts + File.separator + folderNameProfilePictures;
    }

    public String upload(MultipartFile file, boolean isAPost) throws IOException {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String newFileName = UUID.randomUUID().toString();
        String completeFileName = newFileName + "." + extension;

        logger.debug("Saving new file with name " + newFileName + " and extension " + extension);
        logger.debug("Complete file name: " + completeFileName);

        if(isAPost){
            File finalFolder = new File(getFileRepositoryFolderForPosts());
            if(!finalFolder.exists()) throw new IOException("Final folder does not exists");
            if(!finalFolder.isDirectory()) throw new IOException("Final folder is not a directory");
            File finalDestination = new File(getFileRepositoryFolderForPosts() + File.separator + completeFileName);
            if(finalDestination.exists()) throw new IOException("File conflict");
            logger.debug("File correctly saved");
            file.transferTo(finalDestination);
            return completeFileName;
        }

        File finalFolder = new File(getFileRepositoryFolderForProfilePictures());
        if(!finalFolder.exists()) throw new IOException("Final folder does not exists");
        if(!finalFolder.isDirectory()) throw new IOException("Final folder is not a directory");
        File finalDestination = new File(getFileRepositoryFolderForProfilePictures() + File.separator + completeFileName);
        if(finalDestination.exists()) throw new IOException("File conflict");
        logger.debug("File correctly saved");
        file.transferTo(finalDestination);
        return completeFileName;

    }

    public List<String> uploadMany(MultipartFile[] files) throws IOException {
        List<String> myFiles = new ArrayList<>();
        for(int i = 0; i < files.length; i++){
            myFiles.add(upload(files[i],true));
        }
        return myFiles;
    }

    public byte[] download(String fileName, boolean isAPost) throws IOException {
        if(isAPost){
            File fileFromRepository = new File(getFileRepositoryFolderForPosts() + File.separator + fileName);
            if(!fileFromRepository.exists()) throw new IOException("File " + fileName + " does not exists");
            return IOUtils.toByteArray(new FileInputStream(fileFromRepository));
        }

        File fileFromRepository = new File(getFileRepositoryFolderForProfilePictures() + File.separator + fileName);
        if(!fileFromRepository.exists()) throw new IOException("File " + fileName + " does not exists");
        return IOUtils.toByteArray(new FileInputStream(fileFromRepository));

    }

}
