package org.bookworm.library.controllers;

import org.bookworm.library.entities.Person;
import org.bookworm.library.repositories.IPersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/")
    public Page<Person> getAll(Pageable pageable) {

        return personRepository.findAll(pageable);
    }
}
