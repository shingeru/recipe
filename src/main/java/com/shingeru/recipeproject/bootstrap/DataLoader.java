package com.shingeru.recipeproject.bootstrap;

import com.shingeru.recipeproject.domain.*;
import com.shingeru.recipeproject.repository.CategoryRepository;
import com.shingeru.recipeproject.repository.RecipeRepository;
import com.shingeru.recipeproject.repository.UniOfMeasureRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.*;

@Component
public class DataLoader implements ApplicationListener<ContextRefreshedEvent> {



    private final RecipeRepository recipeRepository;
    private final CategoryRepository categoryRepository;
    private final UniOfMeasureRepository uniOfMeasureRepository;
//    private final In


    public DataLoader(RecipeRepository recipeRepository, CategoryRepository categoryRepository, UniOfMeasureRepository uniOfMeasureRepository) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.uniOfMeasureRepository = uniOfMeasureRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        recipeRepository.saveAll(getRecipes());

    }


    //    @Override
//    public void run(String... args) throws Exception {
//
//
//        getRecipes();
//
//
//    }

    private List<Recipe> getRecipes() {

        List<Recipe> recipeList = new ArrayList<>();

        Optional<Category> categoryOptional = categoryRepository.findByDescription("Mexican");
        Optional<UnitOfMeasure> unitOfMeasureOptional = uniOfMeasureRepository.findByDescription("Tablespoon");


        Recipe chickenTacos = new Recipe();
        chickenTacos.setName("Spicy Grilled Chicken Tacos");
        chickenTacos.setCategories(new HashSet<>(Arrays.asList(categoryOptional.get())));
        chickenTacos.setCookTime(60);
//        chickenTacos.setDifficulty();
        chickenTacos.setDescription("1 Prepare a gas or charcoal grill for medium-high, direct heat.\n" +
                "\n" +
                "2 Make the marinade and coat the chicken: In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over.\n" +
                "\n" +
                "Set aside to marinate while the grill heats and you prepare the rest of the toppings.");

        Set<Ingridient> tacosIngridients = new HashSet<>();

        Ingridient anchoChilliPowder = new Ingridient();
        anchoChilliPowder.setAmount(new BigDecimal(2.0));
        anchoChilliPowder.setUom(unitOfMeasureOptional.get());
        anchoChilliPowder.setRecipe(chickenTacos);
        anchoChilliPowder.setDescription("ancho chili powder");

        tacosIngridients.add(anchoChilliPowder);


        Notes tacosNotes = new Notes();
        tacosNotes.setRecipe(chickenTacos);
        tacosNotes.setRecipeNotes("1 Prepare a gas or charcoal grill for medium-high, direct heat.\n" +
                "\n" +
                "2 Make the marinade and coat the chicken: In a large bowl, stir together the chili powder, oregano, cumin, sugar, salt, garlic and orange zest. Stir in the orange juice and olive oil to make a loose paste. Add the chicken to the bowl and toss to coat all over.\n" +
                "\n" +
                "Set aside to marinate while the grill heats and you prepare the rest of the toppings. 3 Grill the chicken: Grill the chicken for 3 to 4 minutes per side, or until a thermometer inserted into the thickest part of the meat registers 165F. Transfer to a plate and rest for 5 minutes.\n" +
                "\n" +
                "4 Warm the tortillas: Place each tortilla on the grill or on a hot, dry skillet over medium-high heat. As soon as you see pockets of the air start to puff up in the tortilla, turn it with tongs and heat for a few seconds on the other side.\n" +
                "\n" +
                "Wrap warmed tortillas in a tea towel to keep them warm until serving.\n" +
                "\n" +
                "5 Assemble the tacos: Slice the chicken into strips. On each tortilla, place a small handful of arugula. Top with chicken slices, sliced avocado, radishes, tomatoes, and onion slices. Drizzle with the thinned sour cream. Serve with lime wedges.");


        chickenTacos.setNotes(tacosNotes);

        chickenTacos.setIngridients(tacosIngridients);


        chickenTacos.setDifficulty(Difficulty.MODERATE);

        chickenTacos.setPrepTime(45);
        chickenTacos.setServing(4);

//        recipeRepository.save(chickenTacos);

        recipeList.add(chickenTacos);

        return recipeList;
    }
}
