package com.shingeru.recipeproject.converters;

import com.shingeru.recipeproject.commands.IngridientCommand;
import com.shingeru.recipeproject.commands.UnitOfMeasureCommand;
import com.shingeru.recipeproject.domain.Ingridient;
import com.shingeru.recipeproject.domain.Recipe;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class IngridientCommandToIngridientTest {

    public static final Recipe RECIPE = new Recipe();
    public static final BigDecimal AMOUNT = new BigDecimal(1);
    public static final String DESCRIPTION = "Chees";
    public static final Long ID_VALUE = new Long(1L);
    public static final Long UOM_ID = new Long(2L);

    IngridientCommandToIngridient converter;

    @Before
    public void setUp() throws Exception {
        converter = new IngridientCommandToIngridient(new UnitOfMeasureCommandToUnitOfMeasure());
    }


    @Test
    public void testNullObject() {
        assertNull(converter.convert(null));
    }


    @Test
    public void testEmptyObject() {
        assertNotNull(converter.convert(new IngridientCommand()));
    }


    @Test
    public void convert() {

        //given
        IngridientCommand command = new IngridientCommand();
        command.setId(ID_VALUE);
        command.setAmount(AMOUNT);
        command.setDescription(DESCRIPTION);
        UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
        unitOfMeasureCommand.setId(UOM_ID);
        command.setUnitOfMeasure(unitOfMeasureCommand);

        //when
        Ingridient ingridient = converter.convert(command);

        //then
        assertNotNull(ingridient);
        assertNotNull(ingridient.getUom());
        assertEquals(ID_VALUE, ingridient.getId());
        assertEquals(AMOUNT, ingridient.getAmount());
        assertEquals(DESCRIPTION, ingridient.getDescription());
        assertEquals(UOM_ID, ingridient.getUom().getId());

    }

    @Test
    public void convertWithNullUom() {


        //given
        IngridientCommand command = new IngridientCommand();
        command.setId(ID_VALUE);
        command.setAmount(AMOUNT);
        command.setDescription(DESCRIPTION);

        //when
        Ingridient ingridient = converter.convert(command);

        //then
        assertNotNull(ingridient);
        assertNull(ingridient.getUom());

    }
}