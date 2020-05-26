package org.bookworm.library.services;

import lombok.RequiredArgsConstructor;
import org.bookworm.library.dto.PersonDto;
import org.bookworm.library.entities.Person;
import org.bookworm.library.entities.PersonType;
import org.bookworm.library.mappers.PersonMapper;
import org.bookworm.library.repositories.PersonRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by Grzegorz on 2020/05/25
 */
@RequiredArgsConstructor
@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;


    public Person save(PersonDto personDto) {
        return personRepository.save(personMapper.toPerson(personDto));
    }

    public Page<Person> findAll(Pageable pageable) {
        return personRepository.findAll(pageable);
    }

    public void deleteById(UUID id) {
        personRepository.deleteById(id);
    }

    public void setType(PersonType type, UUID id) {
        personRepository.setType(type, id);
    }
}
