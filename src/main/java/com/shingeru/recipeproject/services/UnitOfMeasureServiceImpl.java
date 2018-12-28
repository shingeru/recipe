package com.shingeru.recipeproject.services;

import com.shingeru.recipeproject.commands.UnitOfMeasureCommand;
import com.shingeru.recipeproject.converters.UnitOfMeasureToUnitTomeasureCommand;
import com.shingeru.recipeproject.repository.UniOfMeasureRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UnitOfMeasureServiceImpl implements  UnitOfMeasureService{

    private final UnitOfMeasureToUnitTomeasureCommand unitOfMeasureToUnitTomeasureCommand;
    private final UniOfMeasureRepository uniOfMeasureRepository;


    public UnitOfMeasureServiceImpl(UnitOfMeasureToUnitTomeasureCommand unitOfMeasureToUnitTomeasureCommand, UniOfMeasureRepository uniOfMeasureRepository) {
        this.unitOfMeasureToUnitTomeasureCommand = unitOfMeasureToUnitTomeasureCommand;
        this.uniOfMeasureRepository = uniOfMeasureRepository;
    }

    @Override
    public Set<UnitOfMeasureCommand> listAllUoms() {

//        Set<UnitOfMeasureCommand> unitOfMeasureCommands = new HashSet<>();
//        uniOfMeasureRepository.findAll().iterator().forEachRemaining(unitOfMeasure ->
//            unitOfMeasureCommands.add(unitOfMeasureToUnitTomeasureCommand.convert(unitOfMeasure))
//        );
//
//        return unitOfMeasureCommands;

        return StreamSupport.stream(uniOfMeasureRepository.findAll()
                .spliterator(), false)
                .map(unitOfMeasureToUnitTomeasureCommand::convert)
                .collect(Collectors.toSet());

    }
}
