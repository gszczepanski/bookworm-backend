package org.bookworm.library.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bookworm.library.dto.AuthorDto;
import org.bookworm.library.security.roles.AllowedRoles;
import org.bookworm.library.services.AuthorService;
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
@RequiredArgsConstructor
@CrossOrigin(origins = "${ws.cross.origin.address}")
@RestController()
@RequestMapping(value = {"/authors", "/v1/authors"})
@Slf4j
public class AuthorController {

    private final AuthorService authorService;

    @PostMapping(value = "")
    @AllowedRoles("EDITOR")
    public ResponseEntity<AuthorDto> insert(@RequestBody AuthorDto authorDto) {
        log.info("Inserting author {}", authorDto);
        return new ResponseEntity<>(authorService.save(authorDto), HttpStatus.CREATED);
    }

    @PutMapping(value = "")
    @AllowedRoles("EDITOR")
    public ResponseEntity<AuthorDto> update(@RequestBody AuthorDto authorDto) {
        log.info("Updating author {}", authorDto);
        return new ResponseEntity<>(authorService.save(authorDto), HttpStatus.OK);
    }

    @GetMapping(value = "")
    @AllowedRoles("CLIENT")
    public ResponseEntity<Page<AuthorDto>> findAll(Pageable pageable) {
        log.info("Find all authors for {}", pageable);
        Page<AuthorDto> authorDtos = authorService.findAll(pageable);
        if (!authorDtos.isEmpty()) {
            return new ResponseEntity<>(authorDtos, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/{id}")
    @AllowedRoles("CLIENT")
    public ResponseEntity<AuthorDto> findById(@PathVariable(value = "id") UUID id) {
        log.info("Searching for author with id {}", id);
        Optional<AuthorDto> authorDtoOptional = authorService.findById(id);
        if (authorDtoOptional.isPresent()) {
            return new ResponseEntity<>(authorDtoOptional.get(), HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/{id}")
    @AllowedRoles("EDITOR")
    public ResponseEntity<String> deleteById(@PathVariable(value = "id") UUID id) {
        log.info("Deleting author with id {}", id);
        authorService.deleteById(id);
        return ResponseEntity.ok("author deleted");
    }
}
