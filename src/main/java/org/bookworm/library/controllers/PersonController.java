package org.bookworm.library.controllers;

import lombok.RequiredArgsConstructor;
import org.bookworm.library.dto.PersonDto;
import org.bookworm.library.entities.Person;
import org.bookworm.library.entities.PersonType;
import org.bookworm.library.services.PersonService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.UUID;

/**
 * Created by Grzegorz on 2019/06/20
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/persons")
public class PersonController {

    private final PersonService personService;

    @CrossOrigin(origins = "${ws.cross.origin.address}")
    @PostMapping(value = "")
    public Person insert(@RequestBody PersonDto personDto) {

        return personService.save(personDto);
    }

    @CrossOrigin(origins = "${ws.cross.origin.address}")
    @PutMapping(value = "/{id}")
    public Person update(@PathVariable(value = "id") UUID id, @RequestBody PersonDto personDto) {

        return personService.save(personDto);
    }

    @CrossOrigin(origins = "${ws.cross.origin.address}")
    @GetMapping(value = "")
    public Page<Person> findAll(Pageable pageable) {

        return personService.findAll(pageable);
    }

    @CrossOrigin(origins = "${ws.cross.origin.address}")
    @DeleteMapping(value = "/{id}")
    @Transactional
    public ResponseEntity<String> deleteById(@PathVariable(value = "id") UUID id) {

        personService.deleteById(id);
        return ResponseEntity.ok("person deleted");
    }

    @CrossOrigin(origins = "${ws.cross.origin.address}")
    @PatchMapping(value = "/{id}/type/{type}")
    public ResponseEntity<String> setType(@PathVariable(value = "type") PersonType type,
                                     @PathVariable(value = "id") UUID id) {

        personService.setType(type, id);
        return ResponseEntity.ok("person type updated");
    }
}
