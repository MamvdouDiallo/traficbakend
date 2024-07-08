package com.itma.gestionProjet.services.imp;

import com.itma.gestionProjet.entities.File;
import com.itma.gestionProjet.entities.Project;
import com.itma.gestionProjet.repositories.FileRepository;
import com.itma.gestionProjet.repositories.ProjectRepository;
import com.itma.gestionProjet.services.IFileService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
public class FileService implements IFileService {

    @Autowired
    FileRepository fileRepository;


    @Autowired
    ProjectRepository projectRepository;

    @Override
    public File uplaodFile(MultipartFile file) throws IOException {
        return null;
    }
    @Override
    public File getFileDetails(Long id) throws IOException {
        return null;
    }
    @Override
    public ResponseEntity<byte[]> getFile(Long id) throws IOException {
        return null;
    }

    @Override
    public List<File> deleteFile(Long id) {
        fileRepository.deleteByProjectId(id);
        return null;
    }

    @Override
    public File uplaodFile(MultipartFile file, Long idProj) throws IOException {
        return null;
    }

    @Override
    public List<File> getFilesParProj(Long ProjId) {
        Project p = projectRepository.findById(ProjId).get();
        return p.getFiles();
    }

    @Override
    public File uplaodFileProj(MultipartFile file, Long idProd) throws IOException {
        Project p = new Project();
        p.setId(Math.toIntExact(idProd));
        return fileRepository.save(File.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .file(file.getBytes())
                .project(p).build() );
    }




    @Transactional
    @Override
    public List<File> updateFiles(MultipartFile[] files, Long idProd) throws IOException {
        Project p = new Project();
        p.setId(Math.toIntExact(idProd));

        // Supprimer les fichiers existants associés au projet
        fileRepository.deleteByProjectId(idProd);
        // Ajouter les nouveaux fichiers
        List<File> uploadedFiles = new ArrayList<>();
        for (MultipartFile file : files) {
            File uploadedFile = File.builder()
                    .name(file.getOriginalFilename())
                    .type(file.getContentType())
                    .file(file.getBytes())
                    .project(p)
                    .build();
            uploadedFiles.add(uploadedFile);
            fileRepository.save(uploadedFile);
        }
        return uploadedFiles;
    }
}

