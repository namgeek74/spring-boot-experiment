package com.springboot.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public class ImageUploadDTO {
    // these annotation not work because they don't support for MultipartFile so far
    // it throw 500 error
    @NotEmpty(message = "Please select an image file.")
    @Size(max = 5 * 1024 * 1024, message = "Image file size must be less than 5MB.")
    private MultipartFile image;

    // Getters and setters

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}
