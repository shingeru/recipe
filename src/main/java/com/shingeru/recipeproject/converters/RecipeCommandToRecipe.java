package com.shingeru.recipeproject.converters;

import com.shingeru.recipeproject.commands.RecipeCommand;
import com.shingeru.recipeproject.domain.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {

    private final NotesCommandToNotes notesCommandToNotes;
    private final IngridientCommandToIngridient ingridientCommandToIngridient;
    private final CategoryCommandtoCategory categoryCommandtoCategory;

    public RecipeCommandToRecipe(NotesCommandToNotes notesCommandToNotes, IngridientCommandToIngridient ingridientCommandToIngridient, CategoryCommandtoCategory categoryCommandtoCategory) {
        this.notesCommandToNotes = notesCommandToNotes;
        this.ingridientCommandToIngridient = ingridientCommandToIngridient;
        this.categoryCommandtoCategory = categoryCommandtoCategory;
    }

    @Synchronized
    @Nullable
    @Override
    public Recipe convert(RecipeCommand source) {
        if(source == null)
            return null;

        final Recipe recipe = new Recipe();
        recipe.setId(source.getId());
        recipe.setServing(source.getServing());
        recipe.setPrepTime(source.getPrepTime());
        recipe.setDifficulty(source.getDifficulty());
        recipe.setNotes(notesCommandToNotes.convert(source.getNotes()));
        recipe.setCookTime(source.getCookTime());
        recipe.setName(source.getName());
        recipe.setDescription(source.getDescription());
        recipe.setSource(source.getSource());
        recipe.setUrl(source.getUrl());


        if(source.getCategories() != null && source.getCategories().size() > 0) {
            source.getCategories().forEach(category -> recipe.getCategories().add(categoryCommandtoCategory.convert(category)));
        }


        if(source.getIngridients() != null && source.getIngridients().size() > 0) {
            source.getIngridients().forEach(ingridient -> recipe.getIngridients().add(ingridientCommandToIngridient.convert(ingridient)));
        }

        return recipe;
    }
}
