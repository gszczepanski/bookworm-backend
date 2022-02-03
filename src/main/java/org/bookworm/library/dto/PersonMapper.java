package org.bookworm.library.dto;

import org.bookworm.library.entities.Person;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    Person toPerson(PersonDto personDto);

    PersonDto toDto(Person person);
}
