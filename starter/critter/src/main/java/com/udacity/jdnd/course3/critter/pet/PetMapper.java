package com.udacity.jdnd.course3.critter.pet;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PetMapper {

    PetMapper MAPPER = Mappers.getMapper(PetMapper.class);
    PetDTO map(Pet Pet);
    Pet map(PetDTO PetDTO);
    List<PetDTO> map(List<Pet> Pets);
}
