package co.develope.SpringSocialNetwork.services.fileStorageServices;

import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
public class PostStorageService {

    String desktopPath = System.getProperty("user.home") + File.separator + "Desktop";
    String postImagesPath = desktopPath + File.separator + "filerepository" + File.separator + "post";

    public String upload(MultipartFile file) throws IOException{
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        String newFileName = UUID.randomUUID().toString();
        String completeFileName = newFileName+"."+extension;
        File finalFolder = new File(postImagesPath);

        if(!finalFolder.exists()) throw  new IOException("Post folder does not exist");
        if(!finalFolder.isDirectory())throw  new IOException("Post folder is not a directory");
        File finalDestination  = new File(postImagesPath + "\\" + newFileName + "." + extension); //creo 1 file vuoto con la destinazione

        if(finalDestination.exists()) throw  new IOException("file conflict");
        file.transferTo(finalDestination);   //lo riempio con il file che ho dato io
        return completeFileName;
    }




}
