package com.shingeru.recipeproject.services;

import com.shingeru.recipeproject.commands.UnitOfMeasureCommand;
import com.shingeru.recipeproject.converters.UnitOfMeasureToUnitTomeasureCommand;
import com.shingeru.recipeproject.domain.UnitOfMeasure;
import com.shingeru.recipeproject.repository.UniOfMeasureRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class UnitOfMeasureServiceImplTest {

    UnitOfMeasureToUnitTomeasureCommand unitOfMeasureToUnitTomeasureCommand = new UnitOfMeasureToUnitTomeasureCommand();
    UnitOfMeasureService service;

    @Mock
    UniOfMeasureRepository uniOfMeasureRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        service = new UnitOfMeasureServiceImpl(unitOfMeasureToUnitTomeasureCommand, uniOfMeasureRepository);

    }

    @Test
    public void listAllUoms() {


            //given
            Set<UnitOfMeasure> unitOfMeasures = new HashSet<>();

            UnitOfMeasure uom1 = new UnitOfMeasure();
            uom1.setId(1L);
            unitOfMeasures.add(uom1);
            UnitOfMeasure uom2 = new UnitOfMeasure();
            uom2.setId(2L);
            unitOfMeasures.add(uom2);

            when(uniOfMeasureRepository.findAll()).thenReturn(unitOfMeasures);

            //when
            Set<UnitOfMeasureCommand> commands = service.listAllUoms();

            //then
            assertEquals(2, commands.size());
            verify(uniOfMeasureRepository, times(1)).findAll();

    }
}