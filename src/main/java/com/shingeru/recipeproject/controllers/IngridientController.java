package com.shingeru.recipeproject.controllers;

import com.shingeru.recipeproject.commands.IngridientCommand;
import com.shingeru.recipeproject.commands.RecipeCommand;
import com.shingeru.recipeproject.commands.UnitOfMeasureCommand;
import com.shingeru.recipeproject.services.IngridientService;
import com.shingeru.recipeproject.services.RecipeService;
import com.shingeru.recipeproject.services.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class IngridientController {

    private final RecipeService recipeService;
    private final IngridientService ingridientService;
    private final UnitOfMeasureService unitOfMeasureService;


    public IngridientController(RecipeService recipeService, IngridientService ingridientService, UnitOfMeasureService unitOfMeasureService) {
        this.recipeService = recipeService;
        this.ingridientService = ingridientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping("recipe/{id}/ingridients")
    public String listIngridients(@PathVariable String id, Model model) {

        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));

        return "recipe/ingridient/list";
    }

    @GetMapping("recipe/{recipeId}/ingridient/{id}/update")
    public String updateRecipeIngridient(@PathVariable String recipeId,
                                        @PathVariable String id,
                                        Model model) {
        model.addAttribute("ingridient", ingridientService.findByRecipeIdAndIngridientId(Long.valueOf(recipeId), Long.valueOf(id)));

        model.addAttribute("uomList", unitOfMeasureService.listAllUoms());

        return "recipe/ingridient/ingridientform";
    }

    @GetMapping("recipe/{recipeId}/ingridient/{id}/show")
    public String showRecipeIngridient(@PathVariable String recipeId,
                                       @PathVariable String id,
                                       Model model) {
        model.addAttribute("ingridient", ingridientService.findByRecipeIdAndIngridientId(Long.valueOf(recipeId), Long.valueOf(id)));

        return "recipe/ingridient/show";
    }

    @PostMapping("recipe/{recipeId}/ingridient")
    public String saveOrUpdate(@ModelAttribute IngridientCommand command) {

        log.debug("command: "+command);


        IngridientCommand savedCommand = ingridientService.saveIngridientCommand(command);

        return "redirect:/recipe/"+savedCommand.getRecipeId() +"/ingridient/" + savedCommand.getId() + "/show";
    }


    @GetMapping("recipe/{recipeId}/ingridient/new")
    public String newRecipe(@PathVariable String recipeId, Model model) {

        RecipeCommand recipeCommand = recipeService.findCommandById(Long.valueOf(recipeId));
        //todo raise exception if null

        IngridientCommand ingridientCommand = new IngridientCommand();
        ingridientCommand.setRecipeId(Long.valueOf(recipeId));
        model.addAttribute("ingridient", ingridientCommand);

        ingridientCommand.setUnitOfMeasure(new UnitOfMeasureCommand());

        model.addAttribute("uomList", unitOfMeasureService.listAllUoms());

        return "recipe/ingridient/ingridientform";
    }

    @GetMapping("recipe/{recipeId}/ingridient/{id}/delete")
    public String deleteIngridientFromRecipe(@PathVariable String recipeId,
                                            @PathVariable String id){

        ingridientService.deleteIngridientFromRecipe(Long.valueOf(recipeId), Long.valueOf(id));

        return "redirect:/recipe/" + recipeId + "/ingridients";

    }

}
