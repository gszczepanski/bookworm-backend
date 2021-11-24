package org.bookworm.library.mappers;

import org.bookworm.library.dto.BookDto;
import org.bookworm.library.entities.Book;
import org.mapstruct.CollectionMappingStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Created by Grzegorz on 2020/05/25
 */
@Mapper(componentModel = "spring", uses = {AuthorMapper.class, PublisherMapper.class},
        collectionMappingStrategy = CollectionMappingStrategy.SETTER_PREFERRED)
public interface BookMapper {

    @Mapping(source = "publisherId", target = "publisher.id")
    Book toBook(BookDto bookDto);

    @Mapping(source = "publisher.id", target = "publisherId")
    BookDto toDto(Book book);
}
