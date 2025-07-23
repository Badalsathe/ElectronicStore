package com.lcwd.electronic.store.services.serviceImpl;

import com.lcwd.electronic.store.exceptions.BadApiRequest;
import com.lcwd.electronic.store.services.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Override
    public String uploadFile(MultipartFile file, String path) throws IOException {

        String originalFilename = file.getOriginalFilename();
        logger.info("Filename : {} ", originalFilename);

        String filename = UUID.randomUUID().toString();

        // Extract extension without the dot
        String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);

        String fileNameWithExtension = filename + "." + extension;
        String fullPathwithFileName = path + fileNameWithExtension;

        logger.info("Full image path : {} ", fullPathwithFileName);

        // Validate file extension
        if (extension.equalsIgnoreCase("png") ||
                extension.equalsIgnoreCase("jpeg") ||
                extension.equalsIgnoreCase("jpg")) {

            logger.info("File extension is {} ", extension);

            File folder = new File(path);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            Files.copy(file.getInputStream(), Paths.get(fullPathwithFileName));
            return fileNameWithExtension;

        } else {
            throw new BadApiRequest("File with this ." + extension + " not allowed");
        }
    }

    @Override
    public InputStream getResource(String path, String name) throws FileNotFoundException {

        String fullPath = path + File.separator + name;
        return new FileInputStream(fullPath);
    }
}
