package org.bookworm.library.dto;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

/**
 * Created by Grzegorz on 2020/05/25
 */
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class BookForModificationDto extends AbstractBookDto implements Serializable {

    private Integer publisherId;
}
