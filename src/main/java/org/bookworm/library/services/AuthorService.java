package org.bookworm.library.services;

import lombok.RequiredArgsConstructor;
import org.bookworm.library.dto.AuthorDto;
import org.bookworm.library.dto.AuthorMapper;
import org.bookworm.library.repositories.AuthorRepository;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by Grzegorz on 2020/05/25
 */
@RequiredArgsConstructor
@Service
@CacheConfig(cacheNames = {"authors"})
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;


    @CachePut(key = "#result.id", unless = "#result == null", condition = "#result.id != null")
    public AuthorDto save(AuthorDto authorDto) {
        return authorMapper.toDto(
                authorRepository.saveAndFlush(authorMapper.toAuthor(authorDto))
        );
    }

    public Page<AuthorDto> findAll(Pageable pageable) {
        return authorRepository.findAll(pageable).map(authorMapper::toDto);
    }

    public List<AuthorDto> findAllByBookId(UUID bookId) {
        return authorRepository.findAllByBookId(bookId)
                .stream().map(authorMapper::toDto).collect(Collectors.toList());
    }

    @Cacheable(key = "#p0")
    public Optional<AuthorDto> findById(UUID id) {
        return authorRepository.findById(id).map(authorMapper::toDto);
    }

    @CacheEvict(key = "#p0")
    public void deleteById(UUID id) {
        authorRepository.deleteById(id);
    }
}
