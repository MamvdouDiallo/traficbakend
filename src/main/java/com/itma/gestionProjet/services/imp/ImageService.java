package com.itma.gestionProjet.services.imp;

import com.itma.gestionProjet.entities.Image;
import com.itma.gestionProjet.exceptions.ImageNotFoundException;
import com.itma.gestionProjet.repositories.ImageRepository;
import com.itma.gestionProjet.repositories.UserRepository;
import com.itma.gestionProjet.services.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@Service
public class ImageService  implements IImageService {


    @Autowired
    ImageRepository imageRepository;


    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Override
    public Image uplaodImage(MultipartFile file) throws IOException {
        /*
         * Ce code commenté est équivalent au code utilisant le design pattern Builder
         * Image image = new Image(null, file.getOriginalFilename(),
         * file.getContentType(), file.getBytes(), null);
         *   return imageRepository.save(image);
         */
        return imageRepository.save(Image.builder().name(file.getOriginalFilename()).type(file.getContentType())
                .image(file.getBytes()).build());
    }

    @Override
    public Image updateImage(Long imageId, MultipartFile file) throws IOException {
        // Vérifier si l'image existe
        Image currentImage = imageRepository.findById(imageId)
                .orElseThrow(() -> new ImageNotFoundException("Image not found with id " + imageId));

        // Mettre à jour les propriétés de l'image existante avec les nouvelles données
        currentImage.setName(file.getOriginalFilename());
        currentImage.setType(file.getContentType());
        currentImage.setImage(file.getBytes());

        // Enregistrer et retourner l'image mise à jour
        return imageRepository.save(currentImage);
    }


    @Override
    public Image getImageDetails(Long id) throws IOException {
        return null;
    }

    @Override
    public ResponseEntity<byte[]> getImage(Long id) throws IOException {
        return null;
    }

    @Override
    public void deleteImage(Long id) {

    }

    @Override
    public Image uplaodImageProd(MultipartFile file, Long idProd) throws IOException {
        return null;
    }

    @Override
    public List<Image> getImagesParProd(Long prodId) {
        return null;
    }
}
