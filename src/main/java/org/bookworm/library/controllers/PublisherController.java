package org.bookworm.library.controllers;

import lombok.RequiredArgsConstructor;
import org.bookworm.library.dto.PublisherDto;
import org.bookworm.library.entities.Publisher;
import org.bookworm.library.services.PublisherService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

/**
 * Created by Grzegorz on 2019/06/20
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/publishers")
public class PublisherController {

    private final PublisherService publisherService;

    @CrossOrigin(origins = "${ws.cross.origin.address}")
    @PostMapping(value = "")
    public Publisher insert(@RequestBody PublisherDto publisherDto) {

        return publisherService.save(publisherDto);
    }

    @CrossOrigin(origins = "${ws.cross.origin.address}")
    @PutMapping(value = "/{id}")
    public Publisher update(@PathVariable(value = "id") Integer id, @RequestBody PublisherDto publisherDto) {

        return publisherService.save(publisherDto);
    }

    @CrossOrigin(origins = "${ws.cross.origin.address}")
    @GetMapping(value = "")
    public Page<Publisher> findAll(Pageable pageable) {

        return publisherService.findAll(pageable);
    }

    @CrossOrigin(origins = "${ws.cross.origin.address}")
    @DeleteMapping(value = "/{id}")
    @Transactional
    public ResponseEntity<String> deleteById(@PathVariable(value = "id") Integer id) {

        publisherService.deleteById(id);
        return ResponseEntity.ok("publisher deleted");
    }
}
