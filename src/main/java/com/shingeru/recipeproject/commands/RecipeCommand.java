package com.shingeru.recipeproject.commands;


import com.shingeru.recipeproject.domain.Difficulty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class RecipeCommand {
    private Long id;

    @NotBlank
    @Size(min = 3, max = 255)
    private String name;

    @NotBlank
    @Size(min =3, max = 255)
    private String description;

    @Min(1)
    @Max(99)
    private Integer prepTime;

    @Min(1)
    @Max(99)
    private Integer cookTime;

    @Min(1)
    @Max(99)
    private Integer serving;


    private String source;

    @URL
    private String url;

    @NotBlank
    private String directions;

    private Set<IngridientCommand> ingridients = new HashSet<>();
    private Difficulty difficulty;
    private NotesCommand notes;
    private Set<CategoryCommand> categories = new HashSet<>();
    private Byte[] image;
}
