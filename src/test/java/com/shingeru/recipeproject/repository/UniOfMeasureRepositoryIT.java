package com.shingeru.recipeproject.repository;

import com.shingeru.recipeproject.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UniOfMeasureRepositoryIT {

    @Autowired
    UniOfMeasureRepository uniOfMeasureRepository;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void findByDescription() throws Exception {

        Optional<UnitOfMeasure> oumOptional = uniOfMeasureRepository.findByDescription("Teaspoon");


        assertEquals("Teaspoon", oumOptional.get().getDescription());
    }

    @Test
    public void findByDescriptionCup() throws Exception {

        Optional<UnitOfMeasure> oumOptional = uniOfMeasureRepository.findByDescription("Cup");


        assertEquals("Cup", oumOptional.get().getDescription());
    }
}