package com.shingeru.recipeproject.services;

import com.shingeru.recipeproject.domain.Recipe;
import com.shingeru.recipeproject.repository.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Slf4j
public class ImageServiceImpl implements ImageService{

    private final RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    @Transactional
    public void saveImageFile(Long id, MultipartFile file) {

        try {
            Recipe recipe = recipeRepository.findById(id).get();

            Byte[] bytesObjects = new Byte[file.getBytes().length];

            int i =0;

            for (byte b: file.getBytes()) {
                bytesObjects[i++] = b;
            }

            recipe.setImage(bytesObjects);


        } catch (IOException e) {
            log.error("Error: " + e.getMessage());
            e.printStackTrace();
        }


    }
}
