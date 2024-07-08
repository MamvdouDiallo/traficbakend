package com.itma.gestionProjet.services;

import com.itma.gestionProjet.entities.File;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IFileService {


    File uplaodFile(MultipartFile file) throws IOException;
    File getFileDetails(Long id) throws IOException;
    ResponseEntity<byte[]> getFile(Long id) throws IOException;
    List<File> deleteFile(Long id) ;
    

    File uplaodFileProj(MultipartFile file,Long idProd) throws IOException;

    File uplaodFile(MultipartFile file, Long idProj) throws IOException;

    List<File> getFilesParProj(Long prodId);


    List<File> updateFiles(MultipartFile[] files, Long idProd) throws IOException;
}
