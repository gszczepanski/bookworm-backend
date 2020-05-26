package org.bookworm.library.controllers;

import lombok.RequiredArgsConstructor;
import org.bookworm.library.dto.AuthorDto;
import org.bookworm.library.entities.Author;
import org.bookworm.library.services.AuthorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Created by Grzegorz on 2019/06/20
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    @CrossOrigin(origins = "${ws.cross.origin.address}")
    @PostMapping(value = "")
    public Author insert(@RequestBody AuthorDto authorDto) {

        return authorService.save(authorDto);
    }

    @CrossOrigin(origins = "${ws.cross.origin.address}")
    @PutMapping(value = "/{id}")
    public Author update(@PathVariable(value = "id") UUID id, @RequestBody AuthorDto authorDto) {

        return authorService.save(authorDto);
    }

    @CrossOrigin(origins = "${ws.cross.origin.address}")
    @GetMapping(value = "")
    public Page<Author> findAll(Pageable pageable) {

        return authorService.findAll(pageable);
    }

    @CrossOrigin(origins = "${ws.cross.origin.address}")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteById(@PathVariable(value = "id") UUID id) {

        authorService.deleteById(id);
        return ResponseEntity.ok("author deleted");
    }
}
