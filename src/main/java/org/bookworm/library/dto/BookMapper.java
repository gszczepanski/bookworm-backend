package org.bookworm.library.dto;

import org.bookworm.library.entities.Book;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Created by Grzegorz on 2020/05/25
 */
@Mapper(componentModel = "spring",// uses = {AuthorMapper.class, PublisherMapper.class},
        collectionMappingStrategy = CollectionMappingStrategy.SETTER_PREFERRED)
public interface BookMapper {

    @Mapping(target = "authors", ignore = true)
    @Mapping(target = "publisher", ignore = true)
    Book toBook(BookForModificationDto bookDto);

    BookForModificationDto toForModificationDto(Book book);

    @Mapping(target = "authorDtoList", ignore = true)
    @Mapping(target = "publisherDto", ignore = true)
    BookForDisplayDto toForDisplayDto(Book book);
}
