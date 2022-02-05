package org.bookworm.library.services;

import lombok.RequiredArgsConstructor;
import org.bookworm.library.dto.PersonDto;
import org.bookworm.library.dto.PersonMapper;
import org.bookworm.library.entities.PersonType;
import org.bookworm.library.repositories.PersonRepository;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * Created by Grzegorz on 2020/05/25
 */
@RequiredArgsConstructor
@Service
@CacheConfig(cacheNames = {"persons"})
public class PersonService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;


    @CachePut(key = "#result.id", unless = "#result == null", condition = "#result.id != null")
    public PersonDto save(PersonDto personDto) {
        return personMapper.toDto(
                personRepository.saveAndFlush(personMapper.toPerson(personDto))
        );
    }

    public Page<PersonDto> findAll(Pageable pageable) {

        return personRepository.findAll(pageable).map(personMapper::toDto);
    }

    @Cacheable(key = "#p0")
    public Optional<PersonDto> findById(UUID id) {

        return personRepository.findById(id).map(personMapper::toDto);
    }

    @CacheEvict(key = "#p0")
    public void deleteById(UUID id) {
        personRepository.deleteById(id);
    }

    @CacheEvict(key = "#id")
    public void setType(PersonType type, UUID id) {
        personRepository.setType(type, id);
    }
}
