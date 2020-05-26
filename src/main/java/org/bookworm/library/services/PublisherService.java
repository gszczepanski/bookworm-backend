package org.bookworm.library.services;

import lombok.RequiredArgsConstructor;
import org.bookworm.library.dto.PublisherDto;
import org.bookworm.library.entities.Publisher;
import org.bookworm.library.mappers.PublisherMapper;
import org.bookworm.library.repositories.PublisherRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Created by Grzegorz on 2020/05/25
 */
@RequiredArgsConstructor
@Service
public class PublisherService {

    private final PublisherRepository publisherRepository;
    private final PublisherMapper publisherMapper;

    public Publisher save(PublisherDto publisherDto) {
        return publisherRepository.save(publisherMapper.toPublisher(publisherDto));
    }

    public Page<Publisher> findAll(Pageable pageable) {
        return publisherRepository.findAll(pageable);
    }

    public void deleteById(Integer id) {
        publisherRepository.deleteById(id);

    }
}
