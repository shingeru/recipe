package com.shingeru.recipeproject.services;

import com.shingeru.recipeproject.commands.IngridientCommand;
import com.shingeru.recipeproject.converters.IngridientCommandToIngridient;
import com.shingeru.recipeproject.converters.IngridientToIngridientCommand;
import com.shingeru.recipeproject.domain.Ingridient;
import com.shingeru.recipeproject.domain.Recipe;
import com.shingeru.recipeproject.repository.RecipeRepository;
import com.shingeru.recipeproject.repository.UniOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Service
public class IngridientServiceImpl implements IngridientService {

    private final IngridientToIngridientCommand ingridientToIngridientCommand;
    private final RecipeRepository recipeRepository;
    private final IngridientCommandToIngridient ingridientCommandToIngridient;
    private final UniOfMeasureRepository unitOfMeasureRepository;

    public IngridientServiceImpl(IngridientToIngridientCommand ingridientToIngridientCommand,
                                 RecipeRepository recipeRepository,
                                 IngridientCommandToIngridient ingridientCommandToIngridient,
                                 UniOfMeasureRepository uniOfMeasureRepository) {
        this.ingridientToIngridientCommand = ingridientToIngridientCommand;
        this.recipeRepository = recipeRepository;
        this.ingridientCommandToIngridient = ingridientCommandToIngridient;
        this.unitOfMeasureRepository = uniOfMeasureRepository;
    }

    @Override
    public IngridientCommand findByRecipeIdAndIngridientId(Long recipeId, Long ingridientId) {


        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (!recipeOptional.isPresent()) {
            //todo impl error handling
            log.error("recipe id not found. Id: " + recipeId);
        }

        Recipe recipe = recipeOptional.get();

        Optional<IngridientCommand> ingridientCommandOptional = recipe.getIngridients().stream()
                .filter(ingridient -> ingridient.getId().equals(ingridientId))
                .map(ingridient -> ingridientToIngridientCommand.convert(ingridient)).findFirst();

        if (!ingridientCommandOptional.isPresent()) {
            //todo impl error handling
            log.error("Ingridinet id not found. Id: " + ingridientId);
        }

        return ingridientCommandOptional.get();

    }

//    @Transactional
//    @Override
//    public IngridientCommand saveIngridientCommand(IngridientCommand command) {
//
//        log.debug("rcipe id in command, id:" + command.getRecipeId());
//
//        Optional<Recipe> recipeOptional = recipeRepository.findById(command.getRecipeId());
//
//        log.debug("Get from optiona. RecipeOptional: "+ recipeOptional.get().getId());
//
//        if(!recipeOptional.isPresent()){
//
//            //todo toss error if not found!
//            log.error("Recipe not found for id: " + command.getRecipeId());
//            return new IngridientCommand();
//        } else {
//            Recipe recipe = recipeOptional.get();
//
//            log.debug("Recipe from opional " + recipe);
//
//            Optional<Ingridient> ingredientOptional = recipe
//                    .getIngridients()
//                    .stream()
//                    .filter(ingredient -> ingredient.getId().equals(command.getId()))
//                    .findFirst();
//
//            if (ingredientOptional.isPresent()) {
//                Ingridient ingredientFound = ingredientOptional.get();
//                ingredientFound.setDescription(command.getDescription());
//                ingredientFound.setAmount(command.getAmount());
//                ingredientFound.setUom(unitOfMeasureRepository
//                        .findById(command.getUnitOfMeasure().getId())
//                        .orElseThrow(() -> new RuntimeException("UOM NOT FOUND"))); //todo address this
//            } else {
//                //add new Ingredient
//                Ingridient ingredient = ingridientCommandToIngridient.convert(command);
//
//                if(ingredient == null)
//                    ingredient = new Ingridient();
//
//                log.debug("ingridient after convert :"+ingredient);
//
//                log.debug("Recipe id:" +  recipe.getId());
//
//                ingredient.setRecipe(recipe);
//                recipe.getIngridients().add(ingredient);
//            }
//
//            Recipe savedRecipe = recipeRepository.save(recipe);
//
//            Optional<Ingridient> savedIngredientOptional = savedRecipe.getIngridients().stream()
//                    .filter(recipeIngredients -> recipeIngredients.getId().equals(command.getId()))
//                    .findFirst();
//
//            //check by description
//            if (!savedIngredientOptional.isPresent()) {
//                //not totally safe... But best guess
//                savedIngredientOptional = savedRecipe.getIngridients().stream()
//                        .filter(recipeIngredients -> recipeIngredients.getDescription().equals(command.getDescription()))
//                        .filter(recipeIngredients -> recipeIngredients.getAmount().equals(command.getAmount()))
//                        .filter(recipeIngredients -> recipeIngredients.getUom().getId().equals(command.getUnitOfMeasure().getId()))
//                        .findFirst();
//            }
//
//            //to do check for fail
//            return ingridientToIngridientCommand.convert(savedIngredientOptional.get());
//
//        }
//    }

