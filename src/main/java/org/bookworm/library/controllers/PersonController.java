package org.bookworm.library.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bookworm.library.dto.PersonDto;
import org.bookworm.library.entities.PersonType;
import org.bookworm.library.security.roles.AllowedRoles;
import org.bookworm.library.services.PersonService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

/**
 * Created by Grzegorz on 2019/06/20
 */
@SuppressWarnings("checkstyle:Indentation")
@RequiredArgsConstructor
@CrossOrigin(origins = "${ws.cross.origin.address}")
@RestController
@RequestMapping(value = {"/persons", "/v1/persons"})
@Slf4j
public class PersonController {

    private final PersonService personService;

    @PostMapping(value = "")
    @AllowedRoles("EDITOR")
    public ResponseEntity<PersonDto> insert(@RequestBody PersonDto personDto) {
        log.info("Inserting person {}", personDto);
        return new ResponseEntity<>(personService.save(personDto), HttpStatus.CREATED);
    }

    @PutMapping(value = "")
    @AllowedRoles("EDITOR")
    public ResponseEntity<PersonDto> update(@RequestBody PersonDto personDto) {
        log.info("Updating person {}", personDto);
        return new ResponseEntity<>(personService.save(personDto), HttpStatus.OK);
    }

    @GetMapping(value = "")
    @AllowedRoles("CLIENT")
    public ResponseEntity<Page<PersonDto>> findAll(Pageable pageable) {
        log.info("Find all persons for {}", pageable);
        Page<PersonDto> personDtos = personService.findAll(pageable);
        if (!personDtos.isEmpty()) {
            return new ResponseEntity<>(personDtos, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/{id}")
    @AllowedRoles("CLIENT")
    public ResponseEntity<PersonDto> findById(@PathVariable(value = "id") UUID id) {
        log.info("Searching for person with id {}", id);
        Optional<PersonDto> personDtoOptional = personService.findById(id);
        if (personDtoOptional.isPresent()) {
            return new ResponseEntity<>(personDtoOptional.get(), HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/{id}")
    @AllowedRoles("EDITOR")
    public ResponseEntity<String> deleteById(@PathVariable(value = "id") UUID id) {
        log.info("Deleting person with id {}", id);
        personService.deleteById(id);
        return ResponseEntity.ok("person deleted");
    }

    @PatchMapping(value = "/{id}/type/{type}")
    @AllowedRoles("EDITOR")
    public ResponseEntity<String> setType(@PathVariable(value = "type") PersonType type,
                                          @PathVariable(value = "id") UUID id) {
        log.info("Setting type {} for person with id {}", type, id);
        personService.setType(type, id);
        return ResponseEntity.ok("person type updated");
    }
}
