package com.springboot.controller;

import com.springboot.dto.ImageUploadResponse;
import com.springboot.entity.Image;
import com.springboot.repository.ImageRepository;
import com.springboot.util.ImageUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Arrays;
import java.util.List;

@RestController
public class ImageController {
    private static final List<String> ALLOWED_IMAGE_TYPES = Arrays.asList("image/jpeg", "image/png");

    private final ImageRepository imageRepository;

    @Autowired
    public ImageController(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @PostMapping("/upload/image")
    public ResponseEntity<?> uploadImage(
            @Valid @RequestParam("image") MultipartFile file
    )
            throws IOException {

        if (file.isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Please select an image file.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        if (!isValidImageType(file)) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Incorrect format, accept only jpeg and png file.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        // Perform custom file size validation
        long maxSizeBytes = 5 * 1024 * 1024; // 5MB, default is Byte
        if (file.getSize() > maxSizeBytes) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Image file size must be less than 5MB.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        imageRepository.save(Image.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .image(ImageUtil.compressImage(file.getBytes())).build());
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ImageUploadResponse("Image uploaded successfully: " +
                        file.getOriginalFilename()));
    }

    @GetMapping(path = {"/get/image/info/{name}"})
    public Image getImageDetails(@PathVariable("name") String name) throws IOException {

        final Optional<Image> dbImage = imageRepository.findByName(name);

        return Image.builder()
                .name(dbImage.get().getName())
                .type(dbImage.get().getType())
                .image(ImageUtil.decompressImage(dbImage.get().getImage())).build();
    }

    @GetMapping(path = {"/get/image/{name}"})
    public ResponseEntity<byte[]> getImage(@PathVariable("name") String name) throws IOException {

        final Optional<Image> dbImage = imageRepository.findByName(name);

        return ResponseEntity
                .ok()
                .contentType(MediaType.valueOf(dbImage.get().getType()))
                .body(ImageUtil.decompressImage(dbImage.get().getImage()));
    }

    @GetMapping(path = {"/image/{id}"})
    public ResponseEntity<byte[]> getImageById(@PathVariable("id") Long id) throws IOException {

        final Optional<Image> dbImage = imageRepository.findById(id);

        return ResponseEntity
                .ok()
                .contentType(MediaType.valueOf(dbImage.get().getType()))
                .body(ImageUtil.decompressImage(dbImage.get().getImage()));
    }

    private boolean isValidImageType(MultipartFile imageFile) {
        String contentType = imageFile.getContentType();
        return contentType != null && ALLOWED_IMAGE_TYPES.contains(contentType);
    }
}
