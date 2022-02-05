package org.bookworm.library.services;

import lombok.RequiredArgsConstructor;
import org.bookworm.library.dto.PublisherDto;
import org.bookworm.library.dto.PublisherMapper;
import org.bookworm.library.repositories.PublisherRepository;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by Grzegorz on 2020/05/25
 */
@RequiredArgsConstructor
@Service
@CacheConfig(cacheNames = {"publishers"})
public class PublisherService {

    private final PublisherRepository publisherRepository;
    private final PublisherMapper publisherMapper;


    @CachePut(key = "#result.id", unless = "#result == null", condition = "#result.id != null")
    public PublisherDto save(PublisherDto publisherDto) {

        return publisherMapper.toDto(
                publisherRepository.saveAndFlush(publisherMapper.toPublisher(publisherDto))
        );
    }

    public Page<PublisherDto> findAll(Pageable pageable) {

        return publisherRepository.findAll(pageable).map(publisherMapper::toDto);
    }

    @Cacheable(key = "#p0")
    public Optional<PublisherDto> findById(Integer id) {

        return publisherRepository.findById(id).map(publisherMapper::toDto);
    }

    @CacheEvict(key = "#p0")
    public void deleteById(Integer id) {

        publisherRepository.deleteById(id);
    }
}
