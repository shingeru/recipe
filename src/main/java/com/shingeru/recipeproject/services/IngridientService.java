package com.shingeru.recipeproject.services;

import com.shingeru.recipeproject.commands.IngridientCommand;

public interface IngridientService {

    IngridientCommand findByRecipeIdAndIngridientId(Long recipeId, Long ingridientId);
    IngridientCommand saveIngridientCommand(IngridientCommand command);
    void deleteIngridientFromRecipe(Long recipeId, Long ingridientId);

}
