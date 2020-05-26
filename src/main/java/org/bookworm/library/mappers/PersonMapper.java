package org.bookworm.library.mappers;

import org.bookworm.library.dto.PersonDto;
import org.bookworm.library.entities.Person;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    Person toPerson(PersonDto personDto);

    PersonDto toDto(Person person);
}
