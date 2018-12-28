package com.shingeru.recipeproject.services;

import com.shingeru.recipeproject.commands.IngridientCommand;
import com.shingeru.recipeproject.converters.IngridientCommandToIngridient;
import com.shingeru.recipeproject.converters.IngridientToIngridientCommand;
import com.shingeru.recipeproject.converters.UnitOfMeasureToUnitTomeasureCommand;
import com.shingeru.recipeproject.domain.Ingridient;
import com.shingeru.recipeproject.domain.Recipe;
import com.shingeru.recipeproject.repository.RecipeRepository;
import com.shingeru.recipeproject.repository.UniOfMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@Slf4j
public class IngridientServiceImplTest {
    
    private final IngridientToIngridientCommand ingridientToIngridientCommand;
    
    
    @Mock
    RecipeRepository recipeRepository;
    
    IngridientService ingridientService;

    @Mock
    IngridientCommandToIngridient ingridientCommandToIngridient;

    @Mock
    UniOfMeasureRepository uniOfMeasureRepository;

    public IngridientServiceImplTest() {
          this.ingridientToIngridientCommand = new IngridientToIngridientCommand(new UnitOfMeasureToUnitTomeasureCommand());
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        
        ingridientService = new IngridientServiceImpl(ingridientToIngridientCommand, recipeRepository, ingridientCommandToIngridient, uniOfMeasureRepository);
    }

    @Test
    public void testSaveRecipeCommand() {
        //given
        IngridientCommand command = new IngridientCommand();
        command.setId(3L);
        command.setRecipeId(2L);

        Optional<Recipe> recipeOptional = Optional.of(new Recipe());


        Recipe savedRecipe = new Recipe();
        savedRecipe.getIngridients().add(new Ingridient());
        savedRecipe.getIngridients().iterator().next().setId(3L);


        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
        when(recipeRepository.save(any())).thenReturn(savedRecipe);

        //when
//        IngridientCommand savedCommand = ingridientService.saveIngridientCommand(command);

//        //then
//        assertEquals(Long.valueOf(3L), savedCommand.getId());
//        verify(recipeRepository, times(1)).findById(anyLong());
//        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }

    @Test
    public void findByRecipeIdAndIngridientId() {
    }

    @Test
    public void findByRecipeIdAndRecipeHappyPath() {
        //given
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        Ingridient ingridient1 = new Ingridient();
        ingridient1.setId(1L);
        ingridient1.setRecipe(recipe);
        Ingridient ingridient2 = new Ingridient();
        ingridient2.setId(2L);
        ingridient2.setRecipe(recipe);
        Ingridient ingridient3 = new Ingridient();
        ingridient3.setId(3L);
        ingridient3.setRecipe(recipe);

        recipe.getIngridients().add(ingridient1);
        recipe.getIngridients().add(ingridient2);
        recipe.getIngridients().add(ingridient3);

        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);


        //when
        IngridientCommand ingridientCommand = ingridientService.findByRecipeIdAndIngridientId(1L, 3L);


        //then
        assertEquals(Long.valueOf(3L), ingridientCommand.getId());
        assertEquals(Long.valueOf(1L), ingridientCommand.getRecipeId());
        verify(recipeRepository, times(1)).findById(anyLong());


    }

    @Test
    public void testDeleteIngridientFromRecipe() {

        //given
        Recipe recipe = new Recipe();
        recipe.setId(1L);

        Ingridient ingridient = new Ingridient();
        ingridient.setId(1L);
        ingridient.setRecipe(recipe);

        recipe.getIngridients().add(ingridient);

        Optional<Recipe> recipeOptional = Optional.of(recipe);

//        recipeRepository.save(recipe);
        when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);

        //when
        ingridientService.deleteIngridientFromRecipe(1L, 1L);

        //then
        assertEquals(0, recipe.getIngridients().size());

        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }
}