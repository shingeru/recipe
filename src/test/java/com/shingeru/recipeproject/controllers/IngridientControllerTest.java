package com.shingeru.recipeproject.controllers;

import com.shingeru.recipeproject.commands.IngridientCommand;
import com.shingeru.recipeproject.commands.RecipeCommand;
import com.shingeru.recipeproject.commands.UnitOfMeasureCommand;
import com.shingeru.recipeproject.domain.UnitOfMeasure;
import com.shingeru.recipeproject.repository.UniOfMeasureRepository;
import com.shingeru.recipeproject.services.IngridientService;
import com.shingeru.recipeproject.services.RecipeService;
import com.shingeru.recipeproject.services.UnitOfMeasureService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static junit.framework.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class IngridientControllerTest {

    @Mock
    RecipeService recipeService;

    @Mock
    IngridientService ingridientService;

    @Mock
    UnitOfMeasureService unitOfMeasureService;

    @Mock
    UniOfMeasureRepository unitOfMeasureRepository;

    IngridientController controller;

    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        controller = new IngridientController(recipeService, ingridientService, unitOfMeasureService);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testListIngridients() throws Exception {

        //given
        RecipeCommand recipeCommand = new RecipeCommand();
        when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);

        //when
        mockMvc.perform(get("/recipe/1/ingridients"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingridient/list"))
                .andExpect(model().attributeExists("recipe"));


        //then
        verify(recipeService, times(1)).findCommandById(anyLong());


    }

    @Test
    public void testShowIngridient() throws Exception {
        //given
        IngridientCommand ingridientCommand = new IngridientCommand();

        //when
        when(ingridientService.findByRecipeIdAndIngridientId(anyLong(), anyLong())).thenReturn(ingridientCommand);

        //then
        mockMvc.perform(get("/recipe/1/ingridient/1/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingridient/show"))
                .andExpect(model().attributeExists("ingridient"));
    }


    @Test
    public void testUpdateIngridientForm() throws Exception {
        //given
        IngridientCommand ingridientCommand = new IngridientCommand();

        //when
        when(ingridientService.findByRecipeIdAndIngridientId(anyLong(), anyLong())).thenReturn(ingridientCommand);
        when(unitOfMeasureService.listAllUoms()).thenReturn(new HashSet<>());

        //then
        mockMvc.perform(get("/recipe/1/ingridient/2/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingridient/ingridientform"))
                .andExpect(model().attributeExists("ingridient"))
                .andExpect(model().attributeExists("uomList"));
    }

    @Test
    public void testSaveOrUpdate() throws Exception {
        //givne
        IngridientCommand command = new IngridientCommand();
        command.setId(3L);
        command.setRecipeId(2L);

        //when
        when(ingridientService.saveIngridientCommand(any())).thenReturn(command);

        //then
        mockMvc.perform(post("/recipe/2/ingridient")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "")
                .param("description", "some string")
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/2/ingridient/3/show"));


    }

    @Test
    public void testNewIngridientForm() throws Exception {
        //given
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(1L);

        //when
        when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);
        when(unitOfMeasureService.listAllUoms()).thenReturn(new HashSet<>());

        //then
        mockMvc.perform(get("/recipe/1/ingridient/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingridient/ingridientform"))
                .andExpect(model().attributeExists("ingridient"))
                .andExpect(model().attributeExists("uomList"));

        verify(recipeService, times(1)).findCommandById(anyLong());
    }


    @Test
    public void testDeleteIngridient() throws Exception {
        //given

        mockMvc.perform(get("/recipe/1/ingridient/1/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/1/ingridients"));


        verify(ingridientService, times(1)).deleteIngridientFromRecipe(anyLong(), anyLong());
    }
}