package com.shingeru.recipeproject.converters;

import com.shingeru.recipeproject.commands.IngridientCommand;
import com.shingeru.recipeproject.domain.Ingridient;
import com.shingeru.recipeproject.domain.Recipe;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngridientCommandToIngridient implements Converter<IngridientCommand, Ingridient> {


    private final UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure;

    public IngridientCommandToIngridient(UnitOfMeasureCommandToUnitOfMeasure unitOfMeasureCommandToUnitOfMeasure) {
        this.unitOfMeasureCommandToUnitOfMeasure = unitOfMeasureCommandToUnitOfMeasure;
    }

    @Nullable
    @Override
    public Ingridient convert(IngridientCommand source) {
        if (source == null)
            return null;

        final Ingridient ingridient = new Ingridient();
        ingridient.setDescription(source.getDescription());

        if(source.getRecipeId() != null){
            Recipe recipe = new Recipe();
            recipe.setId(source.getRecipeId());
            ingridient.setRecipe(recipe);
            recipe.getIngridients().add(ingridient);
        }


        ingridient.setAmount(source.getAmount());
        ingridient.setId(source.getId());
        ingridient.setUom(unitOfMeasureCommandToUnitOfMeasure.convert(source.getUnitOfMeasure()));


        return ingridient;
    }
}
