package org.bookworm.library.dto;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class BookForDisplayDto extends AbstractBookDto implements Serializable {

    private PublisherDto publisherDto;
    private List<AuthorDto> authorDtoList;
}
