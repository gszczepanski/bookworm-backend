package org.bookworm.library.controllers;

import org.bookworm.library.entities.Person;
import org.bookworm.library.entities.PersonType;
import org.bookworm.library.repositories.IPersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.UUID;

/**
 * Created by Grzegorz on 2019/06/20
 */
@RestController
@RequestMapping("/persons")
public class PersonController {

    @Autowired
    IPersonRepository personRepository;

    @CrossOrigin(origins = "${ws.cross.origin.address}")
    @RequestMapping(value = "", method = RequestMethod.POST)
    public Person insert(@RequestBody Person person) {

        return personRepository.save(person);
    }

    @CrossOrigin(origins = "${ws.cross.origin.address}")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Person update(@PathVariable(value = "id") UUID id, @RequestBody Person person) {

        return personRepository.save(person);
    }

    @CrossOrigin(origins = "${ws.cross.origin.address}")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public Page<Person> findAll(Pageable pageable) {

        return personRepository.findAll(pageable);
    }

    @CrossOrigin(origins = "${ws.cross.origin.address}")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @Transactional
    public ResponseEntity<?> deleteById(@PathVariable(value = "id") UUID id) {

        personRepository.deleteById(id);
        return ResponseEntity.ok("person deleted");
    }

    @CrossOrigin(origins = "${ws.cross.origin.address}")
    @RequestMapping(value = "/{id}/type/{type}", method = RequestMethod.PATCH)
    public ResponseEntity<?> setType(@PathVariable(value = "type") PersonType type,
                                     @PathVariable(value = "id") UUID id) {

        personRepository.setType(type, id);
        return ResponseEntity.ok("person type updated");
    }
}
