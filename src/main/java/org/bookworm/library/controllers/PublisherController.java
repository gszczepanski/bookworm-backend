package org.bookworm.library.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bookworm.library.dto.PublisherDto;
import org.bookworm.library.security.roles.AllowedRoles;
import org.bookworm.library.services.PublisherService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Created by Grzegorz on 2019/06/20
 */
@RequiredArgsConstructor
@CrossOrigin(origins = "${ws.cross.origin.address}")
@RestController
@RequestMapping(value = {"/publishers", "/v1/publishers"})
@Slf4j
public class PublisherController {

    private final PublisherService publisherService;

    @PostMapping(value = "")
    @AllowedRoles("EDITOR")
    public ResponseEntity<PublisherDto> insert(@RequestBody PublisherDto publisherDto) {
        log.info("Inserting publisher {}", publisherDto);
        return new ResponseEntity<>(publisherService.save(publisherDto), HttpStatus.CREATED);
    }

    @PutMapping(value = "")
    @AllowedRoles("EDITOR")
    public ResponseEntity<PublisherDto> update(@RequestBody PublisherDto publisherDto) {
        log.info("Updating publisher {}", publisherDto);
        return new ResponseEntity<>(publisherService.save(publisherDto), HttpStatus.OK);
    }

    @GetMapping(value = "")
    @AllowedRoles("CLIENT")
    public ResponseEntity<Page<PublisherDto>> findAll(Pageable pageable) {
        log.info("Find all publishers for {}", pageable);
        Page<PublisherDto> publisherDtos = publisherService.findAll(pageable);
        if (!publisherDtos.isEmpty()) {
            return new ResponseEntity<>(publisherDtos, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/{id}")
    @AllowedRoles("CLIENT")
    public ResponseEntity<PublisherDto> findById(@PathVariable(value = "id") Integer id) {
        log.info("Searching for publisher with id {}", id);
        Optional<PublisherDto> publisherDtoOptional = publisherService.findById(id);
        if (publisherDtoOptional.isPresent()) {
            return new ResponseEntity<>(publisherDtoOptional.get(), HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/{id}")
    @AllowedRoles("EDITOR")
    public ResponseEntity<String> deleteById(@PathVariable(value = "id") Integer id) {
        log.info("Deleting publisher with id {}", id);
        publisherService.deleteById(id);
        return ResponseEntity.ok("publisher deleted");
    }
}