    @Override
    @Transactional
    public IngridientCommand saveIngridientCommand(IngridientCommand command) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(command.getRecipeId());

        if(!recipeOptional.isPresent()){

            //todo toss error if not found!
            log.error("Recipe not found for id: " + command.getRecipeId());
            return new IngridientCommand();
        } else {
            Recipe recipe = recipeOptional.get();

            Optional<Ingridient> ingredientOptional = recipe
                    .getIngridients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(command.getId()))
                    .findFirst();

            if(ingredientOptional.isPresent()){
                Ingridient ingredientFound = ingredientOptional.get();
                ingredientFound.setDescription(command.getDescription());
                ingredientFound.setAmount(command.getAmount());
                ingredientFound.setUom(unitOfMeasureRepository
                        .findById(command.getUnitOfMeasure().getId())
                        .orElseThrow(() -> new RuntimeException("UOM NOT FOUND"))); //todo address this
            } else {
                //add new Ingredient
                Ingridient ingridient =  ingridientCommandToIngridient.convert(command);
                ingridient.setRecipe(recipe);
                recipe.getIngridients().add(ingridient);



//                recipe.getIngridients().add(ingridientCommandToIngridient.convert(command));




            }

            Recipe savedRecipe = recipeRepository.save(recipe);

            Optional<Ingridient> savedIngridientOptional = savedRecipe.getIngridients().stream()
                    .filter(recipeIngridients -> recipeIngridients.getId().equals(command.getId()))
                    .findFirst();

            //check by description
            if(!savedIngridientOptional.isPresent()) {
                //temporary - not completly safe
                savedIngridientOptional = savedRecipe.getIngridients().stream()
                        .filter(recipeIngridients -> recipeIngridients.getDescription().equals(command.getDescription()))
                        .filter(recipeIngridients -> recipeIngridients.getAmount().equals(command.getAmount()))
                        .filter(recipeIngridients -> recipeIngridients.getUom().getId().equals(command.getUnitOfMeasure().getId()  ))
                        .findFirst();

            }



            //to do check for fail
            return ingridientToIngridientCommand.convert(savedIngridientOptional.get());

//            return ingridientToIngridientCommand.convert(savedRecipe.getIngridients().stream()
//                    .filter(recipeIngredients -> recipeIngredients.getId().equals(command.getId()))
//                    .findFirst()
//                    .get());
        }

    }

    @Override
    public void deleteIngridientFromRecipe(Long recipeId, Long ingridientId) {

        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);




        if(recipeOptional.isPresent()) {

            Recipe recipe = recipeOptional.get();
            log.debug("<<< >>> Recipe is found. Id: "+recipe.getId());

            Optional<Ingridient> ingridienToRemove = recipe.getIngridients().stream()
                    .filter(recipeIngridients -> recipeIngridients.getId().equals(ingridientId))
                    .findFirst();


            if(ingridienToRemove.isPresent()) {
                log.debug("<<< >>> Ingridient is found. Id: " + ingridienToRemove.get().getId());

                ingridienToRemove.get().setRecipe(null);
                recipe.getIngridients().remove(ingridienToRemove.get());

                recipeRepository.save(recipe);

//                recipeRepository.
            }
        }

        log.debug("Recipe not found...");

        return;
    }
}
