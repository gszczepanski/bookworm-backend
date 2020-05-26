package org.bookworm.library.services;

import lombok.RequiredArgsConstructor;
import org.bookworm.library.dto.AuthorDto;
import org.bookworm.library.entities.Author;
import org.bookworm.library.mappers.AuthorMapper;
import org.bookworm.library.repositories.AuthorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by Grzegorz on 2020/05/25
 */
@RequiredArgsConstructor
@Service
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    public Author save(AuthorDto authorDto) {
        return authorRepository.save(authorMapper.toAuthor(authorDto));
    }

    public Page<Author> findAll(Pageable pageable) {
        return authorRepository.findAll(pageable);
    }

    public void deleteById(UUID id) {
        authorRepository.deleteById(id);
    }
}
