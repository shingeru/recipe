package com.shingeru.recipeproject.converters;

import com.shingeru.recipeproject.commands.IngridientCommand;
import com.shingeru.recipeproject.domain.Ingridient;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngridientToIngridientCommand implements Converter<Ingridient, IngridientCommand> {

    private final UnitOfMeasureToUnitTomeasureCommand unitOfMeasureToUnitTomeasureCommand;

    public IngridientToIngridientCommand(UnitOfMeasureToUnitTomeasureCommand unitOfMeasureToUnitTomeasureCommand) {
        this.unitOfMeasureToUnitTomeasureCommand = unitOfMeasureToUnitTomeasureCommand;
    }

    @Synchronized
    @Nullable
    @Override
    public IngridientCommand convert(Ingridient source) {

        if (source == null)
            return null;

        final IngridientCommand ingridientCommand = new IngridientCommand();
        ingridientCommand.setId(source.getId());
        ingridientCommand.setAmount(source.getAmount());
        if (source.getRecipe() != null)
            ingridientCommand.setRecipeId(source.getRecipe().getId());
        ingridientCommand.setDescription(source.getDescription());
        ingridientCommand.setUnitOfMeasure(unitOfMeasureToUnitTomeasureCommand.convert(source.getUom()));

        return ingridientCommand;

    }
}
